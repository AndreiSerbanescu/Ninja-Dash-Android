package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameStateManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;

public class PlayState extends AbstractState {
    private Player player;

    public PlayState(GameStateManager game) {
        super(game);

        Texture playerTexture = new Texture("ninjaAnimation/Run__000.png");

        int newWidth = MyGdxGame.WIDTH / 5;
        int newHeight = newWidth * playerTexture.getHeight() / playerTexture.getWidth();

        player = new Player(newWidth,
                newHeight,
                0, MyGdxGame.HEIGHT / 6);

        camera.setToOrtho(false);
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
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        player.render(sb);
    }

    @Override
    public void dispose() {

    }
}
