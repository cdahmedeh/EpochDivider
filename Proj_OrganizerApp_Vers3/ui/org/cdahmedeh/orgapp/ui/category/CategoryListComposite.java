package org.cdahmedeh.orgapp.ui.category;

import java.util.HashMap;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.ContextContainer;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.misc.BigContainer;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notify.CategoryChangedNotification;
import org.cdahmedeh.orgapp.ui.notify.ChangeTaskToCategoryRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.LocalTime;

import swing2swt.layout.BorderLayout;

import com.google.common.eventbus.EventBus;

public class CategoryListComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	class EventRecorder{	
	}
	
	private ContextContainer categoryContainer = null;
	private TaskContainer taskContainer = null;
	private View currentView = null;
	private Tree treeCategorysList;
	
	private HashMap<TreeItem, Context> mapTreeItemCategory = new HashMap<>();
	
	public CategoryListComposite(Composite parent, int style, BigContainer bigContainer) {
		super(parent, style);
		
		this.categoryContainer = bigContainer.getContextContainer();
		this.currentView = bigContainer.getCurrentView();
		this.taskContainer = bigContainer.getTaskContainer();
		
		this.setLayout(new BorderLayout());
		
		makeCategoryTree();
		makeCategoryTreeClickable();
		
		treeCategorysList.setLayoutData(BorderLayout.CENTER);
		
		fillCategoryTree();
		
		Composite bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
		
		setupDrop();
	}

	private void makeCategoryTree() {
		treeCategorysList = new Tree(this, SWT.NONE);
		treeCategorysList.setHeaderVisible(true);
		
		TreeColumn clmName = ComponentFactory.generateTreeColumn(treeCategorysList, "Context", 100);
		TreeColumn clmProgress = ComponentFactory.generateTreeColumn(treeCategorysList, "Progress", 100);
	}
	
	private void makeCategoryTreeClickable(){
		treeCategorysList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventBus.post(new CategoryChangedNotification(mapTreeItemCategory.get(treeCategorysList.getSelection()[0])));
			}
		});
	}
	
	private void fillCategoryTree() {
		for (Context category: categoryContainer.getAllContexts()){
			TreeItem itmCategory = new TreeItem(treeCategorysList, SWT.NONE);
			itmCategory.setText(new String[]{
					category.getName(),
					category.getDurationPassedSince(currentView.getStartDate().toDateTime(LocalTime.MIDNIGHT), currentView.getEndDate().toDateTime(LocalTime.MIDNIGHT), taskContainer).getStandardHours() + "|" +
					category.getGoal(currentView).getStandardHours()
					});
			mapTreeItemCategory.put(itmCategory, category);
		}
	}
	
	private Composite makeBottomBar() {
		Composite bottomBarComposite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		ComponentModifier.removeSpacingAndMargins(gridLayout);
		bottomBarComposite.setLayout(gridLayout);
		
		Text text = new Text(bottomBarComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ToolBar bottomBar = new ToolBar(bottomBarComposite, SWT.FLAT | SWT.RIGHT);
		
		ToolItem buttonAddCategory = new ToolItem(bottomBar, SWT.NONE);
		buttonAddCategory.setText("Add");
		buttonAddCategory.setImage(SWTResourceManager.getImage(CategoryListComposite.class, Icons.ADD));
		return bottomBarComposite;
	}
	
	public void setupDrop(){
		DropTarget target = new DropTarget(treeCategorysList, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		target.addDropListener(new DropTargetAdapter(){
			@Override
			public void drop(DropTargetEvent event) {
				int id = Integer.valueOf((String) event.data);
				eventBus.post(new ChangeTaskToCategoryRequest(id, mapTreeItemCategory.get(event.item)));
			}
		});
	}
}