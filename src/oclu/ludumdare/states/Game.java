package oclu.ludumdare.states;

import oclu.ludumdare.elements.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class Game extends BasicGameState {

    int ID = 1;

    public static Basket plyr;

    ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();


    Cannon cannon;

    Bullet bullet;

    TextButton resume, exit, x;
    Image buttonBack, border, exitButton, exitHover, background;
    SpriteSheet sheet;

    public static boolean isTimeWarp = false;

    public static boolean isPaused = false;

    public static int score = 0;

    int spawnAsteroidCooldown = 0;
    int spawnAsteroidCooldownRequired = 10000;

    public Game(int ID) {
        this.ID = ID;
    }

    @Override

    public int getID() {
        return ID;
    }

    @Override

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        buttonBack = new Image("oclu/ludumdare/res/img/blankButton.png");

        border = new Image("oclu/ludumdare/res/img/Border.png");

        background = new Image("oclu/ludumdare/res/img/background.png");

        sheet = new SpriteSheet("oclu/ludumdare/res/img/Spritesheet.png", 16, 16);

        exitButton = sheet.getSubImage(0,0,16,16).getScaledCopy(32,32);
        exitHover = sheet.getSubImage(0,16,16,16).getScaledCopy(32,32);

        plyr = new Basket(sheet);

        cannon = new Cannon(sheet, gc);

        resume = new TextButton("Resume Game", buttonBack, gc.getWidth()/2-buttonBack.getWidth()/2, gc.getHeight()/2-buttonBack.getHeight()/2, Color.black);
        exit = new TextButton("Return to Main Menu", buttonBack, resume.xPos, resume.yPos + 200, Color.black);
        x = new TextButton("", exitButton, 768, 0, Color.black);

        for (int i = 0; i < 10; i++) {
            asteroids.add(new Asteroid(sheet, gc));
        }

    }

    @Override

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        background.draw();
        plyr.draw(g);
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i) != null) {

                asteroids.get(i).draw();


                for (int ii = 0; ii < asteroids.get(i).shards.length; ii++) {
                    if (asteroids.get(i).shards[ii] != null) {
                        asteroids.get(i).shards[ii].draw();
                    }
                }
            }
        }
        cannon.draw();
        if (bullet != null) {
            bullet.draw();
        }

        if (isPaused) {
            resume.draw(g);
            exit.draw(g);
        }
        border.draw();
        g.drawString("Current Score: " +score, 32, 32);
        x.draw(g);
    }

    @Override

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input in = gc.getInput();

        if (in.isKeyPressed(Input.KEY_ESCAPE)) {
            isPaused = !isPaused;
        }

        if (x.isHover(in)) {
            x.img = exitHover;
        }else {
            x.img = exitButton;
        }

        if (x.isClicked(in)) {
            sbg.enterState(0);
        }

        if (isPaused) {
            if (resume.isClicked(in)) {
                isPaused = false;
            }else if (exit.isClicked(in)) {
                sbg.enterState(0);
            }
        }else {
            plyr.checkAction(in, delta);
            plyr.collectShards(asteroids);
            for (int i = 0; i < asteroids.size(); i++) {
                if (asteroids.get(i) != null) {
                    asteroids.get(i).move(delta);
                }
            }
            cannon.pickTarget(asteroids);
            if (cannon.bulletExists()) {
                bullet = cannon.getBullet();
            }
            if (bullet != null && asteroids.get(Cannon.currentTarget) != null) {
                bullet.move(delta, (float)(asteroids.get(Cannon.currentTarget).xPos), asteroids.get(Cannon.currentTarget).yPos);
                if (bullet.hitDetection(asteroids.get(Cannon.currentTarget))) {
                    asteroids.get(Cannon.currentTarget).destroy();
                    bullet = null;
                }
            }
            for (int i = 0; i < asteroids.size(); i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (asteroids.get(i) != null) {
                        if (asteroids.get(i).shards[ii] != null) {
                            if (asteroids.get(i).shards[ii].isDisappeared) {
                                asteroids.get(i).shards[ii] = null;
                            }else {
                                asteroids.get(i).shards[ii].move(delta);
                            }
                        }
                    }
                }
            }

            if (spawnAsteroidCooldown >= spawnAsteroidCooldownRequired) {
                asteroids.add(new Asteroid(sheet, gc));

                spawnAsteroidCooldown = 0;
                spawnAsteroidCooldownRequired -= 5;
            }else {
                spawnAsteroidCooldown++;
            }
        }
    }
}
