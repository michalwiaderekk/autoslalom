package p02.game;

import java.util.EventObject;

public class ScoreEvent extends EventObject {
    private int value;
    public ScoreEvent(Object source, int value) {
        super(source);
        this.value = value;
    }
}
