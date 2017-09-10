package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.characters.Player;

public class DifficultyHandler {

    private Player player;
    private Difficulty difficulty;
    private float diffTresholdMedium = MyGdxGame.HEIGHT * 2;
    private float diffTresholdHard = diffTresholdMedium * 2;


    public DifficultyHandler(Player player) {
        this.player = player;
        difficulty = Difficulty.EASY;
    }

    public void update() {
        if (player.getPosition().y > diffTresholdHard) {
            difficulty = Difficulty.HARD;
        } else if (player.getPosition().y > diffTresholdMedium) {
            difficulty = Difficulty.MEDIUM;
        } else {
            difficulty = Difficulty.EASY;
        }
    }

    public int getSpawnIncremenet() {
        switch (difficulty) {
            case EASY:
                return MyGdxGame.HEIGHT / 2;
            case MEDIUM:
                return MyGdxGame.HEIGHT / 4;
            case HARD:
                return MyGdxGame.HEIGHT / 7;
            default:
                return MyGdxGame.HEIGHT;
        }
    }

    public int getSpawnNumber() {
        switch (difficulty) {
            case EASY:
                return 1;
            case MEDIUM:
                return 2;
            case HARD:
                return 4;

            default:
                return 0;
        }
    }
}
