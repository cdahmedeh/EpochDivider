package com.tronicdream.epochdivider.swingui.helpers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JViewport;

/**
 * This class contains various methods for simplifying some operations on JTable
 * components.
 * 
 * @author Ahmed El-Hajjar
 */
public class TableHelper {
	
	/**
	 * Scrolls table to the selected row. The table must be a child of
	 * JViewport.
	 * 
	 * Code has been taken from:
	 * http://stackoverflow.com/questions/853020/jtable-scrolling-to-a-specified-row-index
	 * 
	 * @param table The table to scroll.
	 * @param row Which row to scroll to.
	 * @param column Which column to scroll to.
	 */
	public static void scrollTableToCellAt(JTable table, int row, int column) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(row, column, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
    }

	/**
	 * Adds the supplied popupMenu to the table as a right click menu. Also 
	 * ensures that when right clicking the table, the correct table row is
	 * highlighted and selected. If the user right clicks into an empty area of
	 * the table, then no popup-menu is shown. 
	 * 
	 * The code has been inspired from:
	 * http://stackoverflow.com/questions/3558293/java-swing-jtable-right-click-menu-how-do-i-get-it-to-select-aka-highlight-t
	 * 
	 * @param table What table to attach the popup menu to.
	 * @param popupMenu What popup menu to attach to the table.
	 */
	public static void setupPopupMenu(final JTable table, final JPopupMenu popupMenu) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3){
					int r = table.rowAtPoint(e.getPoint());
					if (r >= 0 && r < table.getRowCount()) {
						table.setRowSelectionInterval(r, r);
						popupMenu.show(table, e.getX(), e.getY());
					} else {
						table.clearSelection();
					}
				}
			}
		});
	}
}
