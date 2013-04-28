package oclu.ludumdare;

import oclu.ludumdare.states.*;
import oclu.ludumdare.states.Game;
import org.newdawn.slick.*;
import org.newdawn.slick.Music;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    static Music music;

    static AppGameContainer app;

    public Main() {
        super("Raining Shards (Hallelujah!)");
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new Menu(0));
        this.addState(new Game(1));
        this.addState(new GameOver(2));
    }

    public static void main(String[] args) throws SlickException {

        music = new Music("oclu/ludumdare/res/sounds/music.ogg");
        music.loop(0.8f, 0.5f);

        app = new AppGameContainer(new Main());

        app.setDisplayMode(800, 600, false);

        app.start();

    }

}
