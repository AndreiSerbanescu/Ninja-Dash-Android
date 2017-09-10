package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.characters.*;

import java.util.*;

public class EnemyHandler {

    private Set<Enemy> enemies;
    private Player player;
    private float spawnBoundary;
    private DifficultyHandler diffHand;

    public EnemyHandler(Player player) {
        enemies = new LinkedHashSet<Enemy>();
        spawnBoundary = MyGdxGame.HEIGHT * 0.8f;
        this.player = player;
        diffHand = new DifficultyHandler(player);
        initEnemies();
    }

    private void initEnemies() {
        HorizontalEnemy.initTextures();
        DiagonalEnemy.initTextures();
        VerticalEnemy.initTextures();
    }

    public void update(final float deltaTime) {
        float spawnOffset = MyGdxGame.HEIGHT;


        float spawnBoundaryInc = diffHand.getSpawnIncremenet();

        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        diffHand.update();

        if (player.getPosition().y > spawnBoundary) {
            Random random = new Random();

            for (int i = 0; i < diffHand.getSpawnNumber(); i++) {
                Enemy newEnemy;
                int enemyType = random.nextInt(3);

                if (enemyType == 0) {
                    newEnemy= new VerticalEnemy(spawnBoundary + spawnOffset);
                } else if (enemyType == 1) {
                    newEnemy = new HorizontalEnemy(spawnBoundary + spawnOffset);
                } else {
                    newEnemy = new DiagonalEnemy(0, spawnBoundary + spawnOffset);
                }

                spawnBoundary += spawnBoundaryInc;
                enemies.add(newEnemy);
            }

        }

        deleteEnemies();
        for (Enemy enemy : enemies) {
            if (enemy.collides(player.getCollBox())) {
                if (!enemy.isDead()){
                    player.receiveAttack(enemy);
                }
            }
        }
    }



    private void deleteEnemies() {
        List<Enemy> enemiesToDelete = new ArrayList<Enemy>();

        for (Enemy enemy : enemies) {
            if (enemy.getPosition().y < deleteBoundary()) {
                enemiesToDelete.add(enemy);
            }
        }
        for (Enemy enemy : enemiesToDelete) {
            enemies.remove(enemy);
            enemy.dispose();
        }
    }

    private float deleteBoundary() {
        return player.getPosition().y - 500;
    }

    public void render(SpriteBatch sb) {
        for (Enemy enemy : enemies) {
            enemy.render(sb);
        }
    }

    public void kill(Enemy enemy) {
        enemies.remove(enemy);
        enemy.die();
    }

    public void dispose() {
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        enemies.clear();

        DiagonalEnemy.disposeTextures();
        HorizontalEnemy.disposeTextures();
        VerticalEnemy.disposeTextures();
    }

}
