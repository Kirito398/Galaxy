package com.bg.galaxy.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 26.07.2016.
 */

public abstract class State extends Game {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStatesManager gsm;

    public State(GameStatesManager gsm){
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void render(SpriteBatch sb);
    public abstract void update(float dt);
    public abstract void dispose();
}
