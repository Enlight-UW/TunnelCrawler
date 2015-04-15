package club.enlight.software.states;

import club.enlight.software.entities.player.Player;
import club.enlight.software.handlers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class GameLevel extends State {
    Player player;
    int width;
    int height;

    public GameLevel(final StateManager sm){
        super(sm);
        //Create a level (or load one) using getLevel from sm and Ryan's level generator, along with a room generator,
        //then play the level

        Vector2 centerOfScreen = new Vector2(0,0); // Orthographic camera centers screen at (0,0)
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        player = new Player(this, centerOfScreen, new OrthographicCamera(width, height));
    }
    @Override
    public void handleInput() {
        player.handleInput();
    }

    @Override
    public void update(float dt) {
        player.update(dt);
    }

    @Override
    public void render() {
        player.render();
    }

    @Override
    public void dispose() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
