package oclu.ludumdare.elements;

import org.newdawn.slick.*;
import oclu.ludumdare.states.Game;

public class Powerup {

    SpriteSheet sheet;
    Image sprite;

    public int powerType;

    float speed = 0.2f;

    float posX, posY;

    public boolean isDisappeared = false;

    Sound collect, powerup;

    int cooldown = 0;

    public boolean isActive = false;


    public Powerup(SpriteSheet sheet, float posX, float posY) throws SlickException {

        double random = Math.random();

        this.sheet = sheet;


        sprite = sheet.getSubImage(48,64,16,16);
        powerType = 1;
        /*/ if (random > .95) {
            sprite = sheet.getSubImage(32, 64, 16, 16);
            powerType = 0;
        }else if (random > .80) {
            sprite = sheet.getSubImage(48, 64, 16, 16);
            powerType = 1;
        }else if (random > .72) {
            sprite = sheet.getSubImage(64, 64, 16, 16);
            powerType = 2;
        }else if (random > .65) {
            sprite = sheet.getSubImage(80, 64, 16, 16);
            powerType = 3;
        }else {
            isDisappeared = true;
        }
    /*/
        if (sprite != null) {
            sprite = sprite.getScaledCopy(32, 32);
        }

        this.posX = posX;
        this.posY = posY;

        collect = new Sound("oclu/ludumdare/res/sounds/collect.wav");
        powerup = new Sound("oclu/ludumdare/res/sounds/powerup.wav");

    }

    public void fall(int delta) {
        posY += delta * speed;

        if (posY >= 600) {
            isDisappeared = true;
        }
    }

    public boolean isDisappeared() {
        return isDisappeared;
    }

    public void collect() {
        isDisappeared = true;

        collect.play(1f, 0.1f);
        activate();
    }

    public void activate() {
        powerup.play(1f, 0.1f);
        if (powerType == 0) {
            // potato
        }else if (powerType == 1) {
            Game.plyr.isLarge = true;
        }else if (powerType == 2) {
            Game.isTimeWarp = true;
        }else if (powerType == 3) {
            Game.plyr.isDoubleScore = true;
        }
        startCountdown();
        isActive = true;
    }

    public void startCountdown() {
        if (powerType == 0) {
            // 5 seconds
        }else if (powerType == 1) {
            // 20 seconds

            if (cooldown < 500) {
                cooldown++;
            }else {

            }
        }else if (powerType == 2) {
            // 5-10 seconds
        }else if (powerType == 3) {
            // 30 seconds
        }

        deactivate();
    }

    void deactivate() {
        if (powerType == 0) {
            // un-potato
        }else if (powerType == 1) {
            Game.plyr.isLarge = false;
        }else if (powerType == 2) {
            Game.isTimeWarp = false;
        }else if (powerType == 3) {
            Game.plyr.isDoubleScore = false;
        }
        isActive = false;
    }

    public void draw() {
        if (sprite != null) {
            sprite.draw(posX, posY);
        }
    }

}
