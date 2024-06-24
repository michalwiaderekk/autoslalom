package p02.pres;

import p02.game.ScoreEventListener;
import p02.game.SevenSegmentDigit;

import javax.swing.*;
import java.awt.*;

public class LeftPanel
        extends JPanel
        implements
                ScoreEventListener {
    private SevenSegmentDigit digits;
    private ImageIcon[] digitIcons;
    private ImageIcon controls = new ImageIcon(".\\graphics\\controls.png");
    public LeftPanel(){
        this.setPreferredSize(new Dimension(222, 480));
        this.setBackground(Color.BLACK);
        this.digits = new SevenSegmentDigit(0);
        this.digitIcons = new ImageIcon[10];
        for (int i = 0; i < 10; i++) {
            digitIcons[i] = new ImageIcon(".\\graphics\\digits\\" + i + ".png");
        }
    }
    @Override
    public void scored(int value) {
        this.digits.setValue(value);
        updateGUI(this.getGraphics());
    }
    public void updateGUI(Graphics g){
        String scoreString = String.format("%03d", digits.getValue());
        if(digits.getValue() < 10){
            g.drawImage(digitIcons[digits.getValue() % 10].getImage(), 74 * 2, 10, null);
        } else if (digits.getValue() < 100){
            g.drawImage(digitIcons[digits.getValue() % 10].getImage(), 74 * 2, 10, null);
            g.drawImage(digitIcons[(digits.getValue()/10) % 10].getImage(), 74 * 1, 10, null);
        } else{
            g.drawImage(digitIcons[digits.getValue() % 10].getImage(), 74 * 2, 10, null);
            g.drawImage(digitIcons[(digits.getValue()/10) % 10].getImage(), 74 * 1, 10, null);
            g.drawImage(digitIcons[(digits.getValue()/100) % 10].getImage(), 74 * 0, 10, null);
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Draw each digit as an image
        for (int i = 0; i < 3; i++) {
            g.drawImage(digitIcons[0].getImage(), 74 * i, 10, null); // Adjust the position as needed
        }
        g.drawImage(controls.getImage(),0,200,null);
    }
}
