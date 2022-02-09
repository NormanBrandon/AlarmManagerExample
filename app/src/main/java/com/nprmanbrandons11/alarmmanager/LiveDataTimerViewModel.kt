package com.nprmanbrandons11.alarmmanager

import androidx.lifecycle.LiveData

import android.os.SystemClock

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import java.util.*


class LiveDataTimerViewModel : ViewModel() {

    private val mElapsedTime = MutableLiveData<Long>()
    private val mInitialTime: Long = 60
    private val timer: Timer = Timer()

    val elapsedTime: LiveData<Long>
        get() = mElapsedTime

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000
    }

    init {
        mElapsedTime.postValue(mInitialTime)
        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (elapsedTime.value != null){
                    // setValue() cannot be called from a background thread so post to main thread.
                        when(val newValue = (elapsedTime.value!!.toLong() - 1)){
                            0.toLong()->{
                                mElapsedTime.postValue(60)
                                timer.cancel()
                            }
                            else -> mElapsedTime.postValue(newValue)
                        }

                }
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }
}