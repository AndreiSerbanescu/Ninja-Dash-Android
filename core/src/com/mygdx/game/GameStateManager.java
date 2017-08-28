package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.AbstractState;

import java.util.Stack;

public class GameStateManager {

    private Stack<AbstractState> states;

    public GameStateManager() {
        states = new Stack<AbstractState>();
    }

    public void push(AbstractState state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(AbstractState state) {
        states.pop();
        states.push(state);
    }

    public void update(float deltaTime) {
        states.peek().update(deltaTime);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
