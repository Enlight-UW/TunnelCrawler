package club.enlight.software.entities.Enemy;


        import club.enlight.software.entities.GameObject;
        import club.enlight.software.entities.player.Bullet;
        import club.enlight.software.states.GameLevel;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.graphics.Camera;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.g2d.Animation;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
        import com.badlogic.gdx.math.Vector2;
        import java.util.Random;
        import java.util.ArrayList;
        import java.util.Iterator;
        import java.util.List;
/**
 * Created by jdallman2570 on 4/28/2015.
 */
public class Enemy extends GameObject {
    float velocity;
    Vector2 direction;
    Color color;
    GameLevel owner;
    // temporary shape for player
    ShapeRenderer renderer;
    int health;

    public Enemy(GameLevel owner, Vector2 position, Camera camera)
    {
        super(owner, position);
        Random rand=new Random();
        this.owner=owner;
        health =12;
        color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1.f);
        // set velocity to the player and make it stationary
        this.velocity = 60.f;
        this.direction = new Vector2();
        this.renderer = new ShapeRenderer();
        this.renderer.setProjectionMatrix(camera.combined);

    }

    @Override
    public void update(float dt)
    {
        this.direction = new Vector2(owner.getPlayerPos().x - direction.x, owner.getPlayerPos().y - direction.y);
        position.add(direction.x * velocity * dt, direction.y * velocity * dt);

    }

    @Override
    public void handleInput()
    {

    }

    @Override
    public void render()
    {
        renderer.setColor(color);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, 15.0f);
       // System.out.println("Position: " + position.x + "," + position.y);
        renderer.end();


    }

    @Override
    public void dispose() {

    }
}
