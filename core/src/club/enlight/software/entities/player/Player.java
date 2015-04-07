package club.enlight.software.entities.player;

import club.enlight.software.entities.Creature;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Kenneth on 3/24/15.
 */
public class Player extends Creature {
    Animation leftAnimation;
    Animation rightAnimation;
    Animation upAnimation;
    Animation downAnimation;

    public Player(int startX, int startY, Direction startFacing, double creatureVelocity)
    {
        super(startX, startY, startFacing, creatureVelocity);
    }

    public void handleInput()
    {
       //Gdx.input.isKeyPressed()
    }
}
