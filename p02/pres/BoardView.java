package p02.pres;

import p02.game.PassMVC;
import p02.game.TickEventListener;
import p02.game.UpdateGUIEventListener;

import javax.swing.*;
import java.awt.*;

public class BoardView
        extends JTable
        implements
            UpdateGUIEventListener {
    private IconRenderer iconRenderer;
    public BoardView(){
        super(7,1);
        this.iconRenderer = new IconRenderer();
        this.setRowHeight(65);
        this.setPreferredSize(new Dimension(464,460));
        this.getColumnModel().getColumn(0).setCellRenderer(new IconRenderer());
        for(int i = 0; i < 7; ++i){
            setValueAt(new ImageIcon(".\\graphics\\obstacles\\0.png"), i, 0);
        }
        this.setGridColor(new Color(207,216,195,255));
        this.setBackground(new Color(207,216,195,255));
    }
    // Skalowanie obrazkow
    private ImageIcon scaleImage(ImageIcon icon, int row) {
        int width = 364 - (25 * (6 - row));
        int height = 65 - (10 * (6 - row));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
    @Override
    public void updateGUI(int index, int value) {
        if(index >= 1){
            setValueAt(scaleImage(new ImageIcon(".\\graphics\\obstacles\\" + value + ".png"), 6 - index), 6 - index, 0);
        } else {
            setValueAt(scaleImage(new ImageIcon(".\\graphics\\car\\" + value + ".png"),6), 6, 0);
        }

    }
    // Komorki tabeli nie moga byc edytowane
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}
