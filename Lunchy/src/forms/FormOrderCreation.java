package forms;

import java.util.ArrayList;

import igor.lunchy.LunchyMain;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.Category;
import entities.MenuItem;
import entities.Worker;

public class FormOrderCreation {
	
	Shell shell;
	private Table tableMenu;
	private Table tableOrder;
	String[] columnMenuNames = {"Name", "Category", "Description", "Price"};
	String[] columnOrderNames = {"Name", "Category", "Price", "Quantity"};
	
	ArrayList<MenuItem> currentMenuList = MenuItem.FindByAvail(LunchyMain.menuList, true);
	
	public FormOrderCreation(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText("Order Creation");
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	
	public void open() {
		//createTextWidgets();
		//createControlButtons();
		//shell.pack();
		createWidgets();
		
		
		
		for (int i = 0; i < currentMenuList.size(); i++) {
			String[] temp = new String[6];
			String[] res = new String[4];
			TableItem item = new TableItem(tableMenu, SWT.NONE);
			temp = currentMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryList.get(currentMenuList.get(i).getCategory()).getName();
			res[0] = temp[1];
			res[1] = temp[2];
			res[2] = temp[3];
			res[3] = temp[4];
			item.setText(res);
			//item.setForeground(clGreen);
		}
		
		shell.pack();
		shell.open();
		//shell.setSize(600, 600);
		shell.setSize(shell.getSize().x, 400);
		
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		
		//LunchyMain.setStatusLabel("Modified");
	}
	
	public void createWidgets() {
		// Location:
		//  _________________________________________
		// | menuGroup | addButton      | orderGroup |
		// |           | _____________  |            |
		// |           ||quantityGroup| |            |
		// |           ||_____________| |            |
		// |___________| removeButton   |____________|
		// |_________________________________________|
		// |          Text              |  Text      |
		// |____________________________|____________| 
		// |          Text              | saveOrderBt|
		// |____________________________|____________|
		
		// Top level layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		shell.setLayout(layout);
		
		// menuGroup (categoryLabel, categoryCombo, tableMenu)
		Group menuGroup = new Group(shell, SWT.NONE);
		menuGroup.setText("Menu");
		layout = new GridLayout();
		layout.numColumns = 3;
		menuGroup.setLayout(layout);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalSpan = 3; 
		menuGroup.setLayoutData(gridData);
		
		// menuGroup
		Label categoryLabel = new Label(menuGroup, SWT.NONE);
		categoryLabel.setText("Menu category: ");
		
		// menuGroup
		final Combo categoryCombo = new Combo(menuGroup, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		categoryCombo.setLayoutData(gridData);
		String[] comboData = new String[LunchyMain.categoryList.size()];
		for (Category cat: LunchyMain.categoryList) {
			comboData[cat.getID()] = cat.getName();
		}
		categoryCombo.setItems(comboData);
		categoryCombo.select(0);
		categoryCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Combo");
				refreshTable(categoryCombo.getSelectionIndex());
			}
		});
		
