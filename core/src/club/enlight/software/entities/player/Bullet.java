package club.enlight.software.entities.player;

import club.enlight.software.states.GameLevel;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import club.enlight.software.entities.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by arel on 4/14/15.
 */
public class Bullet extends GameObject{
    float velocity;
    private Vector2 direction;
    private float timeToLive;

    // temporary shape for player
    ShapeRenderer renderer;
    public Bullet(GameLevel owner, Vector2 position, ShapeRenderer renderer, Vector2 direction)
    {
        super(owner, position);

        // set velocity to the bullet and make it goe the direction player faced
        this.renderer = renderer;
        this.velocity = 300.f;
        this.direction = direction;
        this.timeToLive = 2.0f;
    }

    @Override
    public void update(float dt)
    {
        position.add(dt * velocity * direction.x, dt * velocity * direction.y);
        timeToLive -= dt;
    }
    @Override
    public void render()
    {
        renderer.setColor(Color.BLUE);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, 15.0f);
        renderer.end();
    }
    public boolean isExpired() {
        return this.isOutOfBounds();
    }
    @Override
    public void dispose() {

    }
    @Override
    public void handleInput() {

    }
}
