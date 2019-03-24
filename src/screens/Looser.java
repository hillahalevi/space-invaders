package screens;

import biuoop.DrawSurface;
import inter.Sprite;

import java.awt.Color;

/**
 * The type Loserscreen.
 */
public class Looser implements Sprite {

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int y = 0;
        int height = 30;
        Color color = new Color(165, 42, 42);
        while (y < d.getHeight() / 2) {
            d.setColor(color);
            d.fillRectangle(0, y, d.getWidth(), height);
            d.setColor(Color.white);
            if (y % 20 == 0) {
                d.drawText((10), (int) (y + 0.5 * height), "You win some", 24);
                d.drawText((int) (d.getWidth() * 0.75), (int) (y + 0.5 * height), "You loose some", 24);
                color = color.brighter();
            }
            y = y + height;
        }
        y = d.getHeight();
        color = new Color(165, 42, 42);
        while (y > d.getHeight() / 2) {

            d.setColor(color);
            d.fillRectangle(0, y, d.getWidth(), height);

            if (y % 20 == 0) {
                color = color.brighter();
            }
            y = y - height;

        }
        d.drawText(150, d.getHeight() - 50, "Better luck next time !", 50);
        d.fillRectangle(0, 270, d.getWidth(), 30);
    }


    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    @Override
    public void timePassed(double dt) {

    }
}
