package com.example.testclickergame.stats;

import java.util.Random;

public class SceneStats {
    public static int globalGold = 0;
    public static int maxLevelSceneProgress = 1;
    public static int currentLevelScene = 3;

    public static int getHpEnemy(int currentLevelScene){
        int temp = 0;

        int percentScaleCurrentLevelScene = currentLevelScene * 15;

        int iteration1 = 10 * currentLevelScene;
        int iteration2 = (iteration1 * percentScaleCurrentLevelScene) / 100 + iteration1;

        temp = (iteration2 * (new Random().nextInt(10) + 1)) / 100 + iteration2;

        return temp;
    }
}
