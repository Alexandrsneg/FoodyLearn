package com.example.foodylearn.data

import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

class IOExceptionInterceptor : Interceptor {

    var reqId: String = ""
    var isProcessing = false
    var isCanceled = false

    private fun refreshRequestId() {
        reqId = UUID.randomUUID().toString()
    }

    init {
        refreshRequestId()
    }

    private val _status = MutableSharedFlow<InterceptorStatus>(1)
    val status: SharedFlow<InterceptorStatus> = _status


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.tag(RequestType.CRITICAL::class.java) != RequestType.CRITICAL)
            return chain.proceed(request)

        isProcessing = true
        var response: Response? = null
        request = chain.request().newBuilder().addHeader(REQUEST_ID, reqId).build()

        runBlocking {
            var tryCount = 1
            while (response.isInvalid() && tryCount <= MAX_TRY && !isCanceled) {
                _status.emit(InterceptorStatus(IOInterceptorStatus.OnTryConnect, tryCount))

                if (tryCount == 2)
                    _status.emit(InterceptorStatus(IOInterceptorStatus.OnStartReconnect, tryCount))

                try {
                    response = chain
                        .withReadTimeout(CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
                        .withConnectTimeout(CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
                        .withWriteTimeout(CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
                        .proceed(request)
                } catch (e: IOException) {
                    Thread.sleep(SLEEP_INTERVAL_MS)
                    if (isCanceled || tryCount == MAX_TRY)
                        response = getIOErrorResponse(request)
                } finally {
                    tryCount++
                }
            }

            if (!response.isInvalid()) {
                refreshRequestId()
                _status.emit(InterceptorStatus(IOInterceptorStatus.OnSuccessResponse, tryCount))
                return@runBlocking
            }
            _status.emit(InterceptorStatus(IOInterceptorStatus.OnAttemptsEnded, MAX_TRY))
        }
        isProcessing = false
        isCanceled = false
        return response ?: chain.proceed(request)
    }

    private fun getIOErrorResponse(request: Request): Response {
        val corResponse = "AnyError"
        val gson = Gson().toJson(corResponse, String::class.java)

        return Response.Builder()
            .code(IO_EXCEPTION_CODE)
            .body(gson.toResponseBody(null))
            .protocol(Protocol.HTTP_2)
            .message("IO_EXCEPTION")
            .request(request)
            .build()
    }


    private fun Response?.isInvalid(): Boolean {
        val contentType = this?.body?.contentType()
        val charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
        val source = this?.body?.source()
        source?.request(Long.MAX_VALUE)

        var errorIsTimeout: Boolean
        source?.buffer.use { buf ->
            errorIsTimeout =
                "${buf?.clone()?.readString(charset)}".contains("response_timeout_exceeded")
        }
        if (errorIsTimeout)
            Thread.sleep(SLEEP_INTERVAL_MS)
        return (this?.body == null || errorIsTimeout || code == IO_EXCEPTION_CODE)
    }

    companion object {
        const val CALL_TIMEOUT_SEC = 60
        private const val SERVER_RESPONSE_TIMEOUT_EXCEED_SEC = 10
        const val SLEEP_INTERVAL_MS = 10000L
        const val MAX_TRY = ((CALL_TIMEOUT_SEC - SERVER_RESPONSE_TIMEOUT_EXCEED_SEC) / (SLEEP_INTERVAL_MS/1000) ).toInt()
        const val IO_EXCEPTION_CODE = 999


        const val REQUEST_ID = "requestId"
    }
}

data class InterceptorStatus(
    val status: IOInterceptorStatus,
    val tryCount: Int
)

enum class IOInterceptorStatus {
    OnStartReconnect, OnSuccessResponse, OnAttemptsEnded, OnTryConnect
}