package p02.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Board implements KeyListener, TickEventListener {
    private int[] board = new int[7];
    private int lastObstacle = 0;
    private int score;
    private int blankRows;
    private Random rand = new Random();
    private ArrayList<StartEventListener> startEventListeners;
    private ArrayList<ResetEventListener> resetEventListeners;
    private ArrayList<ScoreEventListener> scoreEventListeners;
    private ArrayList<UpdateGUIEventListener> updateGUIEventListeners;
    public Board(){
        this.score = 0;
        this.blankRows = 0;
        clearBoard();
        this.startEventListeners = new ArrayList<>();
        this.resetEventListeners = new ArrayList<>();
        this.scoreEventListeners = new ArrayList<>();
        this.updateGUIEventListeners = new ArrayList<>();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Obsluga klawiszy
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_D -> moveRight();
            case KeyEvent.VK_A -> moveLeft();
            case KeyEvent.VK_S -> notifyStartEvent();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    // Wyzeruj tabele i ustaw samochod na srodku
    private void clearBoard(){
        for(int i = 1; i < board.length; ++i){
            board[i] = 0;
        }
        board[0] = 2;
    }
    private void clearBoardGUI(){
        for(int i = 0; i < board.length; ++i){
            board[i] = 0;
            notifyUpdateGUIEvent(i,0);
        }
    }
    // Generuj przeszkody
    private void generateObstacles(){
        this.lastObstacle = board[1];
        for(int i = 2; i < board.length; ++i){
            if(board[i - 1] != board[i]){
                board[i - 1] = board[i];
                notifyUpdateGUIEvent(i - 1, board[i - 1]);
            }
        }
        int val = 0;
        do {
            val = rand.nextInt(8);
        }while(!playableObstacle(val));
        notifyUpdateGUIEvent(0,(lastObstacle * 10) + board[0]);
        board[board.length - 1] = val;
        notifyUpdateGUIEvent(board.length - 1,  val);
    }
    // Dodanie pustego wiersza
    private void addBlankRow(){
        this.lastObstacle = board[1];
        for(int i = 2; i < board.length; ++i){
            if(board[i - 1] != board[i]){
                board[i - 1] = board[i];
                notifyUpdateGUIEvent(i - 1, board[i - 1]);
            }
        }
        notifyUpdateGUIEvent(0,(lastObstacle * 10) + board[0]);
        board[board.length - 1] = 0;
        notifyUpdateGUIEvent(board.length - 1,  0);
    }
    // Sprawdz czy przeszkoda jest zgodna z duchem gry
    private boolean playableObstacle(int val){
        if(val == 7 || val == 0){
            return false;
        }
        int previous = board[board.length - 2];
        if ((previous | val) == 7) {
            return false;
        }
        for (int bit = 0; bit < 3; bit++) {
            if (((previous >> bit) & 1) == 1 && ((val >> bit) & 1) == 1) {
                return false;
            }
        }
        return true;
    }
    private void moveLeft() {
        if (((board[0] >> 0) & 1) == 1) {
            // Jedynka jest na pozycji 0, przenosimy ją na pozycję 1
            board[0] = (board[0] & ~(1 << 0)) | (1 << 1);
            notifyUpdateGUIEvent(0, board[0]);
        } else if (((board[0] >> 1) & 1) == 1) {
            // Jedynka jest na pozycji 1, przenosimy ją na pozycję 2
            board[0] = (board[0] & ~(1 << 1)) | (1 << 2);
            notifyUpdateGUIEvent(0, board[0]);
        }
        detectCollision();
    }

    private void moveRight() {
        if (((board[0] >> 1) & 1) == 1) {
            // Jedynka jest na pozycji 1, przenosimy ją na pozycję 0
            board[0] = (board[0] & ~(1 << 1)) | (1 << 0);
            notifyUpdateGUIEvent(0, board[0]);
        } else if (((board[0] >> 2) & 1) == 1) {
            // Jedynka jest na pozycji 2, przenosimy ją na pozycję 1
            board[0] = (board[0] & ~(1 << 2)) | (1 << 1);
            notifyUpdateGUIEvent(0, board[0]);
        }
    }
    // Sprawdz czy nastapila kolizja
    public boolean detectCollision(){
        if((lastObstacle & board[0]) == 1 || (lastObstacle & board[0]) == 2 || (lastObstacle & board[0]) == 4){
            notifyResetEvent();
            return true;
        } else if (lastObstacle != 0) {
            this.score++;
            notifyScoreEvent(this.score);
            return false;
        }
        return false;
    }
    public void addStartEventListener(StartEventListener listener) {
        startEventListeners.add(listener);
    }
    public void addResetEventListener(ResetEventListener listener) {
        resetEventListeners.add(listener);
    }
    public void addScoreEventListener(ScoreEventListener listener) {
        scoreEventListeners.add(listener);
    }
    public void addUpdateGUIEventListener(UpdateGUIEventListener listener) {
        updateGUIEventListeners.add(listener);
    }

    private void notifyStartEvent() {
        StartEvent startEvent = new StartEvent(this);
        for (StartEventListener listener : startEventListeners) {
            listener.startGame();
        }
        for (UpdateGUIEventListener listener : updateGUIEventListeners){
            for(int i = 0; i < board.length; ++i){
                listener.updateGUI(i,board[i]);
            }
        }
    }

    private void notifyResetEvent() {
        ResetEvent resetEvent = new ResetEvent(this);
        for (ResetEventListener listener : resetEventListeners) {
            listener.resetGame();
        }
        this.clearBoardGUI();
    }
    private void notifyScoreEvent(int value){
        ScoreEvent scoreEvent = new ScoreEvent(this, value);
        for (ScoreEventListener listener : scoreEventListeners){
            listener.scored(value);
        }
    }
    private void notifyUpdateGUIEvent(int index, int value){
        UpdateGUIEvent updateGUIEvent = new UpdateGUIEvent(this, index, value);
        for (UpdateGUIEventListener listener : updateGUIEventListeners){
            listener.updateGUI(index, value);
        }
    }

    @Override
    public void tickTack() {
        if(score == 0){
            if(blankRows == 4){
                blankRows = 1;
                generateObstacles();
            } else{
                addBlankRow();
                blankRows++;
            }
        } else if (score < 10){
            if(blankRows == 3){
                blankRows = 1;
                generateObstacles();
            } else{
                addBlankRow();
                blankRows++;
            }
        } else if(score >= 10){
            if(blankRows == 2){
                blankRows = 1;
                generateObstacles();
            } else{
                addBlankRow();
                blankRows++;
            }
        }
        detectCollision();
    }
}
