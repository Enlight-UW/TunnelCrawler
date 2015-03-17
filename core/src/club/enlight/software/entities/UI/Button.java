package club.enlight.software.entities.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by jdallman2570 on 3/3/2015.
 */
public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button{
    String imgPath;
    String actionName;
    int x;
    int y;
    int width;
    int height;
    public Button(String imgPath, String actionName, int x, int y, int width, int height){
        super(new Skin(new FileHandle(imgPath)));
        this.actionName=actionName;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;


    }
    public String getActionName(){
        return actionName;
    }
    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }

}
