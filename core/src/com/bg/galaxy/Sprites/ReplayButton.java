package com.bg.galaxy.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 28.07.2016.
 */

public class ReplayButton {
    private Vector3 position;
    private Rectangle bounch;

    public ReplayButton(float x, float y){
        position = new Vector3(x,y,0);
        bounch = new Rectangle(x,y,204,204);
        bounch.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounch() {
        return bounch;
    }
}