		// menuGroup
		tableMenu = new Table(menuGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		tableMenu.setLayoutData(gridData);
		tableMenu.setLinesVisible(true);
		tableMenu.setHeaderVisible(true);
		//table.set
		//table.setMenu(createPopUpMenu());	
		tableMenu.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = tableMenu.getSelection();
				if (items.length > 0) {}//editEntry(items[0]);
				System.out.println("Double Click");
			}
		});
		for(int i = 0; i < columnMenuNames.length; i++) {
			TableColumn column = new TableColumn(tableMenu, SWT.NONE);
			column.setText(columnMenuNames[i]);
			column.setWidth(150);
			//final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					//sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
		
		TableColumn[] columsMenuTable = tableMenu.getColumns();
		columsMenuTable[0].setWidth(120);
		columsMenuTable[1].setWidth(120);
		columsMenuTable[2].setWidth(150);
		columsMenuTable[3].setWidth(80);
		
		// Buttons between menuGroup and orderGroup
		Button addButton = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.verticalIndent = 150;
		gridData.horizontalAlignment = GridData.FILL;
		addButton.setText("Add to Order");
		addButton.setLayoutData(gridData);
		
		// orderGroup (workerLabel, workerCombo, tableOrder)
		Group orderGroup = new Group(shell, SWT.NONE);
		orderGroup.setText("Order");
		layout = new GridLayout();
		layout.numColumns = 3;
		orderGroup.setLayout(layout);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalSpan = 3;
		orderGroup.setLayoutData(gridData);
		
		// orderGroup
		Label workerLabel = new Label(orderGroup, SWT.NONE);
		workerLabel.setText("Worker name: ");
		
		// orderGroup
		Combo workerCombo = new Combo(orderGroup, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		workerCombo.setLayoutData(gridData);
		comboData = new String[LunchyMain.workerList.size()];
		for (Worker wr: LunchyMain.workerList) {
			comboData[wr.getID()] = wr.getName();
		}
		workerCombo.setItems(comboData);
		workerCombo.select(0);
		
		// orderGroup
		tableOrder = new Table(orderGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		tableOrder.setLayoutData(gridData);
		tableOrder.setLinesVisible(true);
		tableOrder.setHeaderVisible(true);
		//table.set
		//table.setMenu(createPopUpMenu());	
		tableOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = tableOrder.getSelection();
				if (items.length > 0) {}//editEntry(items[0]);
				System.out.println("Double Click");
			}
		});
		for(int i = 0; i < columnOrderNames.length; i++) {
			TableColumn column = new TableColumn(tableOrder, SWT.NONE);
			column.setText(columnOrderNames[i]);
			column.setWidth(150);
			//final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					//sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
		TableColumn[] columsOrderTable = tableOrder.getColumns();
		columsOrderTable[0].setWidth(120);
		columsOrderTable[1].setWidth(120);
		columsOrderTable[2].setWidth(50);
		columsOrderTable[3].setWidth(60);
		
		// Group between menuGroup and orderGroup
		Group quantityGroup = new Group(shell, SWT.NONE);
		quantityGroup.setText("Quantity");
		layout = new GridLayout();
		layout.numColumns = 1;
		quantityGroup.setLayout(layout);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		quantityGroup.setLayoutData(gridData);
		
		// quantityGroup
		Spinner quantitySpinner = new Spinner(quantityGroup, SWT.BORDER);
		quantitySpinner.setMinimum(1);
		quantitySpinner.setMaximum(10);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		quantitySpinner.setLayoutData(gridData);
		
		// Buttons between menuGroup and orderGroup
		Button removeButton = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.horizontalAlignment = GridData.FILL;
		removeButton.setText("Remove from Order");
		removeButton.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Text auxText = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		auxText.setText("");
		auxText.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Text auxOrderText = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		auxOrderText.setText("Order amount: ");
		auxOrderText.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Text auxOrderText2 = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		auxOrderText2.setText("Name: ");
		auxOrderText2.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Button saveOrderButton = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 30;
		saveOrderButton.setText("Save Order");
		saveOrderButton.setLayoutData(gridData);
		
	}
	
	void refreshTable(int indexCombo) {
		//table.clearAll();
		tableMenu.clearAll();
		tableMenu.setItemCount(0);
		ArrayList<MenuItem> tempMenuList;
		
		if (indexCombo == 0) {
			tempMenuList = new ArrayList<>(currentMenuList);
		} else {
			tempMenuList = MenuItem.FindByCat(currentMenuList, indexCombo);
		}
		
					
		for (int i = 0; i < tempMenuList.size(); i++) {
			String[] temp = new String[6];
			String[] res = new String[4];
			TableItem item = new TableItem(tableMenu, SWT.NONE);
			temp = tempMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryList.get(tempMenuList.get(i).getCategory()).getName();
			res[0] = temp[1];
			res[1] = temp[2];
			res[2] = temp[3];
			res[3] = temp[4];
			item.setText(res);
			//item.setForeground(clGreen);
		}
		//System.out.println(indexCombo);
	}

}

