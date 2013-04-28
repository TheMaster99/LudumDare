package oclu.ludumdare.elements;

import org.newdawn.slick.*;
import java.lang.Math;
import oclu.ludumdare.states.Game;

public class Asteroid {

    SpriteSheet sheet;
    Image asteroid;

    public double xPos;
    public float yPos = 128;

    int directionX = 0;
    int directionY = 0;

    public int asteroidType;
    public Shard[] shards = new Shard[3];

    public boolean isDestroyed = false;

    Sound boom;


    public void spawn() {
        double randomNum = Math.random();
        xPos = 128 + randomNum * 544;
    }


    public Asteroid(SpriteSheet sheet, GameContainer gc) throws SlickException {
        double random = Math.random();

        this.sheet = sheet;

        boom = new Sound("oclu/ludumdare/res/sounds/boom.wav");

        if (random <= 0.25) {
            asteroid = sheet.getSubImage(32, 80, 16, 16);
            asteroidType = 0;
        }else if (random <= 0.5) {
            asteroid = sheet.getSubImage(48, 80, 16, 16);
            asteroidType = 1;
        }else if (random <= 0.75) {
            asteroid = sheet.getSubImage(64, 80, 16, 16);
            asteroidType = 2;
        }else {
            asteroid = sheet.getSubImage(80, 80, 16, 16);
            asteroidType = 3;
        }
        asteroid = asteroid.getScaledCopy(32, 32);

        spawn();
    }

    public void draw() {
        if (!isDestroyed) {
            asteroid.draw(Float.parseFloat(Double.toString(xPos)), yPos);
        }
    }

    public void move(int delta) {
        if (!isDestroyed) {
            double random = Math.random();
            float defaultSpeed = delta * 0.25f;
            float defaultFallspeed = delta * 0.25f;
            double speed = random * defaultSpeed;
            double fallSpeed = random * defaultFallspeed;


            if (directionX == 0) {
                xPos += speed;
            }else {
                xPos -= speed;
            }
            if (directionY == 0) {
                yPos += fallSpeed;
            }else {
                yPos -= fallSpeed;
            }

            if (xPos <= 128) {
                directionX = 0;
            }
            if (xPos >= 640) {
                directionX = 1;
            }
            if (yPos <= 128) {
                directionY = 0;
            }
            if (yPos >= 568) {
                directionY = 1;
            }
        }
    }

    public void destroy() throws SlickException {
        boom.play(1f, 0.1f);

        shards[0] = new Shard(sheet, asteroidType, (float)xPos, yPos);
        shards[1] = new Shard(sheet, asteroidType, (float)xPos, yPos);
        shards[2] = new Shard(sheet, asteroidType, (float)xPos, yPos);

        shards[0].speedY -= 2f;
        shards[2].speedY += 0.1f;

        Game.powerups.add(new Powerup(sheet, (float)xPos, (float)yPos));

        isDestroyed = true;
    }

}
