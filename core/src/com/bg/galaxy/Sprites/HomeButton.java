package com.bg.galaxy.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 28.07.2016.
 */

public class HomeButton {
    public static final int MOVEMENT_UP=30;

    private Vector3 position;
    private Rectangle bounch;
    int speed;

    public HomeButton(float x, float y){
        position = new Vector3(x,y,0);
        bounch = new Rectangle(x,y,64,64);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void update(float dt){
        position.add(0,(MOVEMENT_UP+speed)*dt,0);
        bounch.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounch() {
        return bounch;
    }
}
