package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.graphics.Rectangle;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

public class CalendarPanel extends JPanel {
	private static final long serialVersionUID = -4698663372347441273L;
	
	//Data
	private BigContainer bigContainer;
	
	//Maps
	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	private HashMap<Rectangle, TimeBlock> rectangleTimeBlockMap = new HashMap<>();
	
	//Drag support
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	private TimeBlock timeBlockDragged;
	private Duration timeClickedOffset;
	
	public CalendarPanel(BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setPreferredSize(new Dimension(100, 750));
		setBackground(Color.WHITE);
		
		fillTimeBlockTaskMap();
		makeCalendarBlocksDraggable();
		allowDropToCalendar();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		GraphicsHelper.enableAntiAliasing(g);

        GridPainter.drawTimeGrid(g, this, bigContainer.getCurrentView());
      		
        rectangleTimeBlockMap.clear();

        for (Entry<TimeBlock, Task> entry : timeBlockTaskMap.entrySet()){
        	ArrayList<Rectangle> rectangles = TimeBlockPainter.draw(entry.getKey(), entry.getValue(), bigContainer.getCurrentView(), g, this);
        	for (Rectangle rectangle: rectangles){
        		rectangleTimeBlockMap.put(rectangle, entry.getKey());
        	}
		}
        
        GridPainter.drawCurrentTime(this, bigContainer.getCurrentView(), g);
	}
	
	private void allowDropToCalendar() {
		this.setTransferHandler(new TransferHandler(){
			@Override
			public boolean canImport(TransferSupport support) {
				if (uiMode == CalendarUIMode.DRAG){
					Duration duration = timeBlockDragged.getDuration();
					timeBlockDragged.setStart(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(support.getDropLocation().getDropPoint().x, support.getDropLocation().getDropPoint().y, new Rectangle(0, 0, getWidth(), getHeight()), bigContainer.getCurrentView()).minus(timeClickedOffset), 15));
					timeBlockDragged.setEnd(timeBlockDragged.getStart().plus(duration));
					repaint();
					return true;
				} else if (uiMode == CalendarUIMode.RESIZE_BOTTOM) {
					timeBlockDragged.setEnd(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(support.getDropLocation().getDropPoint().x, support.getDropLocation().getDropPoint().y, new Rectangle(0, 0, getWidth(), getHeight()), bigContainer.getCurrentView()), 15));
					repaint();
					return true;
				} 
				
				
				
				Transferable trans = support.getTransferable();
				System.out.println(trans);
//				if (trans instanceof StringSelection){
					try {
						System.out.println("WE ARE HERE");
						Object transferData = trans.getTransferData(DataFlavor.stringFlavor);
						
						    Task selectedTask = bigContainer.getTaskContainer().getTaskFromId(Integer.valueOf((String) transferData));
						    TimeBlock newTimeBlock = new TimeBlock();
						    selectedTask.assignToTimeBlock(newTimeBlock);
						    timeBlockDragged = newTimeBlock;
						    timeClickedOffset = new Duration(15*DateTimeConstants.MILLIS_PER_MINUTE);
						//        eventBus.post(new TasksModifiedNotification());
						    //TODO: test only
						    timeBlockTaskMap.clear();
						    fillTimeBlockTaskMap();
						    repaint();
						    
						    uiMode = CalendarUIMode.DRAG;
						//        System.out.println(selectedTask.getTitle());
						    repaint();
						  
						
					} catch (UnsupportedFlavorException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}			}
					return true;
			}
			
			@Override
			public boolean importData(TransferSupport support) {
//				System.out.println("WE ARE HERE");

				System.out.println("MOUSE RELEASE");
				uiMode = CalendarUIMode.NONE;
//				eventBus.post(new TasksModifiedNotification());
				repaint();
				return true;
			}
		});
	}

	public void fillTimeBlockTaskMap() {
		for (Task task: bigContainer.getTaskContainer().getAllTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()){
				timeBlockTaskMap.put(timeBlock, task);
			}
		}
	}
	

	
	private void makeCalendarBlocksDraggable() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				System.out.println("MOUSE RELEASE");
				uiMode = CalendarUIMode.NONE;
//				eventBus.post(new TasksModifiedNotification());
				repaint();
			}
		});
		
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(java.awt.event.MouseEvent e) {

				if (uiMode == CalendarUIMode.NONE){
				for (Rectangle r: rectangleTimeBlockMap.keySet()){ //TODO: Use Entry set.
					if (r.contains(e.getX(),e.getY())) {
						System.out.println("WE ARE DRAGGING");
						if ((r.y+r.height)-e.getY()<=10) {
							uiMode = CalendarUIMode.RESIZE_BOTTOM;
						} else {
							uiMode = CalendarUIMode.DRAG;
						}
						timeBlockDragged = rectangleTimeBlockMap.get(r);
						timeClickedOffset  = new Duration(timeBlockDragged.getStart(), PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), new Rectangle(0, 0, getWidth(), getHeight()), bigContainer.getCurrentView()));
					}
				}
				}
				if (uiMode == CalendarUIMode.DRAG){
					dragTimeBlock(e);
					repaint();
				} else if (uiMode == CalendarUIMode.RESIZE_BOTTOM) {
					resizeBottomTimeBlock(e);
					repaint();
				} 
			}
		});
	}
	
	private void resizeBottomTimeBlock(java.awt.event.MouseEvent e) {
		timeBlockDragged.setEnd(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), new Rectangle(0, 0, getWidth(), getHeight()), bigContainer.getCurrentView()), 15));
	}

	private void dragTimeBlock(java.awt.event.MouseEvent e) {
		Duration duration = timeBlockDragged.getDuration();
		timeBlockDragged.setStart(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), new Rectangle(0, 0, getWidth(), getHeight()), bigContainer.getCurrentView()).minus(timeClickedOffset), 15));
		timeBlockDragged.setEnd(timeBlockDragged.getStart().plus(duration));
	}
	
	
}
