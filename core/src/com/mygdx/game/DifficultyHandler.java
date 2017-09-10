package com.mygdx.game;

import com.mygdx.game.characters.Player;

public class DifficultyHandler {

    private Player player;
    private Difficulty difficulty;

    private float easyMedTreshold = MyGdxGame.HEIGHT * 3;
    private float mediumTreshold = easyMedTreshold * 2;
    private float mediumHardTreshold = mediumTreshold * 2;
    private float hardTreshold = mediumHardTreshold  * 2;
    private float ultraHardTreshold = hardTreshold * 2;


    public DifficultyHandler(Player player) {
        this.player = player;
        difficulty = Difficulty.EASY;
    }

    public void update() {
        if (player.getPosition().y > ultraHardTreshold) {
            difficulty = Difficulty.ULTRAHARD;
        } else if (player.getPosition().y > hardTreshold) {
            difficulty = Difficulty.HARD;
        } else if (player.getPosition().y > mediumHardTreshold) {
            difficulty = Difficulty.MEDIUMHARD;
        } else if (player.getPosition().y > mediumTreshold){
            difficulty = Difficulty.MEDIUM;
        } else if (player.getPosition().y > easyMedTreshold) {
            difficulty = Difficulty.EASYMED;
        } else {
            difficulty = Difficulty.EASY;
        }
    }

    public float getSpawnIncremenet() {
        System.out.println(difficulty + " " + player.getPosition().y);
        switch (difficulty) {
            case EASY:
                return MyGdxGame.HEIGHT / 1.5f;
            case EASYMED:
                return MyGdxGame.HEIGHT / 2f;
            case MEDIUM:
                return MyGdxGame.HEIGHT / 3f;
            case MEDIUMHARD:
                return MyGdxGame.HEIGHT / 4f;
            case HARD:
                return MyGdxGame.HEIGHT / 5f;
            case ULTRAHARD:
                return MyGdxGame.HEIGHT / 6f;
            default:
                return MyGdxGame.HEIGHT;
        }
    }

    public int getSpawnNumber() {
        switch (difficulty) {
            case EASY:
                return 1;
            case EASYMED:
                return 1;
            case MEDIUM:
                return 2;
            case MEDIUMHARD:
                return 2;
            case HARD:
                return 3;
            case ULTRAHARD:
                return 4;
            default:
                return 0;
        }
    }
}
