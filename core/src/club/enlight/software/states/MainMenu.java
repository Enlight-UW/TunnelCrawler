package club.enlight.software.states;

import club.enlight.software.handlers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.badlogic.gdx.graphics.Texture.*;

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
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Button/ButtonPack.atlas"));
        ImageButton.ImageButtonStyle optionsButtonStyle = new ImageButton.ImageButtonStyle();
        optionsButtonStyle.up =  new TextureRegionDrawable(atlas.findRegion("Options"));
        optionsButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("OptionsClick"));
        optionsButtonStyle.imageOver = new TextureRegionDrawable(atlas.findRegion("OptionsHover"));
        final ImageButton optionsButton = new ImageButton(optionsButtonStyle);
        optionsButton.setSize(4/14*Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        optionsButton.setPosition(7 / 14 * Gdx.graphics.getWidth(), 0);

        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("Play"));
        playButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("PlayClick"));
        playButtonStyle.imageOver = new TextureRegionDrawable(atlas.findRegion("PlayHover"));
        final ImageButton playButton = new ImageButton(playButtonStyle);
        playButton.setSize(3 / 14 * Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        playButton.setPosition(0, 0);

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable( atlas.findRegion("Exit"));
        exitButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("ExitClick"));
        exitButtonStyle.imageOver = new TextureRegionDrawable(atlas.findRegion("ExitHover"));
        final ImageButton exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(4 / 14 * Gdx.graphics.getWidth(), 7 / 50 * Gdx.graphics.getHeight());
        exitButton.setPosition(3/14*Gdx.graphics.getWidth(), 0);

        ImageButton.ImageButtonStyle loadButtonStyle = new ImageButton.ImageButtonStyle();
        loadButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("Load"));
        loadButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("LoadClick"));
        loadButtonStyle.imageOver = new TextureRegionDrawable(atlas.findRegion("LoadHover"));
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

        //Gdx.input.setInputProcessor(stage);
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
        stage.getBatch().end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
