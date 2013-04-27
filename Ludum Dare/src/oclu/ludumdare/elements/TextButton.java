package oclu.ludumdare.elements;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

public class TextButton {

    private final Color fontColor;
    public final int yPos;
    private final String text;
    public Image img;
    public final int xPos;

    public TextButton(String text, Image img, int xPos, int yPos, Color color) {

        this.text = text;
        this.img = img;
        this.xPos = xPos;
        this.yPos = yPos;
        this.fontColor = color;

    }

    public boolean isHover(Input in) {
        int mouseX = in.getMouseX();
        int mouseY = in.getMouseY();

        if (mouseX >= xPos && mouseX <= xPos + img.getWidth() && mouseY >= yPos && mouseY <= yPos + img.getHeight()) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isClicked(Input in) {
        boolean mouseClicked = in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        int mouseX = in.getMouseX();
        int mouseY = in.getMouseY();

        if (isHover(in) && mouseClicked) {
            return true;
        }else {
            return false;
        }
    }

    public void draw(Graphics g) {
        if (g.getColor() != fontColor) {
            g.setColor(fontColor);
        }

        img.draw(xPos, yPos);
        g.drawString(text, xPos + img.getWidth()/2 - g.getFont().getWidth(text)/2, yPos + img.getHeight()/2 - g.getFont().getHeight(text)/2);

    }

}
