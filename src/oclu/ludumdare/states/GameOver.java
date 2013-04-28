package oclu.ludumdare.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {

    int ID = 2;

    Image background;

    public GameOver(int ID) {
        this.ID = ID;
    }

    @Override

    public int getID() {
        return ID;
    }

    @Override

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        background = new Image("oclu/ludumdare/res/img/background.png");

    }

    @Override

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {

        background.draw();

        g.setColor(Color.white);

        g.drawString("You have died!", gc.getWidth()/2-g.getFont().getWidth("You have died!")/2, gc.getHeight()/2-200);
        g.drawString("You got a score of "+Game.score, gc.getWidth()/2-g.getFont().getWidth("You got a score of"+Game.score)/2, gc.getHeight()/2-100);
        g.drawString("Would you like to play again? (PRESS SPACE)", gc.getWidth()/2-g.getFont().getWidth("Would you like to play again? (PRESS SPACE)")/2, gc.getHeight()/2);

    }

    @Override

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {

        Input in = gc.getInput();

        if (in.isKeyPressed(Input.KEY_SPACE)) {
            Game.asteroids.clear();
            Game.powerups.clear();

            Game.spawnAsteroidCooldown = 0;
            Game.spawnAsteroidCooldownRequired = 10000;

            Game.score = 0;
            Game.shardsMissed = 0;

            sbg.enterState(1);
        }

    }

}
