package gameobj;


import animated.GameLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Enemies.
 */
public class Enemies {
    private boolean first = true;
    private long past;
    private double speed;
    private double min;
    private double max;
    private GameLevel g;
    private boolean right;
    private List<Enemy> enemys = new ArrayList<Enemy>();

    /**
     * Instantiates a new Enemies.
     *
     * @param sp the sp
     * @param mn the mn
     * @param mx the mx
     * @param gi the gi
     */
    public Enemies(double sp, double mn, double mx, GameLevel gi) {
        speed = sp;
        min = mn;
        max = mx;
        this.g = gi;
        right = true;
    }

    /**
     * Add enemy.
     *
     * @param e  the e
     * @param dt the dt
     */
    public void addEnemy(Block e, double dt) {

        enemys.add(new Enemy(e, speed, min, max, dt));
    }

    /**
     * Move.
     *
     * @param mx the mx
     * @return the boolean
     */
    public boolean move(double mx) {
        if (right) {
            Enemy end = searchEdge();
            if (end.inRightRangend()) {
                for (Enemy e : enemys) {
                    e.moveRight();
                }
            } else {
                right = false;
                return change(mx);
            }

        } else {
            Enemy start = searchstart();
            if (start.inRightRangestart()) {
                for (Enemy e : enemys) {
                    e.moveLeft();
                }
            } else {
                right = true;
                return change(mx);
            }
        }
        return true;

    }

    /**
     * change the whole gang.
     *
     * @param mx the max
     * @return the boolean
     */
    public boolean change(double mx) {
        for (Enemy e : enemys) {
            e.change();
            double y = e.getCollisionRectangle().getUpperLeft().getY() + e.getCollisionRectangle().getHeight();
            if (y >= mx) {
                return false;
            }
        }
        return true;

    }


    /**
     * returns the lowest enemy from the list.
     * @param col a list of enemies
     * @return enemy.
     */
    private Enemy searchEnd(List<Enemy> col) {
        double y = 0;
        Enemy t = null;
        for (Enemy e : col) {
            if (e.getE().getCollisionRectangle().getUpperLeft().getY() > y) {
                y = e.getE().getCollisionRectangle().getUpperLeft().getY();
                t = e;

            }
        }
        return t;
    }


    /**
     * \search for the enemy positined at the absoultly end of the line.
     *
     * @return Enemy
     */
    private Enemy searchEdge() {
        double x = 0;
        Enemy t = null;
        for (Enemy e : enemys) {
            if (e.getE().getCollisionRectangle().getUpperLeft().getX() > x) {
                x = e.getE().getCollisionRectangle().getUpperLeft().getX();
                t = e;

            }
        }
        return t;
    }

    /**
     * \search for the enemy positined at the absoultly start of the line.
     *
     * @return Enemy
     */
    private Enemy searchstart() {
        double x = max;
        Enemy t = null;
        for (Enemy e : enemys) {
            if (e.getE().getCollisionRectangle().getUpperLeft().getX() < x) {
                x = e.getE().getCollisionRectangle().getUpperLeft().getX();
                t = e;

            }
        }
        return t;
    }

    /**
     * Gets enemys.
     *
     * @return the enemys
     */
    public List<Enemy> getEnemys() {
        return enemys;
    }

    /**
     * Add enemy.
     *
     * @param e the e
     */
    public void removeEnemy(Block e) {
        for (Enemy t : enemys) {
            if (t.same(e)) {
                enemys.remove(t);
                t.removeFromGameLevel(g);
                break;
            }
        }
    }

    /**
     * Timed shoot.
     */
    public void timedShoot() {
        List<List<Enemy>> cols = setcols();
        if (first) {
            past = System.currentTimeMillis();
            pick(lastRow(cols)).shoot(g);
            first = false;
        } else {
            long now = System.currentTimeMillis();
            if (now - past >= 0.5 * 1000) {
                past = System.currentTimeMillis();
                pick(lastRow(cols)).shoot(g);
            }
        }
    }


    /**
     * returns a list of potencial shooters.
     *
     * @param cols coloms of enemies.
     * @return the last enemy from every colom
     */
    private List<Enemy> lastRow(List<List<Enemy>> cols) {
        List<Enemy> row = new ArrayList<Enemy>();
        for (List<Enemy> col : cols) {
            row.add(searchEnd(col));
        }
        return row;

    }

    /**
     * returns an enemy randomlly from specific list.
     *
     * @param enemies a row of enemies.
     * @return an enemy.
     */
    private Enemy pick(List<Enemy> enemies) {
        return enemies.get(new Random().nextInt(enemies.size()));

    }

    /**
     * set coloms of enemies.
     *
     * @return list of list of enemies.
     */
    private List<List<Enemy>> setcols() {
        List<List<Enemy>> cols = new ArrayList<List<Enemy>>();
        double x = searchstart().getE().getCollisionRectangle().getUpperLeft().getX();
        double mx = searchEdge().getE().getCollisionRectangle().getUpperLeft().getX();
        List<Enemy> col = null;
        while (x <= mx) {
            for (Enemy e : enemys) {
                if (e.getE().getCollisionRectangle().getUpperLeft().getX() == x) {
                    if (col == null) {
                        col = new ArrayList<Enemy>();

                    }
                    col.add(e);
                }
            }
            if (col != null) {
                cols.add(new ArrayList<Enemy>(col));
                col = null;
            }
            x = x + 50;
        }
        return cols;
    }

    /**
     * Reset.
     */
    public void restart() {
        for (Enemy e : enemys) {
            e.reset();
        }
    }
}



