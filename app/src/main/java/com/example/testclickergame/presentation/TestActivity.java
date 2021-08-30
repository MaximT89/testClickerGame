package com.example.testclickergame.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testclickergame.R;
import com.example.testclickergame.databinding.ActivityTestBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
//    private Animation animation;
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.btnStart.setOnClickListener(v -> {
            startAnim();
        });
    }

    private void startAnim() {
        Completable.timer(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Animation animation;
                        animation = AnimationUtils.loadAnimation(TestActivity.this, R.anim.anim_combination);
                        TextView textView;
                        textView = new TextView(TestActivity.this);
                        RelativeLayout.LayoutParams lpView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lpView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        textView.setLayoutParams(lpView);
                        textView.setText("-100");
                        textView.setTextColor(Color.parseColor("#DE0F0F"));
                        binding.frameRootText.addView(textView);

                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                textView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                textView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        textView.startAnimation(animation);
                        Completable.timer(1000, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableCompletableObserver() {
                                    @Override
                                    public void onComplete() {
                                        binding.frameRootText.removeView(textView);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}