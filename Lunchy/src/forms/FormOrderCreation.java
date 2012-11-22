package forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
import entities.GeneralOrder;
import entities.MenuItem;
import entities.MenuItemPersonalOrder;
import entities.PersonalOrder;
import entities.Worker;

public class FormOrderCreation {
	
	private Shell shell;
	private Table tableMenu;
	private Table tableOrder;
	private Table tableOrder2; ///    temp, for testing
	private Spinner quantitySpinner;
	private Button addButton;
	private Button removeButton;
	private Text auxOrderText;
	private Combo menuItemCombo;
	
	private int currentWorkerID = 0;
	private int currentQuantity = 1;
	private int currentMenuItem = -1;
	private int currentGeneralOrderID = 0;
	
	private static ResourceBundle resLunchy = LunchyMain.resLunchy;
	
	private String[] columnLabels = { 	resLunchy.getString("ID"),
								resLunchy.getString("Menu_item_name"),
								resLunchy.getString("Menu_cat"),
								resLunchy.getString("Menu_descr"),
								resLunchy.getString("Price"),
								resLunchy.getString("Menu_aval")
								};
	
	private String[] columnMenuNames = {columnLabels[0], columnLabels[1], columnLabels[2], columnLabels[3], columnLabels[4]};
	private String[] columnOrderNames = {columnLabels[0], columnLabels[1], columnLabels[2], columnLabels[4], resLunchy.getString("Quantity")};
	private String[] columnOrderByMenuItem = new String[] {"Ordered By", "Quantity"};
	
	private ArrayList<MenuItem> currentMenuList = LunchyMain.menuItemDAO.getMenuItemByAvailability(true);
	
