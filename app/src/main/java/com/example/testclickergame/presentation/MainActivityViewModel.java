package com.example.testclickergame.presentation;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private CountDownTimer timer;
    private MutableLiveData<Integer> seconds = new MutableLiveData<>();

    public LiveData<Integer> seconds(){
        return seconds;
    }

    public void startTimer(){
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                long timeLeft = l / 1000;
                seconds.setValue((int)timeLeft);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void stopTimer(){
        timer.cancel();
    }
}