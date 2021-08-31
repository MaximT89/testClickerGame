package com.example.testclickergame.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testclickergame.R;
import com.example.testclickergame.databinding.ActivityMainBinding;
import com.example.testclickergame.generatorFactory.GeneratorSprites;
import com.example.testclickergame.stats.SceneStats;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    private final String TAG = "TAG";

    private ActivityMainBinding binding;

    private AnimationDrawable mAnimationPlayer, mAnimationEnemy, mAnimatorGoldMonet;
    private Animation animShake;
    private long totalDuration = 0;
    private boolean inGame = false;
    private int totalDamage = 0;
    private int currentDamage = 5;
    private int hpEnemy = 1;

    private boolean enemyDie = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initView();
        goldMonetCreater();
        playerCreater();
        enemyCreater();

        binding.constrainRoot.setOnClickListener(v -> {
            stopAnimation(mAnimationPlayer);
            startAnimation(mAnimationPlayer);

            stopAnimation(mAnimationEnemy);
            startAnimation(mAnimationEnemy);

            showScore();
            updateHpBarEnemyAndCreateNewEnemy();
            showDamage(currentDamage);
        });
    }

    private void enemyCreater() {
        Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        GeneratorSprites.fillAnimation(mAnimationEnemy, GeneratorSprites.getListBitmapGoblinHurt(MainActivity.this), 40);
                        binding.imageViewEnemy.setBackground(mAnimationEnemy);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void playerCreater() {
        Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        GeneratorSprites.fillAnimation(mAnimationPlayer, GeneratorSprites.getListBitmapOrcSlashing(MainActivity.this), 40);
                        binding.imageViewPlayer.setBackground(mAnimationPlayer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void goldMonetCreater() {
        Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                        binding.imageGoldMonet.startAnimation(animShake);

                        GeneratorSprites.fillAnimation(mAnimatorGoldMonet, GeneratorSprites.getListBitmapGoldMonet(MainActivity.this), 40);
                        binding.imageGoldMonet.setBackground(mAnimatorGoldMonet);
                        stopAnimation(mAnimatorGoldMonet);
                        startAnimation(mAnimatorGoldMonet);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    private void updateHpBarEnemyAndCreateNewEnemy() {
        // В данном методе мы заносим хп противника в прогресс бар (хп бар) под монстром,
        // если хп заканчивается мы прибавляем золото и наполняем прогресс бар с хп новыми жизнями

        totalDamage += currentDamage;
        int currentEnemyHp = hpEnemy - totalDamage;
        binding.pbHorizontal.setProgress(currentEnemyHp);

        if (currentEnemyHp <= 0) {
            binding.textHpEnemy.setText("0");
            sendGold(10);

            Completable.complete()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {

                            binding.constrainRoot.setClickable(false);
                            binding.textHpEnemy.setText("DEAD");

                            mAnimationEnemy = new AnimationDrawable();
                            mAnimationEnemy.setOneShot(true);
                            GeneratorSprites.fillAnimation(mAnimationEnemy, GeneratorSprites.getListBitmapGoblinDying(MainActivity.this), 40);

                            binding.imageViewEnemy.setBackground(mAnimationEnemy);

                            stopAnimation(mAnimationEnemy);
                            startAnimation(mAnimationEnemy);

                            Completable.timer(700, TimeUnit.MILLISECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableCompletableObserver() {
                                        @Override
                                        public void onComplete() {
                                            stopAnimation(mAnimationEnemy);
                                            createEnemyHp();
                                            binding.constrainRoot.setClickable(true);
                                            mAnimationEnemy = new AnimationDrawable();
                                            mAnimationEnemy.setOneShot(true);
                                            GeneratorSprites.fillAnimation(mAnimationEnemy, GeneratorSprites.getListBitmapGoblinHurt(MainActivity.this), 40);
                                            binding.imageViewEnemy.setBackground(mAnimationEnemy);
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            e.printStackTrace();
                                        }
                                    });
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                        }
                    });
        } else {
            binding.textHpEnemy.setText(String.valueOf(currentEnemyHp));
        }
    }

    private void createEnemyHp() {
        // Создаем полоску с жизнями монстра
        hpEnemy = SceneStats.getHpEnemy(SceneStats.currentLevelScene);
        binding.pbHorizontal.setMax(hpEnemy);
        binding.pbHorizontal.setProgress(hpEnemy);
        binding.textHpEnemy.setText(String.valueOf(hpEnemy));
        totalDamage = 0;
    }

    private void stopGame() {
        inGame = false;
        stopAnimation(mAnimationPlayer);
    }

    private long getTotalDuration(AnimationDrawable mAnimation) {
        long temp = 0;

        for (int i = 0; i < mAnimation.getNumberOfFrames(); i++) {
            temp += mAnimation.getDuration(i);
        }
        return temp;
    }

    private void updateData(long totalDuration) {
        Completable.timer(totalDuration, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        sendGold(10);
                        showScore();
                        if (inGame) {
                            updateData(totalDuration);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Скрываем навигационные кнопки
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initView() {
        // Инициализируем view model
        final MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Создаем Enemy
        createEnemyHp();

        // Показываем золото
        binding.textScore.setText("Gold : " + SceneStats.score);

        // Создаем аниматор для золотой монеты
        mAnimatorGoldMonet = new AnimationDrawable();
        mAnimatorGoldMonet.setOneShot(false);
        mAnimatorGoldMonet.setVisible(true, true);

        // Создаем аниматор для ГГ
        mAnimationPlayer = new AnimationDrawable();
        mAnimationPlayer.setOneShot(true);
        mAnimationPlayer.setVisible(true, true);

        // Создаем аниматор для Enemy
        mAnimationEnemy = new AnimationDrawable();
        mAnimationEnemy.setOneShot(true);
        mAnimationEnemy.setVisible(true, true);
    }

    private void showScore() {
        binding.textScore.setText("Gold : " + SceneStats.score);
    }

    private void showDamage(int currentDamage) {
        // Данный метод показывает красный урон над монстром

        Completable.timer(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_combination);
                        TextView textView = new TextView(MainActivity.this);
                        RelativeLayout.LayoutParams lpView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lpView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        textView.setLayoutParams(lpView);
                        textView.setText("- " + currentDamage);
                        textView.setTypeface(Typeface.DEFAULT_BOLD);
                        textView.setTextSize(26f);
                        textView.setShadowLayer(2, 1, 1, Color.BLACK);
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

    private void sendGold(int count) {
        SceneStats.score = SceneStats.score + count;
    }

    private void startAnimation(AnimationDrawable mAnimation) {
        mAnimation.start();
    }

    private void stopAnimation(AnimationDrawable mAnimation) {
        if (mAnimation.isRunning()) {
            mAnimation.stop();
        }
    }

    private void startGame() {
        inGame = true;
        stopAnimation(mAnimationPlayer);
        updateData(getTotalDuration(mAnimationPlayer));
        startAnimation(mAnimationPlayer);
    }
}