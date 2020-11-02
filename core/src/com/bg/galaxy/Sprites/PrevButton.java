package com.bg.galaxy.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 13.08.2016.
 */

public class PrevButton {
    private Vector3 position;
    private Rectangle bounch;

    public PrevButton(float x, float y){
        position = new Vector3(x,y,0);
        bounch = new Rectangle(x,y,64,64);
        bounch.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounch() {
        return bounch;
    }
}
