package com.bg.galaxy.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 13.08.2016.
 */

public class NextButton {
    private Vector3 position;
    private Rectangle bounds;

    public NextButton(float x, float y){
        position = new Vector3(x,y,0);
        bounds = new Rectangle(x,y,64,64);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}