package com.bg.galaxy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.bg.galaxy.Sprites.Bullet;
import com.bg.galaxy.Sprites.Clicker;
import com.bg.galaxy.Sprites.Enemy;
import com.bg.galaxy.Sprites.HomeButton;
import com.bg.galaxy.Sprites.MenuBar;
import com.bg.galaxy.Sprites.Player;
import com.bg.galaxy.Galaxy;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kirito398 on 26.07.2016.
 */

public class PlayState extends State {

    private Player player;
    private MenuBar menu;
    private HomeButton home;
    private Array<Enemy> enemies;
    private Array<Bullet> bullets;
    private BitmapFont font;
    private int enemy_count = 15;
    private Random rand;
    private int empty,enemy_y=736, mode=1, speed;
    private long score=0;
    private Vector3 touchPos;
    private Clicker clicker;
    private boolean GameOver;
    private Texture texture;
    private TextureRegion t_clicker,t_player,t_enemy,t_bullet,t_menu,t_home;
    private Sound s_shoot;

    private final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private long lastDropTime, lastShotTime;

    public PlayState(GameStatesManager gsm, int modes) {
        super(gsm);
        mode = modes;
        System.out.print("Mode:" + mode);

        camera.setToOrtho(false, Galaxy.WIDTH, Galaxy.HEIGTH);

        player = new Player(Galaxy.WIDTH / 2, mode);
        enemies = new Array<Enemy>();
        bullets = new Array<Bullet>();
        clicker = new Clicker(0, 0);
        touchPos = new Vector3(0, 0, 0);
        font = new BitmapFont();
        rand = new Random();
        menu = new MenuBar(0, Galaxy.HEIGTH - 84);
        home = new HomeButton(Galaxy.WIDTH - 74, Galaxy.HEIGTH - 74);

        switch(mode){
            case 1: {speed = -10; break;}
            case 2: {speed = 10; break;}
            case 3: {speed = 10; break;}
        }

        player.setSpeed(speed);
        home.setSpeed(speed);
        menu.setSpeed(speed);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = 15;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

        GameOver = false;

        texture = new Texture("TextureRegion.png");
        t_clicker = new TextureRegion(texture, 992, 992, 32, 32);
        t_player = new TextureRegion(texture, 544, 0, 32, 32);
        t_enemy = new TextureRegion(texture, 480, 0, 32, 32);
        t_bullet = new TextureRegion(texture, 512, 0, 32, 32);
        t_menu = new TextureRegion(texture, 0, 0, 480, 100);
        t_home = new TextureRegion(texture, 480, 32, 64, 64);

        s_shoot = Gdx.audio.newSound(Gdx.files.internal("shot3.wav"));

        spawnEnemies();

        Galaxy.ShowAd(false);
        Galaxy.ShowInterstitial(2);
    }

    @Override
    protected void handleInput() {
        int x = (int)player.getPosition().x, pos;
        if(x-(32*(x/32))>16){
            pos = 32*((x/32)+1);
        }else{
            pos = 32*((x/32));
        }

        if(Gdx.input.justTouched()){
            if(TimeUtils.nanoTime() - lastShotTime > 100000000) {
                switch (mode) {
                    case 1: {
                        bullets.add(new Bullet(pos, player.getPosition().y));
                        s_shoot.play();
                        break;
                    }
                    case 2: {
                        bullets.add(new Bullet(pos + 32, player.getPosition().y));
                        bullets.add(new Bullet(pos - 32, player.getPosition().y));
                        s_shoot.play();
                        break;
                    }
                }
                lastShotTime = TimeUtils.nanoTime();
            }


            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            clicker.setPosition(touchPos);
        }

        if(Gdx.input.isTouched() && mode == 3){
            if(TimeUtils.nanoTime() - lastShotTime > 100000000) {
                bullets.add(new Bullet(pos, player.getPosition().y));
                s_shoot.play();
                lastShotTime = TimeUtils.nanoTime();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(t_clicker,clicker.getPosition().x,clicker.getPosition().y);
        sb.draw(t_player,player.getPosition().x,player.getPosition().y);
        for(Enemy enemy:enemies){
            sb.draw(t_enemy,enemy.getPosition().x,enemy.getPosition().y);
        }
        for(Bullet bullet:bullets){
            sb.draw(t_bullet,bullet.getPosition().x,bullet.getPosition().y+32);
        }

        sb.draw(t_menu, menu.getPosition().x, menu.getPosition().y);
        sb.draw(t_home, home.getPosition().x,home.getPosition().y);
        font.draw(sb,"Score: "+ score,10,menu.getPosition().y+50);
        sb.end();

        if(TimeUtils.nanoTime() - lastDropTime > 600000000) spawnEnemies();//600000000 - Norm

        Iterator<Enemy> iter = enemies.iterator();
        while(iter.hasNext()){
            Enemy enemy = iter.next();
            if(enemy.getPosition().y < camera.position.y-camera.viewportHeight/2) {iter.remove();GameOver=true;}

            Iterator<Bullet> bullet_iter = bullets.iterator();
            while(bullet_iter.hasNext()){
                Bullet bullet = bullet_iter.next();
                if(bullet.getPosition().y > camera.position.y+camera.viewportHeight/2) {bullet_iter.remove();}
                if(bullet.getBounds().overlaps(enemy.getBounds())){
                    bullet_iter.remove();
                    iter.remove();
                    score++;
                }
            }
        }

        if(clicker.getBounds().overlaps(home.getBounch())){
            touchPos.set(0,0,0);
            clicker.setPosition(touchPos);
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        menu.update(dt);
        home.update(dt);
        camera.position.y = player.getPosition().y + camera.viewportHeight/2;

        for(Enemy enemy:enemies){
            enemy.update(dt);
        }
        for(Bullet bullet:bullets){
            bullet.update(dt);
        }
        camera.update();

        if(GameOver){
            GameStatesManager.SCORE = score;
            Galaxy.ShowInterstitial(1);
            gsm.set(new GameOverState(gsm,mode));
        }
    }

    private void spawnEnemies(){
        for(int i=0;i<enemy_count;i++){
            empty = rand.nextInt(2);
            if(empty == 1)
                enemies.add(new Enemy(i*Enemy.WIDTH,enemy_y));
        }
        lastDropTime = TimeUtils.nanoTime();
        enemy_y+=32;
    }

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        font.dispose();
        texture.dispose();
        s_shoot.dispose();
    }
}