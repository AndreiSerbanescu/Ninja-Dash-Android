package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;

public class PlayState extends AbstractState {
    private Player player;

    private Texture background;
    private Vector2 bgPos1;
    private Vector2 bgPos2;

    public PlayState(GameStateManager game) {
        super(game);

        Texture playerTexture = new Texture("ninjaAnimation/Run__000.png");

        int newWidth = MyGdxGame.WIDTH / 5;
        int newHeight = newWidth * playerTexture.getHeight() / playerTexture.getWidth();

        player = new Player(newWidth,
                newHeight,
                0, MyGdxGame.HEIGHT / 6);

        camera.setToOrtho(false);

        initBackground();
    }

    private void initBackground() {
        background = new Texture("background.jpg");
        bgPos1 = new Vector2(0, 0);
        bgPos2 = new Vector2(0, MyGdxGame.HEIGHT);
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

        player.update(deltaTime);

        camera.position.y = player.getPosition().y + cameraOffsetY;

        camera.update();

        if (camera.position.y - camera.viewportHeight / 2 > bgPos1.y + MyGdxGame.HEIGHT) {
            bgPos1 = bgPos2.cpy();
            bgPos2 = new Vector2(0, MyGdxGame.HEIGHT + bgPos1.y);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, bgPos1.x, bgPos1.y, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.draw(background, bgPos2.x, bgPos2.y, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

        sb.end();
        player.render(sb);
    }

    @Override
    public void dispose() {

    }
}
