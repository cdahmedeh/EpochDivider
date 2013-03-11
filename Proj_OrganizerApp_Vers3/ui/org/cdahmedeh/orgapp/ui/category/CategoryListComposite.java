package org.cdahmedeh.orgapp.ui.category;

import java.util.HashMap;

import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.CategoryContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.calendar.CalendarUIMode;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notify.ChangeTaskToCategoryRequest;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class CategoryListComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	class EventRecorder{	
	}
	
	private CategoryContainer categoryContainer = null;
	private Tree treeCategorysList;
	
	private HashMap<TreeItem, Category> mapTreeItemCategory = new HashMap<>();
	
	public CategoryListComposite(Composite parent, int style, CategoryContainer categoryContainer) {
		super(parent, style);
		
		this.categoryContainer = categoryContainer;
		
		this.setLayout(new BorderLayout());
		
		makeCategoryTree();
		treeCategorysList.setLayoutData(BorderLayout.CENTER);
		
		fillCategoryTree();
		
		Composite bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
		
		setupDrop();
	}

	private void makeCategoryTree() {
		treeCategorysList = new Tree(this, SWT.NONE);
		treeCategorysList.setHeaderVisible(true);
		
		TreeColumn clmName = ComponentFactory.generateTreeColumn(treeCategorysList, "Category", 100);
	}
	
	private void fillCategoryTree() {
		for (Category category: categoryContainer.getAllCategories()){
			TreeItem itmCategory = new TreeItem(treeCategorysList, SWT.NONE);
			itmCategory.setText(new String[]{
					category.getName()
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
				eventBus.post(new ChangeTaskToCategoryRequest(id, mapTreeItemCategory.get(treeCategorysList.getSelection()[0])));
			}
		});
	}
}