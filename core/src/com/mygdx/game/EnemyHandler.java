package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.characters.Enemy;
import com.mygdx.game.characters.HorizontalEnemy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EnemyHandler {

    private Set<Enemy> enemies;

    public EnemyHandler() {
        enemies = new LinkedHashSet<Enemy>();
        initEnemies();
    }

    private void initEnemies() {
        for (int i = 0; i < 10; i++) {
            enemies.add(new HorizontalEnemy(0, 100 * i));
        }
    }

    public void update(final float deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
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
