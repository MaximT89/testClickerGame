package com.example.testclickergame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.testclickergame.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    private AnimationDrawable mAnimationPlayer;
    private AnimationDrawable mAnimationEnemy;
    private long totalDuration = 0;
    private boolean inGame = false;
    private int totalDamage = 0;
    private int hpEnemy = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initView();

        fillAnimation(mAnimationPlayer, getListBitmapOrcSlashing(), 40);
        fillAnimation(mAnimationEnemy, getListBitmapGoblinHurt(), 40);

        binding.imageViewPlayer.setBackground(mAnimationPlayer);
        binding.imageViewEnemy.setBackground(mAnimationEnemy);

        binding.stopGame.setOnClickListener(v -> stopGame());
        binding.constrainRoot.setOnClickListener(v -> {
            stopAnimation(mAnimationPlayer);
            startAnimation(mAnimationPlayer);

            stopAnimation(mAnimationEnemy);
            startAnimation(mAnimationEnemy);

            showScore();
            updateHpEnemy();
        });
    }

    private void updateHpEnemy() {
        totalDamage += 5;
        int currentEnemyHp = hpEnemy - totalDamage;
        binding.pbHorizontal.setProgress(currentEnemyHp);
        if (currentEnemyHp < 0) {
            binding.textHpEnemy.setText("0");
            generateEnemy();
        } else {
            binding.textHpEnemy.setText(String.valueOf(currentEnemyHp));
        }
    }

    private void generateEnemy() {
        hpEnemy = Utils.getHpEnemy(Utils.currentLevelScene);
        binding.pbHorizontal.setMax(hpEnemy);
        binding.pbHorizontal.setProgress(hpEnemy);
        binding.textHpEnemy.setText(String.valueOf(hpEnemy));
        totalDamage = 0;
        updateScore(10);
    }


    private void stopGame() {
        inGame = false;
        stopAnimation(mAnimationPlayer);
    }

    private void startAnimation(AnimationDrawable mAnimation) {
        mAnimation.start();
    }

    private void stopAnimation(AnimationDrawable mAnimation) {
        if (mAnimation.isRunning()) {
            mAnimation.stop();
        }
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
                        updateScore(10);
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

    private void fillAnimation(AnimationDrawable mAnimation, List<BitmapDrawable> listBitmap, int duration) {
        for (int i = 0; i < listBitmap.size(); i++) {
            mAnimation.addFrame(listBitmap.get(i), duration);
        }
    }

    private List<BitmapDrawable> getListBitmapOrcSlashing() {
        List<BitmapDrawable> frames = new ArrayList<>();
        List<Bitmap> listBitmap = new ArrayList<>();

        Bitmap bm1 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_000.png");
        Bitmap bm2 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_001.png");
        Bitmap bm3 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_002.png");
        Bitmap bm4 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_003.png");
        Bitmap bm5 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_004.png");
        Bitmap bm6 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_005.png");
        Bitmap bm7 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_006.png");
        Bitmap bm8 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_007.png");
        Bitmap bm9 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_008.png");
        Bitmap bm10 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_009.png");
        Bitmap bm11 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_010.png");
        Bitmap bm12 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Slashing_011.png");
        Bitmap bm13 = getBitmapFromAssets(this, "images/orc/slash/0_Orc_Idle_000.png");

        BitmapDrawable frame1 = new BitmapDrawable(getResources(), bm1);
        BitmapDrawable frame2 = new BitmapDrawable(getResources(), bm2);
        BitmapDrawable frame3 = new BitmapDrawable(getResources(), bm3);
        BitmapDrawable frame4 = new BitmapDrawable(getResources(), bm4);
        BitmapDrawable frame5 = new BitmapDrawable(getResources(), bm5);
        BitmapDrawable frame6 = new BitmapDrawable(getResources(), bm6);
        BitmapDrawable frame7 = new BitmapDrawable(getResources(), bm7);
        BitmapDrawable frame8 = new BitmapDrawable(getResources(), bm8);
        BitmapDrawable frame9 = new BitmapDrawable(getResources(), bm9);
        BitmapDrawable frame10 = new BitmapDrawable(getResources(), bm10);
        BitmapDrawable frame11 = new BitmapDrawable(getResources(), bm11);
        BitmapDrawable frame12 = new BitmapDrawable(getResources(), bm12);
        BitmapDrawable frame13 = new BitmapDrawable(getResources(), bm13);

        frames.add(frame1);
        frames.add(frame2);
        frames.add(frame3);
        frames.add(frame4);
        frames.add(frame5);
        frames.add(frame6);
        frames.add(frame7);
        frames.add(frame8);
        frames.add(frame9);
        frames.add(frame10);
        frames.add(frame11);
        frames.add(frame12);
        frames.add(frame13);

        return frames;
    }

    private List<BitmapDrawable> getListBitmapGoblinHurt() {
        List<BitmapDrawable> frames = new ArrayList<>();
        List<Bitmap> listBitmap = new ArrayList<>();

        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Idle_000.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_000.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_001.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_002.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_003.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_004.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_005.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_006.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_007.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_008.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_009.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_010.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Hurt_011.png"));
        listBitmap.add(getBitmapFromAssets(this, "images/goblin/hurt/0_Goblin_Idle_000.png"));

        for (int i = 0; i < listBitmap.size(); i++) {
            frames.add(new BitmapDrawable(getResources(), listBitmap.get(i)));
        }

        return frames;
    }

    private Bitmap getBitmapFromAssets(MainActivity mainActivity, String filepath) {
        AssetManager assetManager = mainActivity.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    private void showScore() {
        binding.textScore.setText("Gold : " + Utils.score);
    }

    private void updateScore(int count) {
        Utils.score = Utils.score + count;
    }

    private void initView() {

        hpEnemy = Utils.getHpEnemy(Utils.currentLevelScene);

        binding.pbHorizontal.setMax(hpEnemy);
        binding.pbHorizontal.setProgress(hpEnemy);
        binding.textHpEnemy.setText(String.valueOf(hpEnemy));

        Log.d(TAG, "pbHorizontal.getMax: " + binding.pbHorizontal.getMax());
        Log.d(TAG, "pbHorizontal.getProgress: " + binding.pbHorizontal.getProgress());
        Log.d(TAG, "textHpEnemy.getText: " + binding.textHpEnemy.getText());

        binding.textScore.setText("Gold : " + Utils.score);
        mAnimationPlayer = new AnimationDrawable();
        mAnimationPlayer.setOneShot(true);
        mAnimationPlayer.setVisible(true, true);

        mAnimationEnemy = new AnimationDrawable();
        mAnimationEnemy.setOneShot(true);
        mAnimationEnemy.setVisible(true, true);
    }


    private void stopAnimationEnemy(AnimationDrawable mAnimation) {
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