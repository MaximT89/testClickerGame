package com.example.testclickergame.generatorFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSprites {

    // Наполняет аниматор картинками и задает скорость анимации
    public static void fillAnimation(AnimationDrawable mAnimation, List<BitmapDrawable> listBitmap, int duration) {
        for (int i = 0; i < listBitmap.size(); i++) {
            mAnimation.addFrame(listBitmap.get(i), duration);
        }
    }

    // Метод для получения картинки из assets
    private static Drawable getDrawable(Context context, String pathPicture) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(pathPicture);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Drawable drawable = Drawable.createFromStream(inputStream, null);
        return drawable;
    }

    // Данным методом мы берем картинку по ссылке из assets
    private static Bitmap getBitmapFromAssets(Context context, String filepath) {
        AssetManager assetManager = context.getAssets();
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

    // Получаем набор картинок для анимации атаки ГГ
    public static List<BitmapDrawable> getListBitmapOrcSlashing(Context context) {
        List<BitmapDrawable> frames = new ArrayList<>();
        List<Bitmap> listBitmap = new ArrayList<>();

        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_000.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_000.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_001.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_002.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_003.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_004.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_005.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_006.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_007.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_008.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_009.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_010.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Slashing_011.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/orc/slash/0_Orc_Idle_000.png"));

        for (int i = 0; i < listBitmap.size(); i++) {
            frames.add(new BitmapDrawable(context.getResources(), listBitmap.get(i)));
        }

        return frames;
    }

    // Получаем набор картинок для анимации получению урона Гоблина
    public static List<BitmapDrawable> getListBitmapGoblinHurt(Context context) {
        List<BitmapDrawable> frames = new ArrayList<>();
        List<Bitmap> listBitmap = new ArrayList<>();

        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Idle_000.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_000.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_001.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_002.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_003.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_004.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_005.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_006.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_007.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_008.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_009.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_010.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Hurt_011.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/hurt/0_Goblin_Idle_000.png"));

        for (int i = 0; i < listBitmap.size(); i++) {
            frames.add(new BitmapDrawable(context.getResources(), listBitmap.get(i)));
        }

        return frames;
    }

    // Получаем набор картинок для анимации смерти гоблина Гоблина
    public static List<BitmapDrawable> getListBitmapGoblinDying(Context context) {
        List<BitmapDrawable> frames = new ArrayList<>();
        List<Bitmap> listBitmap = new ArrayList<>();

        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_000.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_001.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_002.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_003.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_004.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_005.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_006.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_007.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_008.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_009.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_010.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_011.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_012.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_013.png"));
        listBitmap.add(getBitmapFromAssets(context, "images/goblin/dying/0_Goblin_Dying_014.png"));

        for (int i = 0; i < listBitmap.size(); i++) {
            frames.add(new BitmapDrawable(context.getResources(), listBitmap.get(i)));
        }

        return frames;
    }

}