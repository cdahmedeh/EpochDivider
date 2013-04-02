package org.cdahmedeh.orgapp.swing.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.containers.ContextContainer;
import org.cdahmedeh.orgapp.containers.TaskContainer;
import org.cdahmedeh.orgapp.swing.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swing.category.ContextListPanel;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainSwingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN,12));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainSwingWindow window = new MainSwingWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainSwingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JXMultiSplitPane jxMultiSplitPane = new JXMultiSplitPane();		
		jxMultiSplitPane.setModel(new MySplitPlaneModel());
		frame.getContentPane().add(jxMultiSplitPane);
		
		View currentView = new View(new LocalDate(2013,3,31), new LocalDate(2013,3,31).plusDays(7), new LocalTime(0, 0, 0), new LocalTime(23, 59, 59, 999));
		
		ContextContainer contextContainer = new ContextContainer();
		Context context = new Context("Essentials");
		contextContainer.addContext(context);
		
		TaskContainer taskContainer = new TaskContainer();
		Task task01 = new Task("Do Work");
		task01.assignToTimeBlock(new TimeBlock(new DateTime(), new DateTime().plus(new Duration(DateTimeConstants.MILLIS_PER_DAY*3))));
		Task task02 = new Task("Do More Work");
		task02.assignToTimeBlock(new TimeBlock(new DateTime().minus(DateTimeConstants.MILLIS_PER_DAY), new Duration(DateTimeConstants.MILLIS_PER_HOUR*2)));
		task02.setContext(context);
		taskContainer.addTask(task01);
		taskContainer.addTask(task02);

		BigContainer bigContainer = new BigContainer(taskContainer, contextContainer, currentView);
		
		ContextListPanel contextListPanel = new ContextListPanel(bigContainer);
		jxMultiSplitPane.add(contextListPanel, DefaultSplitPaneModel.BOTTOM);
		
		TaskListPanel taskListPanel = new TaskListPanel(bigContainer);
		jxMultiSplitPane.add(taskListPanel, DefaultSplitPaneModel.LEFT);
		
		JXMonthView jxDatePicker = new JXMonthView();
		jxDatePicker.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
		jxMultiSplitPane.add(jxDatePicker, DefaultSplitPaneModel.TOP);
		
		jxDatePicker.setSelectionInterval(currentView.getStartDate().toDate(), currentView.getEndDate().toDate());
		jxDatePicker.setFirstDayOfWeek(Calendar.MONDAY);
		
		CalendarPanel calendarPanel = new CalendarPanel(bigContainer);
		jxMultiSplitPane.add(calendarPanel, MySplitPlaneModel.RIGHT);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNPreviousButton = new JButton("Previous week");
		btnNPreviousButton.setIcon(new ImageIcon(MainSwingWindow.class.getResource("/org/cdahmedeh/orgapp/ui/icons/previous.png")));
		toolBar.add(btnNPreviousButton);
		
		JButton btnNextButton = new JButton("Next week");
		btnNextButton.setIcon(new ImageIcon(MainSwingWindow.class.getResource("/org/cdahmedeh/orgapp/ui/icons/next.png")));
		toolBar.add(btnNextButton);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mnNewMenu.add(mntmNewMenuItem);
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	} 
}

class MySplitPlaneModel extends Split {
    public static final String LEFT = "left";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String RIGHT = "right";
    
    /** Creates a new instance of DefaultSplitPaneLayout */
    public MySplitPlaneModel() {
        Split row = new Split();
        Split col = new Split();
        col.setRowLayout(false);
        Split coltwo = new Split();
        coltwo.setRowLayout(false);
        setChildren(col, new Divider(), coltwo);
        col.setChildren(new Leaf(TOP), new Divider(), new Leaf(BOTTOM));
        coltwo.setChildren(new Leaf(RIGHT), new Divider(), new Leaf(LEFT));
    }
    
}