package com.example.domain.usecase

import com.example.domain.models.FoodJokeClean
import javax.inject.Inject


/**
 * Test class For learning RxJava
 */
class GetJokeUseCaseRx @Inject constructor(
    private val repository: IRepository
)  {

//    @Volatile
//    private var disposable: Disposable? = null
    fun execute(
        apiKey: String,
        onComplete: () -> Unit,
        onNext: (joke: FoodJokeClean) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
//        disposable?.let {
//            if (!it.isDisposed)
//                it.dispose()
//        }

//        disposable = repository.remote.getJokeRx(apiKey)
//            .subscribeOn(Schedulers.io())
//            .flatMap {
//                Observable.fromIterable(it)
//            }
//            .flatMap {
//                repository.remote.getJokeSRx(it)
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(onNext, onError, onComplete)
    }
}