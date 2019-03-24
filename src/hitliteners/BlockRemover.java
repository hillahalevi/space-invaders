package hitliteners;

import animated.GameLevel;
import gamehelp.Counter;
import gameobj.Ball;
import gameobj.Block;
import gameobj.Enemies;
import inter.HitListener;

/**
 * BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * *of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;
    private Enemies enemies;

    /**
     * Instantiates a new Block remover.
     *
     * @param g             the game
     * @param removedBlocks the removed blocks
     * @param e             the e
     */
    public BlockRemover(GameLevel g, Counter removedBlocks, Enemies e) {
        game = g;
        remainingBlocks = removedBlocks;
        enemies = e;
    }


    /**
     * Hit event.
     * called whenever the beingHit object is hit.
     *
     * @param beingHit object that  being hit
     * @param hitter   the hitter ball.
     *                 Blocks that are hit and reach 0 hit-points should be removed
     *                 from the game. Remember to remove this listener from the block
     *                 that is being removed from the game.
     */

    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            if (beingHit.rUEnemy() && hitter.isEnemy()) {
                return;
            }
            if (beingHit.rUEnemy()) {
                remainingBlocks.decrease(1);
                enemies.removeEnemy(beingHit);
            } else {
                beingHit.removeFromGameLevel(game);
                beingHit.removeHitListener(this);
            }
        }
    }


}