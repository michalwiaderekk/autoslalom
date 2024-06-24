package p02.game;

public interface PassMVC {
    public Board getBoard();
    public void setBoard(Board board);
    public GameLoop getGameLoop();
    public void setGameLoop(GameLoop gameLoop);
}
