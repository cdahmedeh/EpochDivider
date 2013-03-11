package org.cdahmedeh.orgapp.ui.category;

import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.CategoryContainer;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

public class CategoryListComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private CategoryContainer categoryContainer = null;
	private Tree treeCategorysList;
	
	public CategoryListComposite(Composite parent, int style, CategoryContainer categoryContainer) {
		super(parent, style);
		
		this.categoryContainer = categoryContainer;
		
		this.setLayout(new BorderLayout());
		
		makeCategoryTree();
		treeCategorysList.setLayoutData(BorderLayout.CENTER);
		
		fillCategoryTree();
		
		Composite bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
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
}