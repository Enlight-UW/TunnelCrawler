package club.enlight.software.states;

import club.enlight.software.handlers.StateManager;
import club.enlight.software.states.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class MainMenu extends State {
    OrthographicCamera camera;
    ShapeRenderer renderer;

    public MainMenu(final StateManager sm)
    {
        super(sm);
        //Implement buttons and stuff that show up when the game starts.
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();
    }
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        renderer.setColor(Color.CYAN);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.box(0,0,0,20,20,0);
        renderer.end();

    }

    @Override
    public void dispose() {

    }
}
