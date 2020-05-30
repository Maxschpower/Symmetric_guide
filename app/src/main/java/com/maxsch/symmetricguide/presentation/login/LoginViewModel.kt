package com.maxsch.symmetricguide.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.maxsch.symmetricguide.entity.session.repository.SessionRepository
import com.maxsch.symmetricguide.entity.user.repository.UserRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import com.maxsch.symmetricguide.presentation.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
    private val sessionRepositoryImpl: SessionRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val loginPassedEvent = SingleLiveEvent<Unit>()
    val screenState = MutableLiveData<ScreenState>()

    init {
        sessionRepositoryImpl.getActiveSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                proceedToMaterialsList()
                screenState.value = ScreenState.Default
            }, {
                Log.e("${this::class}", "$it")
                screenState.value = ScreenState.Default
            })
            .untilDestroy()
    }

    fun login(username: String, password: String) {
        userRepository.getUserByUsername(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapCompletable {
                if (password == it.password)
                    sessionRepositoryImpl.saveSession(username, password)
                else
                    Completable.error(WrongPassError)
            }
            .subscribe({
                proceedToMaterialsList()
                screenState.value = ScreenState.Default
            }, {
                Log.e("${this::class}", "$it")
                if (it !is Error)
                    screenState.value = ScreenState.Error.WrongUsername()
                else
                    screenState.value = ScreenState.Error.WrongPassword()
            })
            .untilDestroy()
    }

    private fun proceedToMaterialsList() {
        loginPassedEvent()
    }

}

object WrongPassError : Throwable()

sealed class ScreenState {
    sealed class Error(val errorText: String) : ScreenState() {
        class WrongUsername : Error("Неправильное имя пользователя")

        class WrongPassword : Error("Неправильный пароль")
    }

    object Default : ScreenState()

}
