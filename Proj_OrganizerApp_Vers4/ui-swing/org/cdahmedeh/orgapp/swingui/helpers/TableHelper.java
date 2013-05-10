package org.cdahmedeh.orgapp.swingui.helpers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JViewport;

public class TableHelper {
	
	/**
	 * Source:
	 * http://stackoverflow.com/questions/853020/jtable-scrolling-to-a-specified-row-index
	 * 
	 * @param table
	 * @param rowIndex
	 * @param vColIndex
	 */
	public static void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        table.scrollRectToVisible(rect);

        // Scroll the area into view
        //viewport.scrollRectToVisible(rect);
    }

	public static void setupPopupMenu(final JTable table, final JPopupMenu popupMenu) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3){
					//Select the row where the user clicked
					//Credit: http://stackoverflow.com/questions/3558293/java-swing-jtable-right-click-menu-how-do-i-get-it-to-select-aka-highlight-t					
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
