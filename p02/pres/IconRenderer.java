package p02.pres;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IconRenderer extends DefaultTableCellRenderer {
    public IconRenderer(){super();}
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Icon) {
            label.setIcon((Icon) value);
            label.setText("");
        } else {
            label.setIcon(null);
            label.setText(value != null ? value.toString() : "");
        }
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(new EmptyBorder(0,0, 0, 20 * row));
        return label;
    }
}
