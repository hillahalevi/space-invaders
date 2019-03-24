package gameset;

import animated.EndScreen;
import animated.GameLevel;
import animated.KeyPressStoppableAnimation;
import animated.LooserScreen;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import gamehelp.AnimationRunner;
import gamehelp.Counter;
import gameobj.HighScoresTable;
import inter.LevelInformation;
import screens.Looser;

/**
 * The type Game flow.
 */
public class GameFlow {

    private biuoop.KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private HighScoresTable h;
    private DialogManager dialogManager;


    /**
     * Instantiates a new Game flow.
     *
     * @param keyboardSensor  the keyboard sensor
     * @param animationRunner the animation runner
     * @param highScoresTable the high scores table
     * @param dialogManager   the dialog manager
     */
    public GameFlow(KeyboardSensor keyboardSensor, AnimationRunner animationRunner, HighScoresTable highScoresTable,
                    DialogManager dialogManager) {
        this.keyboardSensor = keyboardSensor;
        this.animationRunner = animationRunner;
        this.h = highScoresTable;
        this.dialogManager = dialogManager;
    }


    /**
     * Run levels.
     *
     * @param levelInfo the level info
     */
    public void runLevels(LevelInformation levelInfo) {
        Counter score = new Counter(0);
        Counter numberoflives = new Counter(3);
        double speed = 1;
        GameLevel level;
        while (true) {
            level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, score, numberoflives, speed);
            level.initialize();
            while (!level.shouldStop()) {

                level.run();
            }
            speed++;

            if (numberoflives.getValue() == 0) {
                SpriteCollection s = new SpriteCollection();
                s.addSprite(new Looser());
                this.animationRunner.run(new LooserScreen(3, 5, s));
                EndScreen end = new EndScreen("Game Over!", score);
                this.animationRunner.run(new KeyPressStoppableAnimation(end, keyboardSensor, "space"));
                this.enterTable(score.getValue());
                return;
            }

        }


    }

    /**
     * updating the table .
     *
     * @param score the current score
     */
    private void enterTable(int score) {
        if (this.h.getRank(score) <= this.h.size()) {
            //checks if the score is good enough
            String name = this.dialogManager.showQuestionDialog("Enter Name", "What is your name?", "WHO?");
            this.h.add(new ScoreInfo(name, score));
        }
    }
}