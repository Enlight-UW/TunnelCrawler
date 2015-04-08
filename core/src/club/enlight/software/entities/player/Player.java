package club.enlight.software.entities.player;

import club.enlight.software.entities.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kenneth on 3/24/15.
 */
public class Player extends GameObject {
    float velocity;
    boolean up;
    boolean down;
    boolean right;
    boolean left;

    // temporary shape for player
    ShapeRenderer renderer;

    public Player(Vector2 position, Camera camera)
    {
        super(position);

        // set velocity to the player and make it stationary
        this.velocity = 100.f;
        up = false;
        down = false;
        left = false;
        right = false;

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void update(float dt)
    {
        if(up)
        {
            position.add(0, dt * velocity);
        }

        if(down)
        {
            position.add(0, -dt * velocity);
        }

        if(left)
        {
            position.add(-dt * velocity, 0);
        }

        if(right)
        {
            position.add(dt * velocity, 0);
        }
    }

    @Override
    public void handleInput()
    {
        up = false;
        down = false;
        left = false;
        right = false;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            up = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            left = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            right = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            down = true;
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
    }

    @Override
    public void dispose() {

    }
}
