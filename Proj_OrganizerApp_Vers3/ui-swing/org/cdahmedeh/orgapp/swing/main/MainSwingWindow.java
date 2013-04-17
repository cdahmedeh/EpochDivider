package org.cdahmedeh.orgapp.swing.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.containers.ContextContainer;
import org.cdahmedeh.orgapp.containers.TaskContainer;
import org.cdahmedeh.orgapp.swing.calendar.BigCalendarPanel;
import org.cdahmedeh.orgapp.swing.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swing.category.ContextListPanel;
import org.cdahmedeh.orgapp.swing.task.TaskListPanel;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.AllContexts;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.DueTodayContext;
import org.cdahmedeh.orgapp.types.category.NoContext;
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
import org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel;

import com.alee.laf.WebLookAndFeel;
import com.google.common.eventbus.EventBus;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainSwingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		try {
//			setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN,12));
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//			UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
//			UIManager.setLookAndFeel(new PlasticLookAndFeel());
//			UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
//			UIManager.setLookAndFeel(new WebLookAndFeel());
//			WebLookAndFeel.install();	
//		    UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );
		} catch (Exception e1) {
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

	boolean attemptedCompactMode = true;
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println(System.currentTimeMillis());
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		EventBus eventBus = new EventBus();
		
		JXMultiSplitPane jxMultiSplitPane = new JXMultiSplitPane();
		jxMultiSplitPane.setBorder(null);
		jxMultiSplitPane.setDividerSize(2);
		jxMultiSplitPane.setModel(new MySplitPlaneModel(attemptedCompactMode));
		frame.getContentPane().add(jxMultiSplitPane);
		
		final BigContainer bigContainer = generateSomeTestData();
		fill(bigContainer.getContextContainer(), bigContainer.getTaskContainer(), bigContainer.getCurrentView());
		
		final ContextListPanel contextListPanel = new ContextListPanel(bigContainer);
		jxMultiSplitPane.add(contextListPanel, DefaultSplitPaneModel.BOTTOM);
		
		final TaskListPanel taskListPanel = new TaskListPanel(bigContainer);
		jxMultiSplitPane.add(taskListPanel, DefaultSplitPaneModel.LEFT);
		
		final JXMonthView jxDatePicker = new JXMonthView();
		jxDatePicker.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		jxDatePicker.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
		
		if (attemptedCompactMode == false) {
		jxMultiSplitPane.add(jxDatePicker, DefaultSplitPaneModel.TOP);
		}
		
		contextListPanel.setEventBus(eventBus);
		
		jxDatePicker.setSelectionInterval(bigContainer.getCurrentView().getStartDate().toDate(), bigContainer.getCurrentView().getEndDate().toDate());
		jxDatePicker.setFirstDayOfWeek(Calendar.MONDAY);
		jxDatePicker.setBoxPaddingX(1);
		jxDatePicker.setBoxPaddingY(1);
		
		final BigCalendarPanel calendarPanel = new BigCalendarPanel(bigContainer);
		calendarPanel.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		jxMultiSplitPane.add(calendarPanel, MySplitPlaneModel.RIGHT);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNPreviousButton = new JButton("Previous week");
		btnNPreviousButton.setIcon(new ImageIcon(MainSwingWindow.class.getResource("/org/cdahmedeh/orgapp/ui/icons/previous.png")));
		toolBar.add(btnNPreviousButton);
		
		btnNPreviousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getCurrentView().moveAmountOfDays(-7);	
				calendarPanel.repaint();
				taskListPanel.repaint();
				contextListPanel.repaint();
			}
		});
		
		JButton btnNextButton = new JButton("Next week");
		btnNextButton.setIcon(new ImageIcon(MainSwingWindow.class.getResource("/org/cdahmedeh/orgapp/ui/icons/next.png")));
		toolBar.add(btnNextButton);
		
		btnNextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getCurrentView().moveAmountOfDays(7);	
				calendarPanel.repaint();
				taskListPanel.repaint();
				contextListPanel.repaint();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		jxDatePicker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bigContainer.getCurrentView().setStartDate(new LocalDate(jxDatePicker.getFirstSelectionDate()));	
				bigContainer.getCurrentView().setEndDate((new LocalDate(jxDatePicker.getLastSelectionDate())));	
				calendarPanel.repaint();
				taskListPanel.repaint();
				contextListPanel.repaint();
			}
		});
		
		taskListPanel.setEventBus(eventBus);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mnNewMenu.add(mntmNewMenuItem);
		System.out.println(System.currentTimeMillis());
		
		
	}
	
	private BigContainer generateSomeTestData() {
		View currentView = new View(new LocalDate(2013, 4, 15), new LocalDate(2013, 4, 15).plusDays(6));
		
		ContextContainer contextContainer = new ContextContainer();
		contextContainer.addContext(new AllContexts());
		contextContainer.addContext(new NoContext());
		
		contextContainer.addContext(new DueTodayContext());
		contextContainer.addContext(new Context("due tomorrow"));
		contextContainer.addContext(new Context("due this view"));
		
//		Context context = new Context("Essentials 2");
//		contextContainer.addContext(context);
//		context.setGoal(currentView, new Duration(DateTimeConstants.MILLIS_PER_HOUR*150));
//		Context context2 = new Context("Fun");
//		contextContainer.addContext(context2);
//		context2.setGoal(currentView, new Duration(DateTimeConstants.MILLIS_PER_HOUR*35));
		
		TaskContainer taskContainer = new TaskContainer();
//		task01.assignToTimeBlock(new TimeBlock(new DateTime(), new DateTime().plus(new Duration(DateTimeConstants.MILLIS_PER_DAY*3))));
//		for (int i = 0; i<40; i++) {
//		Task task02 = new Task("Do More Work " + i);
////		task02.assignToTimeBlock(new TimeBlock(new DateTime().minus(DateTimeConstants.MILLIS_PER_DAY), new Duration(DateTimeConstants.MILLIS_PER_HOUR*2)));
//		task02.setContext(context);
//		task02.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*15));
//		task02.setContext(context2);
//		taskContainer.addTask(task02);
//		}

		
		Context epochDivider = new Context("Epoch Divider");
		contextContainer.addContext(epochDivider);
		epochDivider.setGoal(currentView, Duration.standardHours(10));
		Context misc = new Context("Misc");
		contextContainer.addContext(misc);
		misc.setGoal(currentView, Duration.standardHours(5));
		Context gaming = new Context("Gaming");
		contextContainer.addContext(gaming);
		gaming.setGoal(currentView, Duration.standardHours(9));
		
		Context reading = new Context("Reading");
		contextContainer.addContext(reading);
		reading.setGoal(currentView, Duration.standardHours(6));
		
		Context assgnmt = new Context("Assignments");
		contextContainer.addContext(assgnmt);
		assgnmt.setGoal(currentView, Duration.standardHours(7));
		
		Task task01 = new Task("The Art of Unix Programming - Eric Raymond");
		task01.setContext(reading);
		task01.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*12));
		taskContainer.addTask(task01);
		
		Task task02 = new Task("Implement SQL Subsystem");
		task02.setContext(epochDivider);
		task02.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*15));
		task02.setDueDate(DateTime.now());
		taskContainer.addTask(task02);
		
		Task task03 = new Task("CSI2520 Assignment 4");
		task03.setContext(assgnmt);
		task03.setDueDate(new DateTime(2013, 4, 20, 15, 30));
		task03.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*15));
		taskContainer.addTask(task03);
		
		Task task04 = new Task("Pay Internet Bill");
		task04.setContext(misc);
		task04.setDueDate(new DateTime(2013, 4, 21, 23, 30));
		task04.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*1));
		taskContainer.addTask(task04);
		
		Task task05 = new Task("CSI2532 Final Report");
		task05.setContext(assgnmt);
		task05.setDueDate(new DateTime(2013, 4, 22, 23, 30));
		task05.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*15));
		taskContainer.addTask(task05);
		
		Task task07 = new Task("SEG4911 Write Presentation Slides");
		task07.setContext(assgnmt);
		task07.setDueDate(new DateTime(2013, 4, 30, 23, 30));
		task07.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*10));
		taskContainer.addTask(task07);
		
		Task task06 = new Task("Linux 101 Hacks - Book");
		task06.setContext(reading);
		task06.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*10));
		taskContainer.addTask(task06);
		
		Task task08 = new Task("Clean Own Car");
		task08.setContext(misc);
		task08.setEstimate(new Duration(DateTimeConstants.MILLIS_PER_HOUR*15));
		taskContainer.addTask(task08);
		
		return new BigContainer(taskContainer, contextContainer, currentView);
	}

	public void fill(ContextContainer context, TaskContainer task, View view){
//		context.addContext(new AllContexts());
//		context.addContext(new NoContext());
	
		Context essentials = new Context("Essentials");
		essentials.setGoal(view, new Duration(20 * DateTimeConstants.MILLIS_PER_HOUR));
		Context faith = new Context("Faith");
		faith.setGoal(view, Duration.standardHours(5));
		Context university = new Context("University");
		university.setGoal(view, Duration.standardHours(19));
		Context transportation = new Context("Transportation");
		transportation.setGoal(view, Duration.standardHours(13));
		
		context.addContext(essentials);
		context.addContext(faith);
		context.addContext(university);
		context.addContext(transportation);

		
		for (int i=0; i<10; i++){
		Task sleep = new Task("Sleep");
		sleep.setContext(essentials);
		sleep.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 14+i, 23, 00),
		new Duration(7*DateTimeConstants.MILLIS_PER_HOUR)));
		
		task.addTask(sleep);

		Task faithTask = new Task("Faith");
		faithTask.setContext(faith);
		faithTask.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 14+i, 6, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		
		task.addTask(faithTask);
		
		Task catchingUp = new Task("Catching Up");
		catchingUp.setContext(essentials);
		catchingUp.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 14+i, 6, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		
		task.addTask(catchingUp);
		}
		
		Task drive1 = new Task("Drive");
		drive1.setContext(transportation);
		drive1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 15, 9, 00),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive1);
