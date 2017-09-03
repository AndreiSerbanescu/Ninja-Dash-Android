package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.states.StartState;

public class MyGdxGame extends ApplicationAdapter {
	public static final int WIDTH = 350;
	public static final int HEIGHT = 700;

	public static final float BORDERWIDTH = WIDTH / 10;

	public static final String TITLE = "Ave Games";

    private SpriteBatch batch;
    private Texture img;
	private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gsm = new GameStateManager();

		gsm.push(new StartState(gsm));

        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
	}


	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
