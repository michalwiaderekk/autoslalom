import p02.game.Board;
import p02.game.GameLoop;
import p02.pres.*;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
    public Main(){
        Board board = new Board();


        GameLoop gameLoop = new GameLoop(board);
        board.addStartEventListener(gameLoop);
        board.addResetEventListener(gameLoop);
        board.addScoreEventListener(gameLoop);


        LeftPanel lp = new LeftPanel();
        CenterPanel cp = new CenterPanel();

        this.setLayout(new BorderLayout());


        this.add(lp, BorderLayout.WEST);
        this.add(cp, BorderLayout.CENTER);


        BoardView bv = new BoardView();
        cp.addKeyListener(board);
        cp.setFocusable(true);
        gameLoop.addTickEventListener(board);
        board.addScoreEventListener(lp);
        board.addUpdateGUIEventListener(bv);
        cp.add(bv);
        this.setSize( 740, 480);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}