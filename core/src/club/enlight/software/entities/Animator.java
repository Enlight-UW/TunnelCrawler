package club.enlight.software.entities;

/**
 * Created by arel on 3/11/15.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Animator {
    private TextureAtlas textureAtlas;
    private Animation animation;
    private int xLocation = 0;
    private int yLocation = 0;
    
    public Animator(String fileName, int x, int y) {
        textureAtlas = new TextureAtlas(Gdx.files.internal(fileName));
        animation = new Animation(1/15f,textureAtlas.getRegions()); //This 20 is the frames per second
        xLocation = x;
        yLocation = y;
    }
    public Animation getAnimation() {
        return animation;
    }
    public int getXLocation() {
        return xLocation;
    }
    public int getYLocation() {
        return yLocation;
    }
}
