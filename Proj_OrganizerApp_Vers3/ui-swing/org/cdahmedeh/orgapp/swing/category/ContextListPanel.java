package org.cdahmedeh.orgapp.swing.category;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.main.SwingUIDefaults;

public class ContextListPanel extends JPanel {
	private static final long serialVersionUID = 4463133789121204761L;

	private BigContainer bigContainer;
	
	private JScrollPane mainPane;
	private JTable contextListTable;
	
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		this.setPreferredSize(new Dimension(SwingUIDefaults.CONTEXT_PANEL_WIDTH, 0));
		this.setLayout(new BorderLayout(0, 0));
		
		mainPane = new JScrollPane();
		add(mainPane);
		
		createContextListTable();
		mainPane.setViewportView(contextListTable);
	}

	private void createContextListTable() {
		contextListTable = new JTable(){};
		contextListTable.setFillsViewportHeight(true);
		contextListTable.setShowHorizontalLines(false);
		contextListTable.setShowVerticalLines(false);
		contextListTable.setModel(new ContextListTableModel(bigContainer));
		contextListTable.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());
	}
	
	private class ProgressCellRenderer extends DefaultTableCellRenderer {
		private ProgressInfo savedValue;

		@Override
		protected void setValue(Object value) {
			this.savedValue = (ProgressInfo)value;
		}

		@Override
		public void paint(Graphics g) {
			double start = savedValue.first;
			double total = savedValue.third;
			
			g.setColor(Color.LIGHT_GRAY);
			if (total > 0){
			g.fillRoundRect(1, 1, this.getBounds().width-2, this.getBounds().height-2, 5, 5);
			}
			g.setColor(Color.GRAY);
			g.fillRoundRect(1, 1, (int)(((start/total))*this.getBounds().width)-2, this.getBounds().height-2, 5, 5);
			g.setColor(Color.BLACK);
			g.drawRoundRect(1, 1, this.getBounds().width-2, this.getBounds().height-2, 5, 5);

			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(start), 5, this.getBounds().height-2);
		
			int stringWidth = g.getFontMetrics().stringWidth("10.0");
			
			g.drawString(String.valueOf(total), this.getBounds().width - stringWidth-5, this.getBounds().height-2);
			

			
//			super.paint(g);
		}
	}
}
