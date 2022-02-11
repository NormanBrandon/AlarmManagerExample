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
    fun setTime(i:Int){
        mElapsedTime.postValue(i.toLong())
    }

    fun initTimer(){
        mElapsedTime.postValue(60)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (elapsedTime.value != null){
                    // setValue() cannot be called from a background thread so post to main thread.
                    var newValue = (elapsedTime.value!!.toLong())
                    when(newValue){
                        0.toLong()->{
                            mElapsedTime.postValue(60)
                        }
                        else -> {
                            mElapsedTime.postValue(newValue -1)
                            newValue -= 1
                        }
                    }
                }
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }
    companion object {
        private const val ONE_SECOND = 1000
    }

    init {
        mElapsedTime.postValue(mInitialTime)
        // Update the elapsed time every second.
        initTimer()

    }
}