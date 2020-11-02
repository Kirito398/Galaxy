package com.bg.galaxy.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 26.07.2016.
 */

public class Enemy {
    public static final int MOVEMENT = 50;
    public static final int WIDTH = 32;
    private Vector3 position;
    private Rectangle bounds;

    public Enemy(float x, float y){
        position = new Vector3(x,y,0);
        bounds = new Rectangle(x,y,32,32);
        bounds.setPosition(position.x,position.y);
    }

    public void update(float dt){

    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}