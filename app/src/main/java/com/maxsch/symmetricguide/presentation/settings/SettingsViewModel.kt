package com.maxsch.symmetricguide.presentation.settings

import com.maxsch.symmetricguide.entity.session.repository.SessionRepository
import com.maxsch.symmetricguide.entity.user.repository.UserRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import com.maxsch.symmetricguide.presentation.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingsViewModel(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val logoutEvent = SingleLiveEvent<Unit>()

    fun logout() {
        sessionRepository.clearSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                logoutEvent()
            }, {
                logoutEvent()
            })
            .untilDestroy()
    }

    fun deleteAccount() {
        sessionRepository.getActiveSession()
            .subscribeOn(Schedulers.io())
            .flatMap {
                userRepository.getUserByUsername(it.username)
            }
            .flatMap {
                userRepository.deleteUser(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                logoutEvent()
            }, {
                logoutEvent
            })
            .untilDestroy()
    }
}