package oclu.ludumdare.elements;

import org.newdawn.slick.*;
import oclu.ludumdare.states.Game;

import java.util.ArrayList;

public class Basket {

    SpriteSheet sheet;
    Image basket, basketLarge;

    public boolean isLarge = false;

    float xPos = 0;
    float yPos = 582;
    float yPosLarge = 580;

    public boolean isDoubleScore = false;

    public Basket(SpriteSheet sheet) throws SlickException {

        this.sheet = sheet;

        basket = sheet.getSubImage(48, 102, 32, 9).getScaledCopy(64,18);
        basketLarge = sheet.getSubImage(48, 118, 48, 10).getScaledCopy(96,20);
    }

    public void draw(Graphics g) {
        //g.setColor(Color.blue);
        //g.fillRect(xPos, yPos, 32, 16);
        if (isLarge) {
            basketLarge.draw(xPos, yPosLarge);
        }else {
            basket.draw(xPos, yPos);
        }
    }

    public void checkAction(Input in, int delta) {
        float speed = delta * 0.5f;

        if (!Game.isPaused) {

            if (in.isKeyDown(Input.KEY_A) || in.isKeyDown(Input.KEY_LEFT)) {
                xPos -= speed;
            }else if (in.isKeyDown(Input.KEY_D) || in.isKeyDown(Input.KEY_RIGHT)) {
                xPos += speed;
            }else {
                if (!isLarge) {
                    xPos = in.getMouseX()-32;
                }else {
                    xPos = in.getMouseX()-48;
                }
            }

            if (!isLarge) {
                if (xPos < 128) {
                    xPos = 128;
                }else if (xPos > 608) {
                    xPos = 608;
                }
            }else {
                if (xPos < 128) {
                    xPos = 128;
               }else if (xPos > 576) {
                   xPos = 576;
               }
            }
        }
    }

    public void collectShards(ArrayList<Asteroid> asteroids) {
        for (int i = 0; i < asteroids.size(); i++) {
            for (int ii = 0; ii < asteroids.get(i).shards.length; ii++) {
                Shard currentShard = asteroids.get(i).shards[ii];
                if (currentShard != null) {
                    if (isLarge) {
                        if (currentShard.posX >= xPos && currentShard.posX <= xPos + basketLarge.getWidth() && currentShard.posY >= yPosLarge) {
                            currentShard.collect();
                            if (isDoubleScore) {
                                Game.score += 6;
                            }else {
                                Game.score += 3;
                            }
                        }
                    }else {
                        if (currentShard.posX >= xPos && currentShard.posX <= xPos + basket.getWidth() && currentShard.posY >= yPos) {
                            currentShard.collect();
                            if (isDoubleScore) {
                                Game.score += 6;
                            }else {
                                Game.score += 3;
                            }
                        }
                    }
                }
            }
        }
    }

}