//	
		for (int i = 0; i< 1; i++){
//			Task universityCourse1 = new Task("Some Thing");
//			universityCourse1.setCategory(university);
//			universityCourse1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 9, 0, 00).plusMinutes(30*i),
//			new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
//			universityCourse1.setMutability(Mutability.MUTABLE);
//			task.addTask(universityCourse1);
			
		Task universityCourse1 = new Task("CSI2101 LEC");
		universityCourse1.setContext(university);
		universityCourse1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 15, 10, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
//		universityCourse1.setMutability(Mutability.IMMUTABLE);
		task.addTask(universityCourse1);

		Task universityCourse2 = new Task("CSI2532 LAB");
		universityCourse2.setContext(university);
		universityCourse2.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 15, 17, 00),
		new Duration(150*DateTimeConstants.MILLIS_PER_MINUTE)));
//		universityCourse2.setMutability(Mutability.IMMUTABLE);
		task.addTask(universityCourse2);
		}
//		
		Task drive2 = new Task("Drive");
		drive2.setContext(transportation);
		drive2.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 15, 19, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive2);
		
		Task drive3 = new Task("Drive");
		drive3.setContext(transportation);
		drive3.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive3);
		
		Task universityCourse3 = new Task("CSI2532 LEC");
		universityCourse3.setContext(university);
		universityCourse3.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 11, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse3);
		
		Task universityCourse4 = new Task("PHI2794 LEC");
		universityCourse4.setContext(university);
		universityCourse4.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 13, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse4);
		
		Task universityCourse5 = new Task("CSI2532 LEC");
		universityCourse5.setContext(university);
		universityCourse5.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 14, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse5);
		
		Task universityCourse6 = new Task("CSI2101 TUT");
		universityCourse6.setContext(university);
		universityCourse6.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 17, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse6);
		
		Task drive4 = new Task("Drive");
		drive4.setContext(transportation);
		drive4.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 16, 19, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive4);
		
		Task drive5 = new Task("Drive");
		drive5.setContext(transportation);
		drive5.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 17, 7, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive5);
		
		Task universityCourse7 = new Task("CSI2101 LEC");
		universityCourse7.setContext(university);
		universityCourse7.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 17, 8, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse7);
		
		Task drive6 = new Task("Drive");
		drive6.setContext(transportation);
		drive6.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 17, 10, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive6);
		
		Task drive7 = new Task("Drive");
		drive7.setContext(transportation);
		drive7.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 18, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive7);
		
		Task universityCourse8 = new Task("CSI2532 LEC");
		universityCourse8.setContext(university);
		universityCourse8.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 18, 11, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse8);
		
		Task universityCourse9 = new Task("CSI2520 TUT");
		universityCourse9.setContext(university);
		universityCourse9.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 18, 14, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse9);
		
		Task universityCourse10 = new Task("CSI2520 LAB");
		universityCourse10.setContext(university);
		universityCourse10.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 18, 16, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse10);
		
		Task drive8 = new Task("Drive");
		drive8.setContext(transportation);
		drive8.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 18, 17, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive8);
		
		Task drive9 = new Task("Drive");
		drive9.setContext(transportation);
		drive9.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 19, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive9);
	
		Task fridayPrayer = new Task("Friday Prayer");
		fridayPrayer.setContext(faith);
		fridayPrayer.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 19, 12, 00),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(fridayPrayer);
		
		Task universityCourse11 = new Task("PHI2794 LEC");
		universityCourse11.setContext(university);
		universityCourse11.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 19, 13, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse11);
		
		Task universityCourse12 = new Task("CSI2520 LEC");
		universityCourse12.setContext(university);
		universityCourse12.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 19, 16, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse12);
		
		Task drive10 = new Task("Drive");
		drive10.setContext(transportation);
		drive10.assignToTimeBlock(new TimeBlock(new DateTime(2013, 4, 19, 17, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive10);
		
		for (Task task3: task.getAllTasks()){
			if (task3.getContext() == essentials || 
					task3.getContext() == faith || 
					task3.getContext() == university || 
					task3.getContext() == transportation){
				task3.setEvent(true);	
			}
			
		}
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
    public MySplitPlaneModel(boolean compactMode) {
        Split row = new Split();
        Split col = new Split();
        col.setRowLayout(false);
        Split coltwo = new Split();
        coltwo.setRowLayout(false);
        if (compactMode) {
            setChildren(new Leaf(BOTTOM), new Divider(), coltwo);
        } else {
            setChildren(col, new Divider(), coltwo);
            col.setChildren(new Leaf(TOP), new Divider(), new Leaf(BOTTOM));
        }
        coltwo.setChildren(new Leaf(RIGHT), new Divider(), new Leaf(LEFT));
    }
    
}