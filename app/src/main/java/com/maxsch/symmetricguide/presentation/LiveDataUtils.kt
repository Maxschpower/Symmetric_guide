package com.maxsch.symmetricguide.presentation

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean



fun <T> LiveData<T>.subscribe(lifecycleOwner: LifecycleOwner, func: (T) -> Unit) =
    this.observe(lifecycleOwner, Observer { func(it) })

fun SingleLiveEvent<Unit>.subscribe(lifecycleOwner: LifecycleOwner, func: () -> Unit) =
    this.observe(lifecycleOwner, Observer { func() })

class SingleLiveEvent<T : Any> : MutableLiveData<T?>() {

    private val mPending: AtomicBoolean = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        if (hasActiveObservers()) {
            Log.w(
                TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    operator fun invoke()  {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}