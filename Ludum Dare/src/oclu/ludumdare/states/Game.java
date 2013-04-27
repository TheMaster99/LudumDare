package oclu.ludumdare.states;

import oclu.ludumdare.elements.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {

    int ID = 1;

    Basket plyr;

    Asteroid[] asteroids = new Asteroid[10];

    Cannon cannon;

    Bullet bullet;

    TextButton resume, exit, x;
    Image buttonBack, border, exitButton, exitHover, background;
    SpriteSheet sheet;

    public static boolean isPaused = false;

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

        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid(sheet, gc);
        }

        resume = new TextButton("Resume Game", buttonBack, gc.getWidth()/2-buttonBack.getWidth()/2, gc.getHeight()/2-buttonBack.getHeight()/2, Color.black);
        exit = new TextButton("Return to Main Menu", buttonBack, resume.xPos, resume.yPos + 200, Color.black);
        x = new TextButton("", exitButton, 768, 0, Color.black);

    }

    @Override

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        background.draw();
        plyr.draw(g);
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] != null) {

                asteroids[i].draw();


                for (int ii = 0; ii < asteroids[i].shards.length; ii++) {
                    System.out.println(asteroids[i].shards[ii]);
                    if (asteroids[i].shards[ii] != null) {
                        System.out.println("Shards drawing!");
                        asteroids[i].shards[ii].draw();
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
            for (int i = 0; i < asteroids.length; i++) {
                if (asteroids[i] != null) {
                    asteroids[i].move(delta);
                }
            }
            cannon.pickTarget(asteroids);
            if (cannon.bulletExists()) {
                bullet = cannon.getBullet();
            }
            if (bullet != null && asteroids[Cannon.currentTarget] != null) {
                bullet.move(delta, Float.parseFloat(Double.toString(asteroids[Cannon.currentTarget].xPos)), asteroids[Cannon.currentTarget].yPos);
                if (bullet.hitDetection(asteroids[Cannon.currentTarget])) {
                    asteroids[Cannon.currentTarget].destroy();
                    asteroids[Cannon.currentTarget] = null;
                    bullet = null;
                }
            }
            for (int i = 0; i < asteroids.length; i++) {
                for (int ii = 0; ii < 3; ii++) {
                    if (asteroids[i] != null) {
                        if (asteroids[i].shards[ii] != null) {
                            asteroids[i].shards[ii].move(delta);
                        }
                    }
                }
            }
        }
    }
}
