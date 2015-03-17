package club.enlight.software.states;

import club.enlight.software.handlers.StateManager;
import club.enlight.software.states.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class MainMenu extends State {
    OrthographicCamera camera;
    ShapeRenderer renderer;
    Stage stage;

    public MainMenu(final StateManager sm)
    {   super(sm);
        stage = new Stage();
        ImageButton.ImageButtonStyle optionsButtonStyle = new ImageButton.ImageButtonStyle();
        optionsButtonStyle.up = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal("Button/Options.png"))));
        optionsButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("Button/OptionsClick.png")));
        optionsButtonStyle.imageOver = new TextureRegionDrawable(new TextureRegion(new Texture("Button/OptionsHover.png")));
        final ImageButton optionsButton = new ImageButton(optionsButtonStyle);
        optionsButton.setSize(4/14*Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        optionsButton.setPosition(7/14*Gdx.graphics.getWidth(),0);

        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable( new TextureRegion(new Texture("Button/Play.png")));
        playButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("Button/PlayClick.png")));
        playButtonStyle.imageOver = new TextureRegionDrawable(new TextureRegion(new Texture("Button/PlayHover.png")));
        final ImageButton playButton = new ImageButton(playButtonStyle);
        playButton.setSize(3 / 14 * Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        playButton.setPosition(0, 0);

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable( new TextureRegion(new Texture("Button/Exit.png")));
        exitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("Button/ExitClick.png")));
        exitButtonStyle.imageOver = new TextureRegionDrawable(new TextureRegion(new Texture("Button/ExitHover.png")));
        final ImageButton exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(4 / 14 * Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        exitButton.setPosition(3/14*Gdx.graphics.getWidth(), 0);

        ImageButton.ImageButtonStyle loadButtonStyle = new ImageButton.ImageButtonStyle();
        loadButtonStyle.up = new TextureRegionDrawable( new TextureRegion(new Texture("Button/Load.png")));
        loadButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("Button/LoadClick.png")));
        loadButtonStyle.imageOver = new TextureRegionDrawable(new TextureRegion(new Texture("Button/LoadHover.png")));
        final ImageButton loadButton = new ImageButton(loadButtonStyle);
        loadButton.setSize(3 / 14 * Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        loadButton.setPosition(11/14*Gdx.graphics.getWidth(), 0);

        Image background = new Image(new Texture("Menu Screen/BG1.png"));
        //Implement buttons and stuff that show up when the game starts.
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();

        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(loadButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
    stage.act(dt);
    }

    @Override
    public void render() {
        stage.draw();
        stage.getBatch().begin();
        renderer.setColor(Color.CYAN);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.box(0,0,0,20,20,0);
        renderer.end();

    }

    @Override
    public void dispose() {
    stage.dispose();
    }
}
