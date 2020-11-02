package com.bg.galaxy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.bg.galaxy.Sprites.Clicker;
import com.bg.galaxy.Sprites.NextButton;
import com.bg.galaxy.Sprites.PlayButton;
import com.bg.galaxy.Sprites.PrevButton;
import com.bg.galaxy.Galaxy;

/**
 * Created by Kirito398 on 13.08.2016.
 */

public class PlayerState extends State {
    private BitmapFont font,font1,font2;
    private Texture texture;
    private TextureRegion next, prev, player, t_clicker, p_doubleshot,p_standart,select,t_play,t_buy_noclick,t_buy,t_coins;
    private Clicker clicker;
    private NextButton next_btn;
    private PrevButton prev_btn;
    private PlayButton play_btn;
    private Vector3 touchPos;
    private int mode;
    private int[] price;
    private char[] modes;
    private long coins;
    private String record_std, record_ds, record_ms, text;

    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public PlayerState (GameStatesManager gsm){
        super(gsm);
        Gdx.input.setCatchBackKey(true);

        camera.setToOrtho(false, Galaxy.WIDTH,Galaxy.HEIGTH);
        clicker = new Clicker(0,0);
        touchPos = new Vector3(0,0,0);
        next_btn = new NextButton((float)400,(float)Galaxy.HEIGTH/2-32);
        prev_btn = new PrevButton(42,Galaxy.HEIGTH/2-32);
        play_btn = new PlayButton(Galaxy.WIDTH/2-67,200);
        mode = 1;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = 20;
        parameter.color = Color.GRAY;
        font = generator.generateFont(parameter);
        parameter.size = 10;
        font1 = generator.generateFont(parameter);
        parameter.color = Color.YELLOW;
        font2 = generator.generateFont(parameter);
        generator.dispose();

        texture = new Texture("TextureRegion.png");
        prev = new TextureRegion(texture,544,32,38,64);
        next = new TextureRegion(texture,582,32,38,64);
        p_standart = new TextureRegion(texture,544,0,32,32);
        t_clicker = new TextureRegion(texture,992,992,32,32);
        p_doubleshot = new TextureRegion(texture,892,356,96,64);
        t_play = new TextureRegion(texture,758,356,134,56);
        t_buy_noclick = new TextureRegion(texture,758,468,134,56);
        t_buy = new TextureRegion(texture,758,412,134,56);
        t_coins = new TextureRegion(texture,576,0,32,32);

        FileHandle file = Gdx.files.local("score.txt");
        record_std = file.readString();
        file = Gdx.files.local("scoreds.txt");
        record_ds = file.readString();
        file = Gdx.files.local("scorems.txt");
        record_ms = file.readString();
        file = Gdx.files.local("coins.txt");
        text = file.readString();
        coins = Long.decode(text);
        file = Gdx.files.local("modes.txt");
        text = file.readString();

        modes = new char[10];

        if(text.length() == 2){text += "0";}
        for(int i=1;i<=text.length();i++){
            modes[i] = text.charAt(i-1);
        }

        price = new int[10];
        price[1] = 0;
        price[2] = 3000;
        price[3] = 10000;

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
        font.draw(sb,"Choose player", Galaxy.WIDTH/5,700);
        sb.draw(next,next_btn.getPosition().x,next_btn.getPosition().y);
        sb.draw(prev,prev_btn.getPosition().x,prev_btn.getPosition().y);
        sb.draw(t_clicker,clicker.getPosition().x,clicker.getPosition().y);
        switch (mode){
            case 1:{
                font.draw(sb,"Standart",Galaxy.WIDTH/3-10,Galaxy.HEIGTH/2+128);
                sb.draw(player, Galaxy.WIDTH/2-16,Galaxy.HEIGTH/2-64);
                if(record_std!="0"){font1.draw(sb,"Record: "+record_std,Galaxy.WIDTH/3+32,Galaxy.HEIGTH/2+64);}
                break;
            }
            case 2:{
                font.draw(sb,"DoubleShot",Galaxy.WIDTH/3-26,Galaxy.HEIGTH/2+128);
                sb.draw(player, Galaxy.WIDTH/2-48,Galaxy.HEIGTH/2-64);
                if(record_ds!="0"){font1.draw(sb,"Record: "+record_ds,Galaxy.WIDTH/3+32,Galaxy.HEIGTH/2+64);}
                break;
            }
            case 3:{
                font.draw(sb,"AutoShot",Galaxy.WIDTH/3-10,Galaxy.HEIGTH/2+128);
                sb.draw(player, Galaxy.WIDTH/2-16,Galaxy.HEIGTH/2-64);
                if(record_ms!="0"){font1.draw(sb,"Record: "+record_ms,Galaxy.WIDTH/3+32,Galaxy.HEIGTH/2+64);}
                break;
            }
        }
        sb.draw(t_coins,16,Galaxy.HEIGTH-32,16,16);
        font2.draw(sb,""+coins,48,Galaxy.HEIGTH-16);
        if(modes[mode]=='0'){
            sb.draw(t_coins,Galaxy.WIDTH/2-50,180,16,16);
            font2.draw(sb,""+price[mode],Galaxy.WIDTH/2-18,196);
        }
        sb.draw(select, play_btn.getPosition().x,play_btn.getPosition().y);
        sb.end();

        if(clicker.getBounds().overlaps(next_btn.getBounds())){
            if(mode<3){
                mode++;
            }
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
        }

        if(clicker.getBounds().overlaps(prev_btn.getBounch())){
            if(mode>1){
                mode--;
            }
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
        }

        if(clicker.getBounds().overlaps(play_btn.getBounch())){
            if(modes[mode]=='2') {
                gsm.set(new PlayState(gsm, mode));
            }
            if(modes[mode]=='0' && coins>=price[mode]){
                coins=coins-price[mode];
                modes[mode]='2';
                Save();
            }
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        switch (mode){
            case 1: {player = p_standart;break;}
            case 2: {player = p_doubleshot;break;}
            case 3: {player = p_standart;break;}
        }

        switch (modes[mode]){
            case '0':{
                if(coins>=price[mode]){
                    select = t_buy;
                }else {
                    select = t_buy_noclick;
                }
                break;
            }
            case '2':{select = t_play; break;}
        }
    }

    @Override
    public void create() {

    }

    public void Save(){
        FileHandle file = Gdx.files.local("modes.txt");
        String str = "";
        for(int i=1;i<modes.length;i++){
            str+=modes[i];
        }
        file.writeString(str,false);

        file = Gdx.files.local("coins.txt");
        file.writeString(""+coins,false);
    }

    @Override
    public void dispose() {
        font.dispose();
        font1.dispose();
        texture.dispose();
    }
}