package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.characters.Enemy;
import com.mygdx.game.characters.HorizontalEnemy;
import com.mygdx.game.characters.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EnemyHandler {

    private Set<Enemy> enemies;
    private Player player;
    private float spawnBoundary;

    public EnemyHandler(Player player) {
        enemies = new LinkedHashSet<Enemy>();
        spawnBoundary = MyGdxGame.HEIGHT * 0.8f;
        this.player = player;
        initEnemies();
    }

    private void initEnemies() {

    }

    public void update(final float deltaTime) {

        float spawnOffset = MyGdxGame.HEIGHT;
        float spawnBoundaryInc = MyGdxGame.HEIGHT / 3;

        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        if (player.getPosition().y > spawnBoundary) {
            Enemy newEnemy = new HorizontalEnemy(spawnBoundary + spawnOffset);

            spawnBoundary += spawnBoundaryInc;
            enemies.add(newEnemy);
        }


        deleteEnemies();

        for (Enemy enemy : enemies) {
            if (enemy.collides(player.getCollBox())) {
                if (player.isJumping()) {
                    enemy.die();
                } else if (!player.isJumping() && !enemy.isDead()){
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

}
