package club.enlight.software.entities;

import club.enlight.software.states.GameLevel;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;
    public GameLevel owner;

    public GameObject(GameLevel owner, Vector2 position)
    {
        this.owner = owner;
        this.position = position;
    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render();
    public abstract void dispose();

    public boolean isOutOfBounds() {
        return  Math.abs(this.position.x) * 2 > owner.getWidth() ||
                Math.abs(this.position.y) * 2 > owner.getHeight();
    }
}