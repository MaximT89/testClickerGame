package com.example.testclickergame.stats;

import com.example.testclickergame.R;

import java.util.Random;

public class SceneStats {
    public static int globalGold = 0;
    public static int maxLevelSceneProgress = 1;
    public static int currentLevelScene = 1;
    public static int currentPartLevelScene = 1;

    public static int getHpEnemy(int currentLevelScene) {
        int temp = 0;

        int percentScaleCurrentLevelScene = currentLevelScene * 15;

        int iteration1 = 10 * currentLevelScene;
        int iteration2 = (iteration1 * percentScaleCurrentLevelScene) / 100 + iteration1;

        if (currentPartLevelScene != 10) {
            // Генерация для жизний обычного моба
            temp = (iteration2 * (new Random().nextInt(10) + 1)) / 100 + iteration2;
        } else {
            // Генерация для жизней босса (босс появляется на каждой 10 части уровня)
            temp = ((iteration2 * (new Random().nextInt(10) + 1)) / 100 + iteration2) * 9;
        }

        return temp;
    }

    public static int getBackgroundResource() {
        int temp = R.drawable.game_background_1;
        if (currentLevelScene >= 1 && currentLevelScene <= 5) {
            temp = R.drawable.game_background_1;
        } else if (currentLevelScene > 5 && currentLevelScene <= 10) {
            temp = R.drawable.game_background_2;
        } else if (currentLevelScene > 10 && currentLevelScene <= 15) {
            temp = R.drawable.game_background_4;
        }
        return temp;
    }
}
