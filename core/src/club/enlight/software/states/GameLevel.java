package club.enlight.software.states;

import club.enlight.software.entities.GameObject;
import club.enlight.software.entities.player.Bullet;
import club.enlight.software.entities.player.Player;
import club.enlight.software.handlers.StateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class GameLevel extends State {
    Player player;
    int width;
    int height;
    ArrayList<GameObject> enemies = new ArrayList<GameObject>();

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

        for(int i = 0; i < enemies.size(); i++){
            GameObject enemy = enemies.get(i);

            //See if the player hit the enemy
            if(player.sprite.getBoundingRectangle().overlaps(enemy.sprite.getBoundingRectangle())){
                //TODO: If the cooldown is 0, do 5 damage to the player
            }

            //See if the player hit one of the enemy's bullets
            /*for(int j = 0; j < enemy.bullets.size(); j++){
                player.health -= 10;
                bullet.dispose();
                enemy.bullets.remove(j);
            }
            */
            
            //See if the enemy hit one of the player's bullets
            for(int j = 0; j < player.bullets.size(); j++){
                Bullet bullet = player.bullets.get(j);

                if(enemy.sprite.getBoundingRectangle().overlaps(bullet.sprite.getBoundingRectangle())){
                    //TODO: Do 10 damage to the enemy
                    bullet.dispose();
                    player.bullets.remove(j);
                }
            }


        }

        //Detect if the player's bullets hit an enemy

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
