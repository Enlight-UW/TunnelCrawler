package club.enlight.software.states;

import club.enlight.software.handlers.StateManager;
import club.enlight.software.states.State;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class GameLevel extends State {

    public GameLevel(final StateManager sm){
        super(sm);
        //Create a level (or load one) using getLevel from sm and Ryan's level generator, along with a room generator,
        //then play the level

    }
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
