package oclu.ludumdare.states;

import oclu.ludumdare.Main;
import oclu.ludumdare.elements.TextButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

    public int ID = 0;

    TextButton start,quit;

    Image buttonBack, background;

    SpriteSheet sheet;

    public Menu(int ID) {
        this.ID = ID;
    }

    @Override

    public int getID() {
        return ID;
    }

    @Override

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        background = new Image("oclu/ludumdare/res/img/background.png");

        sheet = new SpriteSheet(new Image("oclu/ludumdare/res/img/Spritesheet.png"), 16, 16);
        buttonBack = new Image("oclu/ludumdare/res/img/blankButton.png");

        start = new TextButton("Start Game", buttonBack, gc.getWidth()/2-buttonBack.getWidth()/2, gc.getHeight()/2-buttonBack.getHeight()/2, Color.black);
        quit = new TextButton("Quit Game (How dare you!)", buttonBack, start.xPos, start.yPos + 125, Color.black);


    }

    @Override

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {

        background.draw();

        start.draw(g);
        quit.draw(g);

    }

    @Override

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        Input in = gc.getInput();

        if (start.isClicked(in)) {
            sbg.enterState(1);
        }else if (quit.isClicked(in)) {
            gc.exit();
        }

    }
}
