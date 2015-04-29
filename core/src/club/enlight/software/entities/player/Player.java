package club.enlight.software.entities.player;

import club.enlight.software.entities.GameObject;
import club.enlight.software.states.GameLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Kenneth on 3/24/15.
 */
public class Player extends GameObject {
    public float velocity;
    public Vector2 direction;
    public List<Bullet> bullets = new ArrayList();
    public float coolDown;
    public int health;

    // temporary shape for player
    ShapeRenderer renderer;

    public Player(GameLevel owner, Vector2 position, Camera camera)
    {
        super(owner, position);

        // set velocity to the player and make it stationary
        this.velocity = 100.f;
        this.direction = new Vector2();
        this.renderer = new ShapeRenderer();
        this.renderer.setProjectionMatrix(camera.combined);
        this.coolDown = -1;

        this.health = 100;
    }

    @Override
    public void update(float dt)
    {
        coolDown -= dt;
        position.add(direction.x * velocity * dt, direction.y * velocity * dt);
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(dt);
            if (bullet.isExpired()) {
                bullet.dispose();
                bulletIterator.remove();
            }
        }
    }

    @Override
    public void handleInput()
    {
        direction.set(0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            direction.add(0,1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            direction.add(-1,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            direction.add(1,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            direction.add(0,-1);
        }
        direction.nor();

        //Bullet direction- Change keys as necessary
        if (coolDown < 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                bullets.add(new Bullet(owner, new Vector2(position), renderer, new Vector2(0, 1)));
                coolDown = 0.5f;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                bullets.add(new Bullet(owner, new Vector2(position), renderer, new Vector2(-1, 0)));
                coolDown = 0.5f;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                bullets.add(new Bullet(owner, new Vector2(position), renderer, new Vector2(1, 0)));
                coolDown = 0.5f;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                bullets.add(new Bullet(owner, new Vector2(position), renderer, new Vector2(0, -1)));
                coolDown = 0.5f;
            }
        }
    }

    @Override
    public void render()
    {
        renderer.setColor(Color.RED);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, 15.0f);
        System.out.println("Position: " + position.x + "," + position.y);
        renderer.end();

        for (Bullet bullet : bullets) {
            bullet.render();
        }
    }

    @Override
    public void dispose() {

    }
}
