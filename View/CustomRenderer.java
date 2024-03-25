package View;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CustomRenderer extends DefaultTableCellRenderer
{
    private int[][] frameHistory;

    public CustomRenderer(int[][] frameHistory)
    {
        this.frameHistory = frameHistory;
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (row < frameHistory.length && column < frameHistory[row].length)
        {
            if (frameHistory[row][column] == 0)
            {
                cellComponent.setForeground(Color.RED);
            } else if (frameHistory[row][column] == 1)
            {
                cellComponent.setForeground(Color.YELLOW);
            } else
            {
                // Reset font and background color for other values
                cellComponent.setForeground(table.getForeground());
            }
        } else
        {
            // Reset font and background color for invalid indices
            cellComponent.setForeground(table.getForeground());
        }

        // Set semi-transparent background color
        Color backgroundColor = new Color(173, 216, 230, 150); // Adjust the alpha value (128) for opacity
        cellComponent.setBackground(backgroundColor);

        return cellComponent;
    }

    public static void setRowHeightBasedOnContent(JTable table)
    {
        for (int row = 0; row < table.getRowCount(); row++)
        {
            int maxHeight = 0;
            for (int column = 0; column < table.getColumnCount(); column++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component cellComponent = table.prepareRenderer(cellRenderer, row, column);
                int cellHeight = cellComponent.getPreferredSize().height;
                maxHeight = Math.max(maxHeight, cellHeight);
            }
            table.setRowHeight(row, maxHeight);
        }
    }

}
