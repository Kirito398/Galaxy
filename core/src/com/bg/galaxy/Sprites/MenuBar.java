package com.bg.galaxy.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirito398 on 28.07.2016.
 */

public class MenuBar {
    public static final int MOVEMENT = 30;
    private Vector3 position;
    int speed;

    public MenuBar(float x, float y){
        position = new Vector3(x,y,0);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void update(float dt){
        position.add(0,(MOVEMENT + speed)*dt,0);
    }

    public Vector3 getPosition() {
        return position;
    }
}
