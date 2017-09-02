package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.EnemyHandler;
import com.mygdx.game.characters.Enemy;
import com.mygdx.game.characters.HorizontalEnemy;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.characters.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends AbstractState {
    private Player player;

    private Texture background;
    private Border border;

    private EnemyHandler enemyHandler;

    public PlayState(GameStateManager game) {
        super(game);

        Texture playerTexture = new Texture("ninjaAnimation/Run__000.png");

        int newWidth = MyGdxGame.WIDTH / 5;
        int newHeight = newWidth * playerTexture.getHeight() / playerTexture.getWidth();


        border = new Border();

        player = new Player(newWidth, newHeight,
                new Vector2(border.width, 100/*1f * MyGdxGame.HEIGHT / 6*/),
                border.collBox1Left, border.collBox1Right,
                border.collBox2Left, border.collBox2Right,
                camera);

        enemyHandler = new EnemyHandler();

        initBackground();

        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

    }

    private void initBackground() {
        background = new Texture("background.jpg");
    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            player.jump();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();

        float cameraOffsetY = camera.position.y - player.getPosition().y;

        updatePlayer(deltaTime);

        camera.position.y = player.getPosition().y + cameraOffsetY;

        camera.update();
        border.update();

        enemyHandler.update(deltaTime);
    }

    private void updatePlayer(float deltaTime) {
        player.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, camera.position.x - camera.viewportWidth / 2,
                camera.position.y - camera.viewportHeight / 2,
                MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

        sb.end();
        player.render(sb);
        border.render(sb);
        enemyHandler.render(sb);
    }

    @Override
    public void dispose() {
        player.dispose();
        background.dispose();
        border.dispose();
    }

    private class Border {

        private TextureRegion textReg;
        private Vector2 pos1Left;
        private Vector2 pos1Right;
        private Vector2 pos2Left;
        private Vector2 pos2Right;

        private Rectangle collBox1Left;
        private Rectangle collBox1Right;
        private Rectangle collBox2Left;
        private Rectangle collBox2Right;

        private float width;
        private float height;

        public Border() {
            textReg = new TextureRegion(new Texture("Tiles/__tile.png"));

            width = 30;
            height = MyGdxGame.HEIGHT;


            pos1Left = new Vector2(0, 0);
            pos1Right = getRightfromLeft(pos1Left);

            pos2Left = new Vector2(0, MyGdxGame.HEIGHT);
            pos2Right = getRightfromLeft(pos2Left);

            collBox1Left = new Rectangle(pos1Left.x, pos1Left.y, width, height);
            collBox2Left = new Rectangle(pos2Left.x, pos2Left.y, width, height);

            collBox1Right = new Rectangle(pos1Right.x, pos1Right.y, width, height);
            collBox2Right = new Rectangle(pos2Right.x, pos2Right.y, width, height);
        }

        private Vector2 getRightfromLeft(Vector2 left) {
            return new Vector2(MyGdxGame.WIDTH - left.x - width, left.y);
        }

        void render(SpriteBatch sb) {

            sb.begin();
            sb.draw(textReg, pos1Left.x, pos1Left.y, width, height);
            sb.draw(textReg, pos2Left.x, pos2Left.y, width, height);

            textReg.flip(true, false);

            sb.draw(textReg, pos1Right.x, pos1Right.y, width, height);
            sb.draw(textReg, pos2Right.x, pos2Right.y, width, height);

            textReg.flip(true, false);

            sb.end();


        }

        void update() {
            if (camera.position.y - MyGdxGame.HEIGHT / 2 > pos1Left.y + height) {
                pos1Left = pos2Left.cpy();
                pos1Right = pos2Right.cpy();

                pos2Left = new Vector2(pos2Left.x, pos1Left.y + height);
                pos2Right = new Vector2(pos2Right.x, pos1Right.y + height);
            }


            collBox1Left.setPosition(pos1Left);
            collBox1Right.setPosition(pos1Right);
            collBox2Left.setPosition(pos2Left);
            collBox2Right.setPosition(pos2Right);
        }
        void dispose() {
            textReg.getTexture().dispose();
        }
    }
}
