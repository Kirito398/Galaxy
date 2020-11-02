package com.bg.galaxy;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bg.galaxy.States.GameStatesManager;
import com.bg.galaxy.States.MenuState;

public class Galaxy extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGTH = 800;

	public static IActivityRequestHandler application;

	private GameStatesManager gsm;

	SpriteBatch batch;

	public Galaxy(IActivityRequestHandler app){
		application = app;
	}

	public static void ShowAd(boolean show){
		application.showAdMob(show);
	}

	public static void ShowInterstitial(int mode){
		application.showInterstitial(mode);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(0.20f, 0.20f, 0.20f, 1);
		gsm = new GameStatesManager();
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.render(batch);
		gsm.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
