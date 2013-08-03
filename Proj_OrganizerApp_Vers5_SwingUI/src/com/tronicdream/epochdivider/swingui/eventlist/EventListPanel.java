package com.tronicdream.epochdivider.swingui.eventlist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import ca.odell.glazedlists.swing.AutoCompleteSupport.AutoCompleteCellEditor;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.event.Event;
import com.tronicdream.epochdivider.imt.icons.IconsLocation;
import com.tronicdream.epochdivider.swingui.helpers.TableHelper;
import com.tronicdream.epochdivider.swingui.helpers.ToolbarHelper;
import com.tronicdream.epochdivider.swingui.main.CPanel;
import com.tronicdream.epochdivider.swingui.notification.ContextsChangedNotification;
import com.tronicdream.epochdivider.swingui.notification.EventListPanelPostInitCompleteNotification;
import com.tronicdream.epochdivider.swingui.notification.EventsChangedNotification;
import com.tronicdream.epochdivider.swingui.notification.SelectedContextChangedNotification;

public class EventListPanel extends CPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	
	public EventListPanel(DataContainer dataContainer, EventBus eventBus) {
		super(dataContainer, eventBus, EventListPanelConstants.COMPONENT_NAME);
	}
	
	@Override
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void selectedContextChanged(SelectedContextChangedNotification notification){
				matcherChangedNotify();
			}
			@Subscribe public void eventListChanged(EventsChangedNotification notification) throws Exception {
				refreshEventListTable();
			}
			@Subscribe public void contextListChanged(ContextsChangedNotification notification){
				refreshContextAutoComplete();
			}
		};
	}
	
	// - Components -
	private JScrollPane eventListPane;
	private JTable eventListTable;

	// - Listeners -
	private EventListMatcherEditor eventListMatcherEditor;
	private EventList<Event> eventEventList;
	private AdvancedTableModel<Event> eventListTableModel;
	private EventList<Context> contextEventList;
	
	@Override
	protected void windowInit() {
		preparePanel();
		createEventListTable();
		createToolbar();
	}

	@Override
	protected void postWindowInit() {
		prepareEventListTableModel();
		prepareEventListTableRendersAndEditors();
		adjustEventListTableColumnWidths();
		setupEventDragAndDrop();
		createRightClickMenu();
		
		//Needed to let the context list know when to select the default context.
		eventBus.post(new EventListPanelPostInitCompleteNotification());
	}

	private void preparePanel() {
		setPreferredSize(new Dimension(
				EventListPanelConstants.DEFAULT_EVENT_PANEL_WIDTH, 
				EventListPanelConstants.DEFAULT_EVENT_PANEL_HEIGHT
		));
		setBorder(UIManager.getBorder("ScrollPane.border"));
		setLayout(new BorderLayout());
	}
	
	private void createEventListTable() {
		eventListPane = new JScrollPane();
		eventListPane.setBorder(BorderFactory.createEmptyBorder());
		add(eventListPane, BorderLayout.CENTER);
		
		eventListTable = new JTable();
		eventListTable.setFillsViewportHeight(true);
		eventListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		eventListPane.setViewportView(eventListTable);
	}

	private void prepareEventListTableModel() {
		//Fill Event list with Events
		eventEventList = new BasicEventList<>();
		eventEventList.addAll(dataContainer.getEvents());
		
		//Prepare Matcher for filtering (by selected context, etc...)
		eventListMatcherEditor = new EventListMatcherEditor(dataContainer);
		FilterList<Event> filterEventList = new FilterList<>(eventEventList, eventListMatcherEditor);

		//Prepare sorting
		SortedList<Event> sortedEventList = new SortedList<>(filterEventList, null);

		//Create Model and set it to table
		TableFormat<Event> eventTableFormat = new EventListTableFormat();
		eventListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(sortedEventList, eventTableFormat);
		eventListTable.setModel(eventListTableModel);
		
		//Install sorting mechanism for table
		TableComparatorChooser.install(eventListTable, sortedEventList, TableComparatorChooser.SINGLE_COLUMN);
	}

	private void prepareEventListTableRendersAndEditors() {
		//Setup auto-completed support for Context editor
		contextEventList = new BasicEventList<>();
		contextEventList.addAll(dataContainer.rdSelectableEventContexts());
		
		AutoCompleteCellEditor<Context> contextTableCellEditor = AutoCompleteSupport.createTableCellEditor(contextEventList);
		
		//Setup context cell editor
		TableColumn contextColumn = eventListTable.getColumnModel().getColumn(EventListPanelConstants.COLUMN_EVENT_CONTEXT);
		contextColumn.setCellEditor(contextTableCellEditor);
	}
	
	private void adjustEventListTableColumnWidths() {
		eventListTable.getColumnModel().getColumn(EventListPanelConstants.COLUMN_EVENT_TITLE).setPreferredWidth(EventListPanelConstants.COLUMN_EVENT_TITLE_WIDTH);
		eventListTable.getColumnModel().getColumn(EventListPanelConstants.COLUMN_EVENT_CONTEXT).setPreferredWidth(EventListPanelConstants.COLUMN_EVENT_CONTEXT_WIDTH);
	}

	private void setupEventDragAndDrop() {
		eventListTable.setDragEnabled(true);
		eventListTable.setTransferHandler(new TransferHandler("Event"){
			private static final long serialVersionUID = -750648214860216598L;
			
			@Override
			public int getSourceActions(JComponent c) {
				return COPY_OR_MOVE;
			}
			@Override
			protected Transferable createTransferable(JComponent c) {
				return new EventTransferable(getSelectedEventInTable());
			}
		});
	}

	private void createRightClickMenu() {
		final JPopupMenu popupMenu = new JPopupMenu();
		
		//Setup popup menu for the Event List Table. 
		TableHelper.setupPopupMenu(eventListTable, popupMenu);
		
		//Delete event menu item
		JMenuItem removeEventMenuItem = new JMenuItem(EventListPanelConstants.REMOVE_EVENT_LABEL);
		removeEventMenuItem.setIcon(new ImageIcon(EventListPanel.class.getResource(IconsLocation.DELETE)));
		popupMenu.add(removeEventMenuItem);
		
		removeEventMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedEvent();
			}
		});
	}
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(eventListTable.getBackground());
		add(toolbar, BorderLayout.SOUTH);

		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		
		final JToggleButton showCompletedEvents = ToolbarHelper.createToolbarToggleButton(toolbar, EventListPanelConstants.SHOW_COMPLETED_LABEL, EventListPanel.class.getResource(IconsLocation.COMPLETED));
		showCompletedEvents.setBackground(eventListTable.getBackground());
		
		ToolbarHelper.createToolbarSeperator(toolbar);
		
		final JButton addEventButton = ToolbarHelper.createToolbarButton(toolbar, EventListPanelConstants.ADD_EVENT_LABEL, EventListPanel.class.getResource(IconsLocation.ADD));
		addEventButton.setBackground(eventListTable.getBackground());
		
		//ToolBar button actions
		showCompletedEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataContainer.setShowCompleted(showCompletedEvents.isSelected());
				matcherChangedNotify();
			}
		});
		
		addEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewEventToEventListTable();
			}
		});
	}

	
	// -- non-sequential methods --
	
	private void refreshEventListTable() {
		//TODO: Investigate proper method to refresh GlazedLists tables.
		eventEventList.clear();
		eventEventList.addAll(dataContainer.getEvents());
		
		eventListTable.repaint(); //TODO: temporary call to fix redraw bug
	}
	
	private void refreshContextAutoComplete(){
		//TODO: Investigate proper method to refresh GlazedLists lists.
		contextEventList.clear();
		contextEventList.addAll(dataContainer.rdSelectableEventContexts());
	}
	
	private void addNewEventToEventListTable() {
		//If we are already editing a event, then just set focus to the editor.
		if (eventListTable.isEditing()){
			eventListTable.getEditorComponent().requestFocus();
			return;
		}
		
		//Create a new event.
		dataContainer.emEventNew();
		
		//Refresh event list table and notify others.
		eventBus.post(new EventsChangedNotification());

		//Init. editing the title of the new events and focus the editor.
		eventListTable.editCellAt(eventListTable.getRowCount()-1, EventListPanelConstants.COLUMN_EVENT_TITLE);
		
		Component editorComponent = eventListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}

	private Event getSelectedEventInTable() {
		//TODO: There might be a better way to get the selected event.
		return eventListTableModel.getElementAt(eventListTable.getSelectedRow());
	}
	
	private void matcherChangedNotify() {
		eventListMatcherEditor.matcherChangedNotify();
		eventListTable.repaint(); //TODO: Temporary call to repaint() to fix redraw bug.
	}
	
	private void deleteSelectedEvent() {
		Event selectedEventInTable = getSelectedEventInTable();
		if (selectedEventInTable != null){
			dataContainer.emEventRemove(selectedEventInTable);
			eventBus.post(new EventsChangedNotification());
		}
	}
}
