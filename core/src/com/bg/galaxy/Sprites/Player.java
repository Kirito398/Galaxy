package com.bg.galaxy.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.bg.galaxy.Galaxy;

/**
 * Created by Kirito398 on 26.07.2016.
 */

public class Player {
    public static final int MOVEMENT = 150;
    public static final int MOVEMENT_UP = 30;
    private Vector3 position;
    private float Accel=0;
    int speed, mode, left, right;

    public Player(float x, int modes){
        position = new Vector3(x,0,0);
        mode = modes;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void update(float dt){
        Accel = Gdx.input.getAccelerometerX()*-1;
        position.add(MOVEMENT * dt * Accel, (MOVEMENT_UP + speed)*dt, 0);

        switch(mode){
            case 1: {right = 32; left = 0; break;}
            case 2: {right = 64; left = 32; break;}
            case 3: {right = 32; left = 0; break;}
        }

        if(position.x < left){
            position.x = left;
        }

        if(position.x > Galaxy.WIDTH-right){
            position.x = Galaxy.WIDTH-right;
        }
    }

    public Vector3 getPosition() {
        return position;
    }
}