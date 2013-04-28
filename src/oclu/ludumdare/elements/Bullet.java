package oclu.ludumdare.elements;

import org.newdawn.slick.*;

public class Bullet {

    SpriteSheet sheet;
    Image bullet;

    float posX;
    float posY;
    float speed;

    double angle;

    public Bullet(SpriteSheet sheet, float posX, float posY) {

        this.sheet = sheet;

        bullet = sheet.getSubImage(21, 100, 6, 7);

        this.posX = posX;
        this.posY = posY;

    }

    public void move(int delta, float targetX, float targetY) {

        speed = delta * 0.5f;

            if (targetX - posX == 0) {
                angle = Math.atan((targetY-posY)/0.001);
            }else {
                angle = Math.atan((targetY-posY) / (targetX-posX));
            }
            angle *= 180 / Math.PI;
            angle += 90;
            if (targetX-posX < 0) {
                angle += 180;
            }

        bullet.setRotation((float)angle);

        posX += Math.cos(angle) * speed;
        posY += Math.sin(angle) * speed;

    }

    public void draw() {
        bullet.draw(posX, posY);
    }

    public boolean hitDetection(Asteroid target) {

        if (posX >= target.xPos && posX <= target.xPos + 32 && posY >= target.yPos && posY <= target.yPos + 32 && target.isDestroyed == false) {
            return true;
        }else {
            return false;
        }

    }

}
