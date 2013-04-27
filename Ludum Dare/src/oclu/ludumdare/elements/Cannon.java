package oclu.ludumdare.elements;

import org.newdawn.slick.*;
import java.lang.Math;

public class Cannon {

    SpriteSheet sheet;
    Image cannon;
    int xPos;
    int yPos;
    boolean shootCooldown = false;
    int cooldownCount;
    Bullet bullet;
    public static int currentTarget;

    Sound shoot;

    public boolean bulletExists() {
        return (bullet != null);
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Cannon(SpriteSheet sheet, GameContainer gc) throws SlickException {
        this.sheet = sheet;

        cannon = sheet.getSubImage(4, 97, 8, 14).getScaledCopy(16,28);

        xPos = gc.getWidth()/2+cannon.getWidth()/2;
        yPos = gc.getHeight()/2+cannon.getHeight()/2;
        cannon.setCenterOfRotation(8,16);

        shoot = new Sound("oclu/ludumdare/res/sounds/shoot.wav");
    }

    public void draw() {
        cannon.draw(xPos, yPos);
    }



    public void pickTarget(Asteroid[] asteroids) {
        currentTarget = (int)((float)asteroids.length * Math.random());
        shoot(asteroids[currentTarget]);
    }

    public void shoot(Asteroid target) {
        double angle;

        if (!shootCooldown) {
            if (target != null) {
                if (target.yPos <= 500) {

                    angle = Math.atan((target.yPos-yPos) / (target.xPos-xPos));
                    angle *= 180 / Math.PI;
                    angle += 90;
                    if (target.xPos-xPos < 0) {
                        angle += 180;
                    }

                    cannon.setRotation(Float.parseFloat(Double.toString(angle)));

                    bullet = new Bullet(sheet, xPos, yPos);

                    shoot.play(1f, 0.1f);

                }
                shootCooldown = true;
            }
        }else {
            cooldownCount++;
            if (cooldownCount == 500) {
                shootCooldown = false;
                cooldownCount = 0;
            }
        }
    }

}
