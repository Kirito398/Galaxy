package com.bg.galaxy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.bg.galaxy.Sprites.Clicker;
import com.bg.galaxy.Sprites.ExitButton;
import com.bg.galaxy.Sprites.StartButton;
import com.bg.galaxy.Galaxy;


/**
 * Created by Kirito398 on 26.07.2016.
 */

public class MenuState extends State{
    private BitmapFont font;
    private StartButton start;
    private ExitButton exit;
    private Clicker clicker;
    private Vector3 touchPos;
    private Texture texture;
    private TextureRegion title,t_start,t_exit,t_clicker;

    public MenuState(GameStatesManager gsm){
        super(gsm);
        texture = new Texture("TextureRegion.png");
        font = new BitmapFont();
        title = new TextureRegion(texture,0,693,869,157);
        t_start = new TextureRegion(texture,256,100,256,256);
        t_exit = new TextureRegion(texture,0,100,256,256);
        t_clicker = new TextureRegion(texture,992,992,32,32);
        start = new StartButton(30,230);
        exit = new ExitButton(Galaxy.WIDTH-225,230);
        clicker = new Clicker(0,0);
        touchPos = new Vector3(0,0,0);

        camera.setToOrtho(false,Galaxy.WIDTH,Galaxy.HEIGTH);

        Galaxy.ShowAd(true);
    }



    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            clicker.setPosition(touchPos);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(t_clicker,clicker.getPosition().x,clicker.getPosition().y);
        sb.draw(title,Galaxy.WIDTH/2-434/2,Galaxy.HEIGTH/2+165,434,78);
        sb.draw(t_start,start.getPosition().x,start.getPosition().y,start.SIZE,start.SIZE);
        sb.draw(t_exit,exit.getPosition().x,exit.getPosition().y,exit.SIZE,exit.SIZE);
        sb.end();

        if(clicker.getBounds().overlaps(start.getBounds())){
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
            //gsm.set(new PlayState(gsm));
            gsm.set(new PlayerState(gsm));
        }

        if(clicker.getBounds().overlaps(exit.getBounds())){
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
            System.out.println("Exit!");
            //gsm.set(new GameOverState(gsm));
            System.exit(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        font.dispose();
        texture.dispose();
    }
}