package com.bg.galaxy.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 27.07.2016.
 */

public class ExitButton {
    public static final int SIZE = 204;
    private Vector3 position;
    private Rectangle bounds;

    public ExitButton(float x, float y){
        position = new Vector3(x,y,0);
        bounds = new Rectangle(x,y,SIZE,SIZE);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}