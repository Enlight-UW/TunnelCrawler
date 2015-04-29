package club.enlight.software.states;

import club.enlight.software.entities.Enemy.Enemy;
import club.enlight.software.entities.player.Bullet;
import club.enlight.software.entities.player.Player;
import club.enlight.software.handlers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class GameLevel extends State {
    Player player;
    ArrayList<Enemy> enemies;
    int width;
    int height;
    List<Bullet> bullets = new ArrayList();
    public GameLevel(final StateManager sm){
        super(sm);
        //Create a level (or load one) using getLevel from sm and Ryan's level generator, along with a room generator,
        //then play the level
        enemies = new ArrayList<Enemy>();
        Vector2 centerOfScreen = new Vector2(0,0); // Orthographic camera centers screen at (0,0)
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        OrthographicCamera camera = new OrthographicCamera(width, height);
        player = new Player(this, centerOfScreen, camera);
        enemies.add(new Enemy(this, new Vector2(100f,100f), camera));
    }
    @Override
    public void handleInput() {
        player.handleInput();
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        for(Enemy enemy: enemies){
            enemy.update(dt);
        }
    }

    @Override
    public void render() {
        player.render();
        for(Enemy enemy: enemies){
            enemy.render();
        }
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

    public Vector2 getPlayerPos()
    {
        return player.position;
    }
}
