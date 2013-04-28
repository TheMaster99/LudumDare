package oclu.ludumdare.elements;

import org.newdawn.slick.*;

public class Shard {

    SpriteSheet sheet;
    Image shard;
    float posX, posY;
    public float speedX = 0.01f;
    public float speedY = 0.1f;

    public boolean isDisappeared = false;

    Sound collect;

    public Shard(SpriteSheet sheet, int asteroidType, float posX, float posY) throws SlickException {

        collect = new Sound("oclu/ludumdare/res/sounds/collect.wav");

        this.sheet = sheet;

        if (asteroidType == 0) {
            shard = sheet.getSubImage(32, 80, 16, 16);
        }else if (asteroidType == 1) {
            shard = sheet.getSubImage(48, 80, 16, 16);
        }else if (asteroidType == 2) {
            shard = sheet.getSubImage(64, 80, 16, 16);
        }else {
            shard = sheet.getSubImage(80, 80, 16, 16);
        }

        shard = shard.getScaledCopy(16, 16);

        this.posX = posX;
        this.posY = posY;

    }

    public void move(int delta) {

        posX += delta * speedX;
        posY += delta * speedY;

        if (speedY <= 0.1f) {
            speedY += 0.01f;
        }

        if (posY >= 600) {
            isDisappeared = true;
        }

    }

    public void draw() {
        shard.draw(posX, posY);
    }

    public void collect() {
        isDisappeared = true;

        collect.play(1f, 0.1f);
    }

}
