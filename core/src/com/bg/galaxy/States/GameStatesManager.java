package com.bg.galaxy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Kirito398 on 26.07.2016.
 */

public class GameStatesManager {
    private Stack<State> states;
    public static long SCORE = 0;

    public GameStatesManager(){
        states = new Stack<State>();

        boolean score = Gdx.files.local("score.txt").exists();
        boolean scoreds = Gdx.files.local("scoreds.txt").exists();
        boolean coins = Gdx.files.local("coins.txt").exists();
        boolean modes = Gdx.files.local("modes.txt").exists();
        boolean scorems = Gdx.files.local("scorems.txt").exists();

        if(!score){
            FileHandle file1 = Gdx.files.local("score.txt");
            file1.writeString("0",false);
        }

        if(!scoreds){
            FileHandle file1 = Gdx.files.local("scoreds.txt");
            file1.writeString("0",false);
        }

        if(!scorems){
            FileHandle file1 = Gdx.files.local("scorems.txt");
            file1.writeString("0",false);
        }

        if(!coins){
            FileHandle file1 = Gdx.files.local("coins.txt");
            file1.writeString("0",false);
        }

        if(!modes){
            FileHandle file1 = Gdx.files.local("modes.txt");
            file1.writeString("200",false);
        }
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void update(float dt){
        states.peek().update(dt);
    }
}
