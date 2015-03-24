package club.enlight.software.states;

import club.enlight.software.handlers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.badlogic.gdx.graphics.Texture.*;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class MainMenu extends State {
    OrthographicCamera camera;
    Stage stage;

    public MainMenu(final StateManager sm)
    {
        super(sm);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float buttonWidthDivider = 1.f / 14.f;
        float buttonPositionDivider = 1.f / 14.f;
        float heightOfButton = (7.f / 50.f) * height;

        stage = new Stage();

        // Atlas for the buttons
        TextureAtlas atlas;
        atlas = new TextureAtlas(Gdx.files.internal("Button/ButtonPack.atlas"));

        // Set up Options Button
        ImageButton.ImageButtonStyle optionsButtonStyle = new ImageButton.ImageButtonStyle();
        optionsButtonStyle.up =  new TextureRegionDrawable(atlas.findRegion("Options"));
        optionsButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("OptionsClick"));
        optionsButtonStyle.over = new TextureRegionDrawable(atlas.findRegion("OptionsHover"));

        ImageButton optionsButton = new ImageButton(optionsButtonStyle);
        optionsButton.setSize(4.f * buttonWidthDivider * width, heightOfButton);
        optionsButton.setPosition( 7.f * buttonPositionDivider * width, 0);

        // Set up Play Button
        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("Play"));
        playButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("PlayClick"));
        playButtonStyle.over = new TextureRegionDrawable(atlas.findRegion("PlayHover"));

        ImageButton playButton = new ImageButton(playButtonStyle);
        playButton.setSize( 3.f * buttonWidthDivider * width, heightOfButton);
        playButton.setPosition(0, 0);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sm.setState(StateManager.GAME_LEVEL);
            }
        });

        // Set up Exit Button
        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable( atlas.findRegion("Exit"));
        exitButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("ExitClick"));
        exitButtonStyle.over = new TextureRegionDrawable(atlas.findRegion("ExitHover"));

        final ImageButton exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(3.f * buttonWidthDivider * width, heightOfButton);
        exitButton.setPosition(3.5f * buttonPositionDivider * width, 0);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Set up Load Button
        ImageButton.ImageButtonStyle loadButtonStyle = new ImageButton.ImageButtonStyle();
        loadButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("Load"));
        loadButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("LoadClick"));
        loadButtonStyle.over = new TextureRegionDrawable(atlas.findRegion("LoadHover"));

        final ImageButton loadButton = new ImageButton(loadButtonStyle);
        loadButton.setSize(3.f * buttonWidthDivider * width, heightOfButton);
        loadButton.setPosition(11.f * buttonPositionDivider * width, 0);

        // Set up Background
        Image background = new Image(new Texture("Menu Screen/BG1.png"));
        background.setSize(width, height);

        //add all objects to the stage
        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(loadButton);
        stage.addActor(exitButton);

        //Implement buttons and stuff that show up when the game starts.
        camera = new OrthographicCamera(width, height);

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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
