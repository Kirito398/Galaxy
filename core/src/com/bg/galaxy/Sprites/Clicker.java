package com.bg.galaxy.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 27.07.2016.
 */

public class Clicker {
    private Vector3 position;
    private Rectangle bounds;

    public Clicker(float x, float y){
        position = new Vector3(x,y,0);
        bounds = new Rectangle(x,y,32,32);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(Vector3 touchPos){
        position.set(touchPos);
        bounds.setPosition(position.x,position.y);
    }
}