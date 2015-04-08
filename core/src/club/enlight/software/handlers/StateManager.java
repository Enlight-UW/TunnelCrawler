package club.enlight.software.handlers;

import club.enlight.software.states.GameLevel;
import club.enlight.software.Main;
import club.enlight.software.states.MainMenu;
import club.enlight.software.states.PauseMenu;
import club.enlight.software.states.State;

import java.util.Stack;
/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class StateManager {
    private Main game;
    private Stack<State> stateStack;
    public static final int MAIN_MENU = 0;
    public static final int PAUSE_MENU = 1;
    public static final int GAME_LEVEL = 2;
    public static int level;
    //constructor
    public StateManager(Main main)
    {
        this.game = main;
        stateStack= new Stack<State>();
        pushState(MAIN_MENU);
        level = 0;
    }

    public Main getGame() { return game; }

    //update only the top state in the stack
    public void update(float dt) {
        stateStack.peek().handleInput();
        stateStack.peek().update(dt);
    }

    //render all states in the stack
    public void render() {
        //in java, this will go from bottom to the top
        for(State state : stateStack) {
            state.render();
        }
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    private State getState(int state) {

        //find the state to get
        if(state == MAIN_MENU) {
            return new MainMenu(this);
        } else if(state == PAUSE_MENU) {
            return new PauseMenu(this);
        } else if(state == GAME_LEVEL) {
            return new GameLevel(this);
        }

        return null;
    }

    public void pushState(int state) {
        stateStack.push(getState(state));
    }

    public void popState() {
        State state = stateStack.pop();
        state.dispose();
    }

    //To be used to select the next level to be played.
    public void setLevel(int lev) {
        level = lev;
    }

    //To be used by the club.enlight.software.states.GameLevel constructor to create a level with the correct attributes.
    public int getLevel()
    {
        return level;
    }
}
