package p02.game;

import jdk.jfr.Event;

import java.util.EventObject;

public class TickEvent extends EventObject {
    public TickEvent(Object source){
        super(source);
    }
}
