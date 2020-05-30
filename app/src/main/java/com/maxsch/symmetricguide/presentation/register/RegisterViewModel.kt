package com.maxsch.symmetricguide.presentation.register

import com.maxsch.symmetricguide.entity.session.repository.SessionRepository
import com.maxsch.symmetricguide.entity.user.User
import com.maxsch.symmetricguide.entity.user.repository.UserRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import com.maxsch.symmetricguide.presentation.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : BaseViewModel() {

    val loginEvent = SingleLiveEvent<Unit>()

    fun register(username: String, password: String) {
        userRepository.registerUser(User(null, username, password))
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                sessionRepository.saveSession(username, password)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginEvent()
            }, {

            })
            .untilDestroy()
    }
}