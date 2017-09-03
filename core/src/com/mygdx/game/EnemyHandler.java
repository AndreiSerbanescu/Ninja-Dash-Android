package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.characters.Enemy;
import com.mygdx.game.characters.HorizontalEnemy;
import com.mygdx.game.characters.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//TODO remove hard-coding and magic variables

public class EnemyHandler {

    private Set<Enemy> enemies;
    private Player player;
    private float spawnBoundary;

    public EnemyHandler(Player player) {
        enemies = new LinkedHashSet<Enemy>();
        spawnBoundary = 500;
        this.player = player;
        initEnemies();
    }

    private void initEnemies() {
        for (int i = 0; i < 3; i++) {
            enemies.add(new HorizontalEnemy(0, 200 * i));
        }
    }

    public void update(final float deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }

        if (player.getPosition().y > spawnBoundary) {
            Enemy newEnemy = new HorizontalEnemy(spawnBoundary + 1000);

            spawnBoundary += 300;
            enemies.add(newEnemy);
        }


        deleteEnemies();

        for (Enemy enemy : enemies) {
            if (enemy.collides(player.getCollBox())) {
                System.out.println(enemy + " enemy overlaps with player!");
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
