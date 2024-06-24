package p02.game;

import java.util.EventObject;

public class UpdateGUIEvent extends EventObject {
    private int index;
    private int value;
    public UpdateGUIEvent(Object source, int index, int value) {
        super(source);
        this.index = index;
        this.value = value;
    }
}
