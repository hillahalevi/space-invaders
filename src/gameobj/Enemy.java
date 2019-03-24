package gameobj;

import animated.GameLevel;
import biuoop.DrawSurface;
import gameset.Velocity;
import geo.Point;
import geo.Rectangle;
import inter.Collidable;
import inter.HitListener;
import inter.HitNotifier;
import inter.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Enemy.
 */
public class Enemy implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Block e;
    private double sttaciapeed;
    private Rectangle start;
    private double speed;
    private double minValue;
    private double maxValue;
    private double dt;


    /**
     * constructor.
     *
     * @param b   the b
     * @param sp  the sp
     * @param min the min
     * @param max the max
     * @param d   the d
     */
    public Enemy(Block b, double sp, double min, double max, double d) {
        e = b;
        speed = sp;
        minValue = min;
        maxValue = max;
        this.dt = d;
        start = b.getCollisionRectangle();
        sttaciapeed = sp;

    }


    /**
     * check if the movement will hit the border.
     *
     * @return true or false.
     */
    public boolean inRightRangend() {
        double current = e.getCollisionRectangle().getUpperLeft().getX();
        double way = e.getCollisionRectangle().getWidth();
        return (current + way + speed * dt <= maxValue);
    }

    /**
     * check if the movement will hit the border.
     *
     * @return true or false.
     */
    public boolean inRightRangestart() {
        double current = e.getCollisionRectangle().getUpperLeft().getX();
        return (current - speed * dt >= minValue);
    }

    /**
     * moves the paddle to the left if the movement is legit.
     */
    public void moveLeft() {
        //only if not hitting the left border
        if (inRightRangestart()) {
            double w = this.getCollisionRectangle().getWidth();
            double h = this.getCollisionRectangle().getHeight();
            geo.Point point = this.getCollisionRectangle().getUpperLeft();
            //reposition the paddle
            Rectangle r = new Rectangle(new geo.Point(point.getX() - speed * dt, point.getY()), w, h);
            e.setBlock(r);
        }

    }

    /**
     * moves the paddle to the right if the movement is legit.
     */
    public void moveRight() {
        if (inRightRangend()) {
            //only if not hitting the right border.
            double w = this.getCollisionRectangle().getWidth();
            double h = this.getCollisionRectangle().getHeight();
            geo.Point point = this.getCollisionRectangle().getUpperLeft();
            //reposition the paddle
            Rectangle r = new Rectangle(new Point(point.getX() + speed * dt, point.getY()), w, h);
            e.setBlock(r);

        }

    }

    /**
     * Change.
     */
    public void change() {
        double w = this.getCollisionRectangle().getWidth();
        double h = this.getCollisionRectangle().getHeight();
        geo.Point point = this.getCollisionRectangle().getUpperLeft();
        //reposition the paddle
        Rectangle r = new Rectangle(new Point(point.getX(), point.getY() + 10), w, h);
        e.setBlock(r);
        speed = 110 * speed / 100;

    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        e.drawOn(d);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param d the dt
     */
    @Override
    public void timePassed(double d) {

    }

    /***
     * get the shepe of the collidable object.
     * @return the "collision shape" of the object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return e.getCollisionRectangle();
    }


    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter          the hitting ball
     * @param collisionPoint  the hit point
     * @param currentVelocity the current velocity
     * @return the new velocity expected after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (!hitter.isEnemy()) {
            e.setHitPoints(0);
        }
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(e, hitter);
        }
        return null;
    }

    /**
     * Gets e.
     *
     * @return the e
     */
    public Block getE() {
        return e;
    }


    /**
     * Add hit listener.
     * Add hl as a listener to hit events.
     *
     * @param hl the hl
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);

    }

    /**
     * Remove hit listener.
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hl
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);


    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGameLevel(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * insert the block to the gameobj.
     *
     * @param g the gameobj
     */
    public void addToGameLevel(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);

    }

    /**
     * Same boolean.
     *
     * @param b the b
     * @return the boolean
     */
    public boolean same(Block b) {
        Rectangle r = b.getCollisionRectangle();
        Rectangle t = this.getCollisionRectangle();
        return r.getUpperLeft().equals(t.getUpperLeft());
    }

    /**
     * Shoot.
     *
     * @param gameLevel the game level
     */
    public void shoot(GameLevel gameLevel) {
        gameLevel.ballsCreator(this);

    }

    /**
     * Sets speed.
     *
     * @param spee the spee
     */
    private void setSpeed(double spee) {
        this.speed = spee;
    }

    /**
     * Reset.
     */
    public void reset() {
        e.setBlock(start);
        setSpeed(sttaciapeed);
    }


}