	public FormOrderCreation(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText(resLunchy.getString("Order_creation"));
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	
	public void open() {
		
		//LunchyMain.generalOrderList.clear();
		Date date = new Date();
		//LunchyMain.generalOrderDAO.addGeneralOrder(new GeneralOrder(0, new Date()));
		GeneralOrder generalOrder = LunchyMain.generalOrderDAO.getGeneralOrderByDate(date);
		if (generalOrder == null) {
			System.out.println("No match");
			int id = LunchyMain.generalOrderDAO.getAllGeneralOrder().size();
			LunchyMain.generalOrderDAO.addGeneralOrder(new GeneralOrder(id, date));
			currentGeneralOrderID = id;
		}
		else {
			System.out.println(generalOrder.getId() + " " + generalOrder.getOrderDate());
			currentGeneralOrderID = generalOrder.getId();
		}
		
		
		//LunchyMain.generalOrderList.add(new );
		createWidgets();
		refreshPersonalOrderTable(currentWorkerID);
		refreshTabOrdedByTable(currentWorkerID);
		
		
		for (int i = 0; i < currentMenuList.size(); i++) {
			String[] temp = new String[6];
			String[] res = new String[5];
			TableItem item = new TableItem(tableMenu, SWT.NONE);
			temp = currentMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryDAO.getAllCategory().get(currentMenuList.get(i).getCategory()).getName();
			res[0] = temp[0];
			res[1] = temp[1];
			res[2] = temp[2];
			res[3] = temp[3];
			res[4] = temp[4];
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
		
		//////////////////////////////////////////////
		///  Creating widgets elements 
		//////////////////////////////////////////////
		GridLayout layout;
		GridData gridData;
		
		// Top level layout
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		shell.setLayout(layout);
		
		//////// Menu Group Section  /////////////////////
		// menuGroup (categoryLabel, categoryCombo, tableMenu)
		
		Group menuGroup = new Group(shell, SWT.NONE);
		menuGroup.setText(resLunchy.getString("Menu"));
		layout = new GridLayout();
		layout.numColumns = 3;
		menuGroup.setLayout(layout);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.verticalSpan = 3; 
		menuGroup.setLayoutData(gridData);
		
		// menuGroup
		Label categoryLabel = new Label(menuGroup, SWT.NONE);
		categoryLabel.setText(resLunchy.getString("Menu_category"));
		
		// menuGroup
		final Combo categoryCombo = new Combo(menuGroup, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		categoryCombo.setLayoutData(gridData);
		String[] comboData = new String[LunchyMain.categoryDAO.getAllCategory().size()];
		for (Category cat: LunchyMain.categoryDAO.getAllCategory()) {
			comboData[cat.getId()] = cat.getName();
		}
		categoryCombo.setItems(comboData);
		categoryCombo.select(0);
		categoryCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Combo");
				refreshMenuItemTable(categoryCombo.getSelectionIndex());
			}
		});
		
		// menuGroup
		tableMenu = new Table(menuGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		tableMenu.setLayoutData(gridData);
		tableMenu.setLinesVisible(true);
		tableMenu.setHeaderVisible(true);
		//table.set
		//table.setMenu(createPopUpMenu());	
		tableMenu.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = tableMenu.getSelection();
				if (items.length > 0) {}//editEntry(items[0]);
				
				makeOrder(Integer.parseInt(items[0].getText(0)), currentWorkerID, currentQuantity);
				refreshPersonalOrderTable(currentWorkerID);
				refreshTabOrdedByTable(currentMenuItem);
				
				quantitySpinner.setSelection(1);
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
		columsMenuTable[0].setWidth(50);
		columsMenuTable[1].setWidth(120);
		columsMenuTable[2].setWidth(120);
		columsMenuTable[3].setWidth(150);
		columsMenuTable[4].setWidth(80);
		
		//columsMenuTable[0].setWidth(0);
		//columsMenuTable[0].setResizable(false);
		
		////////  End Menu Group Section  ///////////
		/////////////////////////////////////////////
		
		// Buttons between menuGroup and orderGroup sections
		
		/// ADD to Order button
		addButton = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		gridData.verticalAlignment = GridData.END;
		gridData.verticalIndent = 150;
		gridData.horizontalAlignment = GridData.FILL;
		addButton.setText(resLunchy.getString("Add_to_order"));
		addButton.setLayoutData(gridData);
		addButton.addSelectionListener(new SelectionAdapter() {    
			public void widgetSelected(SelectionEvent e) {
				
				TableItem[] items = tableMenu.getSelection();
				if (items.length > 0) {
					makeOrder(Integer.parseInt(items[0].getText(0)), currentWorkerID, currentQuantity);
					refreshPersonalOrderTable(currentWorkerID);
					refreshTabOrdedByTable(currentMenuItem);
					
					quantitySpinner.setSelection(1);
				}
			}
		});
		////////////////////////////////////////
		
		// Order Group section /////////////////////		
		// orderGroup (workerLabel, workerCombo, tableOrder)
		Group orderGroup = new Group(shell, SWT.NONE);
		orderGroup.setText(resLunchy.getString("Order"));
		layout = new GridLayout();
		layout.numColumns = 1;
		orderGroup.setLayout(layout);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.verticalSpan = 3;
		orderGroup.setLayoutData(gridData);
		
		///  Tab folder in Order group  ////////////////////////
		final TabFolder tabFolder = new TabFolder(orderGroup, SWT.NONE);
		TabItem tabItemWorker = new TabItem(tabFolder, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolder.setLayoutData(gridData);
		tabItemWorker.setText(resLunchy.getString("Making_order"));
		TabItem tabItemMenuItem = new TabItem(tabFolder, SWT.NONE);
		tabItemMenuItem.setText(resLunchy.getString("View_ordered_items"));
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("TabFolder ");
			}
		});
		tabFolder.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				int r = tabFolder.getSelectionIndex();
				if (r == 1) {
					addButton.setEnabled(false);
					tableMenu.setEnabled(false);
					removeButton.setEnabled(false);
					quantitySpinner.setEnabled(false);
				} else {
					addButton.setEnabled(true);
					tableMenu.setEnabled(true);
					removeButton.setEnabled(true);
					quantitySpinner.setEnabled(true);
				}
			}
		});
		
		
		//// Composite for tabItemWorker  //////////////////////
		Composite tabItemWorkerComposite = new Composite(tabFolder, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.numColumns = 2;
		tabItemWorkerComposite.setLayout(layout);
		tabItemWorker.setControl(tabItemWorkerComposite);
		
		/// Creation widgets for tabItemWorkerComposite  ///////////////
		Label workerLabel = new Label(tabItemWorkerComposite, SWT.NONE);
		workerLabel.setText(resLunchy.getString("Worker_name"));
		
		// For tabItemWorkerComposite
		final Combo workerCombo = new Combo(tabItemWorkerComposite, SWT.READ_ONLY);
		gridData = new GridData();
		//gridData.horizontalSpan = 2;
		workerCombo.setLayoutData(gridData);
		comboData = new String[LunchyMain.workerDAO.getAllWorker().size()];
		for (Worker wr: LunchyMain.workerDAO.getAllWorker()) {
			comboData[wr.getId()] = wr.getName();
		}
		workerCombo.setItems(comboData);
		workerCombo.select(0);
		workerCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				currentWorkerID = workerCombo.getSelectionIndex();
				refreshPersonalOrderTable(currentWorkerID);
			}
		});
		
		
		// For tabItemWorkerComposite
		tableOrder = new Table(tabItemWorkerComposite, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
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
		tableOrder.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.keyCode == 127) {          /// Press "Delete"
					TableItem[] items = tableOrder.getSelection();
					int selected = tableOrder.getSelectionIndex();
					if (items.length > 0) {
						//System.out.println("Delete");
						deleteOrderItem(Integer.parseInt(items[0].getText(0)), currentWorkerID);
						refreshPersonalOrderTable(currentWorkerID);
						refreshTabOrdedByTable(currentMenuItem);
						selectTableItem(tableOrder, selected);
					}
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				//System.out.println("Key pressed");
				//System.out.println("Delete");
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
		columsOrderTable[0].setWidth(50);
		columsOrderTable[1].setWidth(120);
		columsOrderTable[2].setWidth(120);
		columsOrderTable[3].setWidth(50);
		columsOrderTable[4].setWidth(90);
		
		//// Composite for tabItemMenuItem  //////////////////////
		Composite tabItemMenuItemComposite = new Composite(tabFolder, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.numColumns = 2;
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolder.setLayoutData(gridData);
		tabItemMenuItemComposite.setLayout(layout);
		tabItemMenuItem.setControl(tabItemMenuItemComposite);
		
		/// Creation widgets for tabItemWorkerComposite  ///////////////
		Label menuItemLabel = new Label(tabItemMenuItemComposite, SWT.NONE);
		menuItemLabel.setText(resLunchy.getString("Menu_item"));
		
		// For tabItemMenuItemComposite
		menuItemCombo = new Combo(tabItemMenuItemComposite, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 150;
		menuItemCombo.setLayoutData(gridData);
		//String [] data = new String[] { "EEE", "AAA"};
		//menuItemCombo.setItems(data);
		//menuItemCombo.select(2);
		menuItemCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				currentMenuItem = menuItemCombo.getSelectionIndex();
				refreshTabOrdedByTable(currentMenuItem);
			}
		});
		
		
		
		// For tabItemMenuItemComposite
		tableOrder2 = new Table(tabItemMenuItemComposite, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		tableOrder2.setLayoutData(gridData);
		tableOrder2.setLinesVisible(true);
		tableOrder2.setHeaderVisible(true);
		//table.set
		//table.setMenu(createPopUpMenu());	
		tableOrder2.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = tableOrder2.getSelection();
				if (items.length > 0) {}//editEntry(items[0]);
				
				System.out.println("Double Click");
			}
		});
		for(int i = 0; i < columnOrderByMenuItem.length; i++) {
			TableColumn column = new TableColumn(tableOrder2, SWT.NONE);
			column.setText(columnOrderByMenuItem[i]);
			column.setWidth(150);
			//final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					//sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
		TableColumn[] columsOrderTable2 = tableOrder2.getColumns();
		columsOrderTable2[0].setWidth(150);
		columsOrderTable2[1].setWidth(90);
		////////////////////////////////////////////
		
		// Group between menuGroup and orderGroup
		Group quantityGroup = new Group(shell, SWT.NONE);
		quantityGroup.setText(resLunchy.getString("Quantity"));
		layout = new GridLayout();
		layout.numColumns = 1;
		quantityGroup.setLayout(layout);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		quantityGroup.setLayoutData(gridData);
		
		// quantityGroup
		quantitySpinner = new Spinner(quantityGroup, SWT.BORDER);
		quantitySpinner.setMinimum(1);
		quantitySpinner.setMaximum(10);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		quantitySpinner.setLayoutData(gridData);
		quantitySpinner.addListener (SWT.Modify, new Listener () {
			public void handleEvent (Event e) {
				currentQuantity = quantitySpinner.getSelection();
			}
		});
		
		// Buttons between menuGroup and orderGroup
		removeButton = new Button(shell, SWT.PUSH); // In global vars
		gridData = new GridData();
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.horizontalAlignment = GridData.FILL;
		removeButton.setText(resLunchy.getString("Remove_from_Order"));
		removeButton.setLayoutData(gridData);
		removeButton.addSelectionListener(new SelectionAdapter() {    
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = tableOrder.getSelection();
				int selected = tableOrder.getSelectionIndex();
				if (items.length > 0) {
					//System.out.println("Delete");
					deleteOrderItem(Integer.parseInt(items[0].getText(0)), currentWorkerID);
					refreshPersonalOrderTable(currentWorkerID);
					refreshTabOrdedByTable(currentMenuItem);
					selectTableItem(tableOrder, selected);					
				}
			}
		});
				
		// Aux widgets areas. Below menuGroup and orderGroup
		Text auxText = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		//auxText.setText("");
		
		/// DEBUG:
		if (LunchyMain.generalOrderDAO.getAllGeneralOrder().size() == 0)
			auxText.setText("444");
		else
			auxText.setText(LunchyMain.generalOrderDAO.getAllGeneralOrder().get(0).getOrderDate());
		auxText.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		auxOrderText = new Text(shell, SWT.READ_ONLY | SWT.BORDER);  // in global vars
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		auxOrderText.setText(resLunchy.getString("Order_amount") + " 0");
		auxOrderText.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Text auxOrderText2 = new Text(shell, SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		auxOrderText2.setText(resLunchy.getString("Name"));
		auxOrderText2.setLayoutData(gridData);
		
		// Aux widgets areas. Below menuGroup and orderGroup
		Button saveOrderButton = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 30;
		saveOrderButton.setText(resLunchy.getString("Save_Order"));
		saveOrderButton.setLayoutData(gridData);
		saveOrderButton.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent e) {
				refreshTabOrdedByTable(0);
			}
		});
		
		
	}
	
	// Menu Item Table
	private void refreshMenuItemTable(int indexCombo) {
		//table.clearAll();
		tableMenu.clearAll();
		tableMenu.setItemCount(0);
		ArrayList<MenuItem> tempMenuList;
		
		if (indexCombo == 0) {
			tempMenuList = new ArrayList<>(currentMenuList);
		} else {
			tempMenuList = MenuItem.findByCat(currentMenuList, indexCombo);
		}
		
					
		for (int i = 0; i < tempMenuList.size(); i++) {
			String[] temp = new String[6];
			String[] res = new String[5];
			TableItem item = new TableItem(tableMenu, SWT.NONE);
			temp = tempMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryDAO.getAllCategory().get(tempMenuList.get(i).getCategory()).getName();
			res[0] = temp[0];
			res[1] = temp[1];
			res[2] = temp[2];
			res[3] = temp[3];
			res[4] = temp[4];
			item.setText(res);
			//item.setForeground(clGreen);
		}
		//System.out.println(indexCombo);
	}
	
	// Aux tab for TabFolder
	private void refreshTabOrdedByTable(int menuItemID) {
		
		int curGeneralOrderID = currentGeneralOrderID;
		
		// 
		ArrayList<Integer> miList = new ArrayList<>();
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderId() == curGeneralOrderID) {
				for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
					if (mi_po.getPersonalOrderId() == po.getId())
						miList.add(mi_po.getMenuItemId());
				}
			}
		}
		
		ArrayList<Integer> uniqMI = new ArrayList<>();
		for (Integer i : miList) {
			if (!uniqMI.contains(i)) {
				uniqMI.add(i);
			}
		}
		
		String[] comboData = new String[uniqMI.size()];
		int idx = 0;
		int [] comboIndexes = new int[uniqMI.size()];
		for (Integer i: uniqMI) {
			comboData[idx] = LunchyMain.menuItemDAO.getAllMenuItem().get(i).getName();
			comboIndexes[idx] = i;
			idx++;
		}
		menuItemCombo.setItems(comboData);
		
		// Check up selection
		if (menuItemID == -1) {
			return;
		} else {
			menuItemCombo.select(menuItemID);
			//System.out.println(menuItemID);
			tableOrder2.clearAll();
			tableOrder2.setItemCount(0);
			
			for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
				if (po.getGeneralOrderId() == curGeneralOrderID) {
					for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
						if (mi_po.getPersonalOrderId() == po.getId()) {
							if (mi_po.getMenuItemId() == comboIndexes[menuItemID]) {
								String[] temp = new String[2];
								TableItem item = new TableItem(tableOrder2, SWT.NONE);
								temp[0] = LunchyMain.workerDAO.getAllWorker().get(po.getWorkerId()).getName();
								temp[1] = String.valueOf(mi_po.getItemCount());
								item.setText(temp);
							}
						}
					}
				}
			}
		}
		
			
				
	}
	
	// Personal Order Table
	private void refreshPersonalOrderTable(int workerID) {
		int curGeneralOrderID = currentGeneralOrderID;
		double amount = 0;
		
		boolean checkPersonalOrderByWorker = false;
		int curPersonalOrderID = LunchyMain.personalOrderDAO.getAllPersonalOrder().size();
		//boolean addmipoReq = true;
		//MenuItemPersonalOrder mipo = null;
		
		auxOrderText.setText(resLunchy.getString("Order_amount") + " 0");
		
		// Checking existance PersonalOrder Entry
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderId() == curGeneralOrderID)
				if (po.getWorkerId() == workerID) {
					/// Entry exists 
					curPersonalOrderID = po.getId();
					checkPersonalOrderByWorker = true;
					break;
				}
		}
		
		// Main branch
		if (!checkPersonalOrderByWorker) {
			tableOrder.clearAll();
			tableOrder.setItemCount(0);
			return;
		}
		else {
			tableOrder.clearAll();
			tableOrder.setItemCount(0);
			
			for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
				if (mi_po.getPersonalOrderId() == curPersonalOrderID) {
					
					String[] temp = new String[5];
					TableItem item = new TableItem(tableOrder, SWT.NONE);
					temp[0] = String.valueOf(mi_po.getMenuItemId());
					temp[1] = LunchyMain.menuItemDAO.getAllMenuItem().get(mi_po.getMenuItemId()).getName();
					temp[2] = LunchyMain.categoryDAO.getAllCategory().get(LunchyMain.menuItemDAO.getAllMenuItem().get(mi_po.getMenuItemId()).getCategory()).getName();
					temp[3] = String.valueOf((LunchyMain.menuItemDAO.getAllMenuItem().get(mi_po.getMenuItemId()).getPrice()) * (mi_po.getItemCount()));
					temp[4] = String.valueOf(mi_po.getItemCount());
					item.setText(temp);
					amount += Double.parseDouble(temp[3]);
					//System.out.println(amount);
				}
			}
		}
		//System.out.println(amount);
		auxOrderText.setText(resLunchy.getString("Order_amount") + " " + amount);
		//auxOrderText.setText("TTT");
	}
	
	// Make order
	private void makeOrder(int menuItemID, int workerID, int count) {
		//System.out.println(menuItemID + " " + workerID + " " + count);
		
		int curGeneralOrderID = currentGeneralOrderID;
		
		boolean checkPersonalOrderByWorker = false;
		int curPersonalOrderID = LunchyMain.personalOrderDAO.getAllPersonalOrder().size();
		boolean addmipoReq = true;
		MenuItemPersonalOrder mipo = null;
		
		// Checking existance PersonalOrder Entry
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderId() == curGeneralOrderID)
				if (po.getWorkerId() == workerID) {
					/// Entry exists 
					curPersonalOrderID = po.getId();
					checkPersonalOrderByWorker = true;
					break;
				}
		}
		
		/// Main branch 
		if (checkPersonalOrderByWorker) {
			/// Personal Order entry exists
			for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
				if (mi_po.getMenuItemId() == menuItemID)
					if (mi_po.getPersonalOrderId() == curPersonalOrderID) {
						mi_po.setItemCount(mi_po.getItemCount() + count); // Adding count to existing entry
						LunchyMain.menuItemPersonalOrderDAO.updateMenuItemPersonalOrder(mi_po);
						addmipoReq = false;
						break;
					}
						
			}
			if (addmipoReq) {
				mipo = new MenuItemPersonalOrder(menuItemID, curPersonalOrderID, count);
				LunchyMain.menuItemPersonalOrderDAO.addMenuItemPersonalOrder(mipo);
			}
			
			/// Personal Order entry not exists
		} else {
			LunchyMain.personalOrderDAO.addPersonalOrder(new PersonalOrder(curPersonalOrderID, workerID, curGeneralOrderID));
			LunchyMain.menuItemPersonalOrderDAO.addMenuItemPersonalOrder(new MenuItemPersonalOrder(menuItemID, curPersonalOrderID, count));
		}
		
	}
	
	//Composite createTabFolderPage (TabFolder tabFolder) {
	//mi_po.setItemCount(mi_po.getItemCount() + count); // Adding count to existing entry
	//mipo = new MenuItemPersonalOrder(menuItemID, curPersonalOrderID, count);
	//
	//}

	private void deleteOrderItem(int menuItemID, int workerID) {
		int curGeneralOrderID = currentGeneralOrderID;
		boolean res = false;
		
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderId() == curGeneralOrderID)
				if (po.getWorkerId() == workerID) {
					res = LunchyMain.menuItemPersonalOrderDAO.removeMenuItemPersonalOrder(menuItemID, po.getId());
				}
		}
		
		/*
		int deletingPersonalOrder = -1;
		
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderID() == curGeneralOrderID)
				if (po.getWorkerID() == workerID) {
					MenuItemPersonalOrder result =
							LunchyMain.menuItemPersonalOrderDAO.getMenuItemPersonalOrderByPersonalOrderID(po.getID());
					if (result == null) {
						deletingPersonalOrder = po.getID();
						break;
					}
				}
		}
		
		if (deletingPersonalOrder != -1) {
			LunchyMain.personalOrderDAO.removePersonalOrder(deletingPersonalOrder);
			System.out.println("Delete personal order");
		}
		*/
		//		
		System.out.println("Deleted " + menuItemID + " " + workerID + " " + res);
		
	}
	
	private void selectTableItem(Table table, int selected) {
		if (selected == 0) {
			if (table.getItemCount() != 0)
				table.select(0);
		} else {
			if (selected == table.getItemCount())
				table.select(selected - 1);
			else
				table.select(selected);
		}
	}
}

