package com.example.testclickergame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.example.testclickergame.databinding.ActivityMainBinding binding;

    AnimationDrawable mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.testclickergame.databinding.ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initView();
        getListBitmapPlayer();

        fillAnimationPlayer(mAnimation, getListBitmapPlayer());
        binding.imageView.setBackground(mAnimation);

        binding.constrainRoot.setOnClickListener(v -> {
            if (mAnimation.isRunning()) {
                mAnimation.stop();
            }
            updateScore();
            showScore();

            mAnimation.start();
        });
    }

    private void fillAnimationPlayer(AnimationDrawable mAnimation, List<BitmapDrawable> listBitmapPlayer) {
        for (int i = 0; i < listBitmapPlayer.size(); i++) {
            mAnimation.addFrame(listBitmapPlayer.get(i), 65);
        }
    }

    private List<BitmapDrawable> getListBitmapPlayer() {
        List<BitmapDrawable> frames = new ArrayList<>();

        Bitmap bm1;
        Bitmap bm2;
        Bitmap bm3;
        Bitmap bm4;
        Bitmap bm5;
        Bitmap bm6;

        bm1 = getBitmapFromAssets(this, "images/player/attack000.png");
        bm2 = getBitmapFromAssets(this, "images/player/attack001.png");
        bm3 = getBitmapFromAssets(this, "images/player/attack002.png");
        bm4 = getBitmapFromAssets(this, "images/player/attack003.png");
        bm5 = getBitmapFromAssets(this, "images/player/attack004.png");
        bm6 = getBitmapFromAssets(this, "images/player/attack000.png");

        BitmapDrawable frame1;
        BitmapDrawable frame2;
        BitmapDrawable frame3;
        BitmapDrawable frame4;
        BitmapDrawable frame5;
        BitmapDrawable frame6;

        frame1 = new BitmapDrawable(getResources(), bm1);
        frame2 = new BitmapDrawable(getResources(), bm2);
        frame3 = new BitmapDrawable(getResources(), bm3);
        frame4 = new BitmapDrawable(getResources(), bm4);
        frame5 = new BitmapDrawable(getResources(), bm5);
        frame6 = new BitmapDrawable(getResources(), bm6);

        frames.add(frame1);
        frames.add(frame2);
        frames.add(frame3);
        frames.add(frame4);
        frames.add(frame5);
        frames.add(frame6);

        return frames;
    }

    private Bitmap getBitmapFromAssets(MainActivity mainActivity,
                                       String filepath) {
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
    @SuppressLint("SetTextI18n")
    private void showScore() {
        binding.textScore.setText("Score : " + Utils.score);
    }

    private void updateScore() {
        Utils.score = Utils.score + 1;
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        binding.textScore.setText("Score : " + Utils.score);
        mAnimation = new AnimationDrawable();
        mAnimation.setOneShot(true);
        mAnimation.setVisible(true, true);
    }
}