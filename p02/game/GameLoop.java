package p02.game;

import java.util.ArrayList;

public class GameLoop
        extends Thread
        implements
            StartEventListener,
            ResetEventListener,
            ScoreEventListener{
    private Board board;
    private int speed;
    private boolean running;
    private ArrayList<TickEventListener> listeners;
    public GameLoop(Board b){
        this.speed = 1000;
        this.board = b;
        this.running = false;
        this.listeners = new ArrayList<>();
    }
    public void run(){
        try {
            do{
                notifyTickEvent();
                Thread.sleep(this.speed);
            }while(this.running);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void addTickEventListener(TickEventListener listener) {
        listeners.add(listener);
    }

    private void notifyTickEvent() {
        TickEvent tickEvent = new TickEvent(this);
        for (TickEventListener listener : listeners) {
            listener.tickTack();
        }
    }

    @Override
    public void startGame() {
        if(!this.running){
            this.running = true;
            this.start();
        }
    }

    @Override
    public void resetGame() {
        this.running = false;
    }

    @Override
    public void scored(int value) {
        this.speed -= 5;
    }
}
