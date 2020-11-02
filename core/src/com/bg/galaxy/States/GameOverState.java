package com.bg.galaxy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.bg.galaxy.Sprites.Clicker;
import com.bg.galaxy.Sprites.ExitButton;
import com.bg.galaxy.Sprites.ReplayButton;
import com.bg.galaxy.Galaxy;

/**
 * Created by Kirito398 on 28.07.2016.
 */

public class GameOverState extends State {

    private Texture texture;
    private TextureRegion t_gameover,t_replay,t_home,t_clicker,t_coins;
    private ReplayButton replay;
    private ExitButton home;
    private Clicker clicker;
    private Vector3 touchPos;
    private BitmapFont font, r_font,font2,font1;
    private long t_score,score;
    private Sound s_score;
    private long record,coins;
    private float time;
    private int mode=1;
    private String files;
    private boolean f_record=false, record_show=false;

    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public GameOverState(GameStatesManager gsm, int modes){
        super(gsm);
        time = TimeUtils.nanoTime();
        mode = modes;

        camera.setToOrtho(false,Galaxy.WIDTH,Galaxy.HEIGTH);

        texture = new Texture("TextureRegion.png");
        t_gameover = new TextureRegion(texture,0,511,294,182);
        t_replay = new TextureRegion(texture,716,100,204,204);
        t_home = new TextureRegion(texture,512,100,204,204);
        t_clicker = new TextureRegion(texture,992,992,32,32);
        t_coins = new TextureRegion(texture,576,0,32,32);
        clicker = new Clicker(0,0);
        touchPos = new Vector3(0,0,0);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = 25;
        parameter.color = Color.YELLOW;
        font = generator.generateFont(parameter);
        parameter.size = 15;
        parameter.color = Color.GRAY;
        r_font = generator.generateFont(parameter);
        parameter.color = Color.YELLOW;
        parameter.size = 10;
        font2 = generator.generateFont(parameter);
        parameter.color = Color.RED;
        parameter.size = 15;
        font1 = generator.generateFont(parameter);
        generator.dispose();

        replay = new ReplayButton(30,130);
        home = new ExitButton(Galaxy.WIDTH-225,130);

        switch (mode){
            case 1: {files = "score.txt"; break; }
            case 2: {files = "scoreds.txt"; break; }
            case 3: {files = "scorems.txt"; break; }
        }

        FileHandle file = Gdx.files.local(files);
        String text = file.readString();
        record = Long.valueOf(text);
        score = GameStatesManager.SCORE;

        if(score > record){
            record = score;
            FileHandle file1 = Gdx.files.local(files);
            file1.writeString(""+score,false);
            f_record = true;
        }

        file = Gdx.files.local("coins.txt");
        text = file.readString();
        coins = Long.decode(text);
        long save = coins+(score / 10);
        file.writeString(""+save,false);

        t_score = 0;
        s_score = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
        s_score.play();

        Galaxy.ShowAd(true);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() && TimeUtils.nanoTime()-time >= 1000000000 && score==t_score){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            clicker.setPosition(touchPos);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb,"Score:", Galaxy.WIDTH/6, 470);
        font.draw(sb," "+t_score, Galaxy.WIDTH/2, 470);
        r_font.draw(sb,"Record:", Galaxy.WIDTH/4, 400);
        r_font.draw(sb," "+record, Galaxy.WIDTH/2, 400);
        sb.draw(t_clicker,clicker.getPosition().x,clicker.getPosition().y);
        sb.draw(t_gameover,Galaxy.WIDTH/2-147,Galaxy.HEIGTH/2+166);
        sb.draw(t_replay,replay.getPosition().x,replay.getPosition().y);
        sb.draw(t_home,home.getPosition().x,home.getPosition().y);
        sb.draw(t_coins,16,Galaxy.HEIGTH-32,16,16);
        font2.draw(sb,""+coins,48,Galaxy.HEIGTH-16);
        if(record_show)font1.draw(sb,"New Record!",Galaxy.WIDTH/4+20,530);
        sb.end();

        if (clicker.getBounds().overlaps(replay.getBounch())){
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
            GameStatesManager.SCORE = 0;
            gsm.set(new PlayState(gsm,mode));

        }

        if (clicker.getBounds().overlaps(home.getBounds())){
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
            GameStatesManager.SCORE = 0;
            gsm.set(new MenuState(gsm));
        }

        if(t_score<score){
            t_score++;
            if(t_score % 10 == 0) {
                coins++;
            }
            s_score.play();
            time = TimeUtils.nanoTime();
        }

        if(t_score==score && f_record){
            record_show=true;
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
        texture.dispose();
        s_score.dispose();
    }
}