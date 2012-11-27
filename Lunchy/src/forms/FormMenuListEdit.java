package forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

import igor.lunchy.LunchyMain;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.Category;
import entities.MenuItem;

public class FormMenuListEdit {
	
	private Shell 	shell;
	private Table 	table;
	private int 	lastSortColumn	= -1;   // For sort method
	private Combo 	categoryCombo;   		// Category of Menu	
	private Color 	clRed 			= Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	
	//private Font 	fontBold 		= new Font(Display.getCurrent(), "Arial", 14, SWT.BOLD);
	
	private static ResourceBundle resLunchy = LunchyMain.resLunchy;
	
	String[] columnLabels = { 	resLunchy.getString("ID"),
								resLunchy.getString("Menu_item_name"),
								resLunchy.getString("Menu_cat"),
								resLunchy.getString("Menu_descr"),
								resLunchy.getString("Price"),
								resLunchy.getString("Menu_aval")
								};
	
	//ArrayList<MenuItem> currentMenuList = new ArrayList<>(LunchyMain.menuList);
	ArrayList<MenuItem> currentMenuList = LunchyMain.menuItemDAO.getAllMenuItem();
	
	public FormMenuListEdit(Shell parent) {
		/// Creating form
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText(resLunchy.getString("Menu_editor"));
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	private void newMenuEntry() {
		DialogMenuRecordEdit dialog = new DialogMenuRecordEdit(shell);
		int currentMenuListSize = LunchyMain.menuItemDAO.getSize();
		
		MenuItem.initId(currentMenuListSize);
		int currentCategory = categoryCombo.getSelectionIndex();
		
		if (currentCategory == 0)
			currentCategory = 1;  // Specify Category
		
		MenuItem newMenuItem =
				new MenuItem(currentMenuListSize,resLunchy.getString("Input_name"),currentCategory,"",0.0, false);
		dialog.setEditableMenuItem(newMenuItem);
		dialog.setTitle(resLunchy.getString("New_menu_item"));
		dialog.open();
		
		if (dialog.getState() == DialogMenuRecordEdit.ITEM_CHANGED) {
			//LunchyMain.menuList.add(newMenuItem);
			LunchyMain.menuItemDAO.addMenuItem(newMenuItem);
			refreshTable(categoryCombo.getSelectionIndex());
			//isModified = true;
		}
	}
	
	private void editMenuEntry(TableItem item) {
		DialogMenuRecordEdit dialog = new DialogMenuRecordEdit(shell);
		//dialog.setEditableMenuItem(LunchyMain.menuItemDAO.getAllMenuItem().get(Integer.parseInt(item.getText(0))));
		MenuItem menuItem = new MenuItem(LunchyMain.menuItemDAO.getMenuItemByID(Integer.parseInt(item.getText(0))));
		dialog.setEditableMenuItem(menuItem);
		dialog.setTitle(resLunchy.getString("Edit_menu_item"));
		dialog.open();
		
		if (dialog.getState() == DialogMenuRecordEdit.ITEM_CHANGED) {
			LunchyMain.menuItemDAO.updateMenuItem(menuItem);
			refreshTable(categoryCombo.getSelectionIndex());
			//isModified = true;
		}
	}
	
	public void open() {
		//createTextWidgets();
		//createControlButtons();
		//shell.pack();
		createWidgets();
		
		//String[][] Data = ParseTextFile.getStringTable("Data/menu.txt", "_", 6);
		//MenuEnrty[] menu = MenuEnrty.getMenuEntries("menu.txt");
		
		//Color clGreen = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		for (int i = 0; i < currentMenuList.size(); i++) {
			String[] temp = new String[6];
			TableItem item = new TableItem(table, SWT.NONE);
			temp = currentMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryDAO.getAllCategory().get(currentMenuList.get(i).getCategory()).getName();
			//temp[2] = "777";
			temp[5] = currentMenuList.get(i).getAvailability() ? "Доступен" : "Не доступен";
			item.setText(temp);
			//item.setForeground(clGreen);
		}			
		
		shell.pack();
		shell.open();
		refreshTable(0);
		//sort(0);
		//shell.setSize(600, 600);
		//shell.setSize(900, shell.getSize().y);
		
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		
		//LunchyMain.setStatusLabel("Modified");
	}
	
	public void createWidgets() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		shell.setLayout(layout);
		
		Label statusCaption = new Label(shell, SWT.NONE);
		GridData gridData = new GridData();
		gridData.minimumWidth = 40;
		
		//Font fontActive = new Font(Display.getCurrent(), "Tahoma", 14, SWT.BOLD);
		//TextStyle textstyle = new TextStyle(font1, clGreen, null);
		//statusCaption.setForeground(clGreen);
		
		statusCaption.setText(resLunchy.getString("Current_menu_status"));
		statusCaption.setLayoutData(gridData);
		
		Label statusText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		statusText.setText(resLunchy.getString("Current_menu_status"));
		statusText.setLayoutData(gridData);
		
		Label validCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validCaption.setText(resLunchy.getString("Menu_valid_date"));
		validCaption.setLayoutData(gridData);
		
		DateTime validDate = new DateTime(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validDate.setLayoutData(gridData);
		//validDate.setDate(2011, 9, 24);
		
		final Button checkBoxUpdate = new Button(shell, SWT.CHECK | SWT.LEFT_TO_RIGHT);
		gridData = new GridData();
		gridData.horizontalIndent = 30;
		gridData.horizontalSpan = 2;
		checkBoxUpdate.setText(resLunchy.getString("Menu_updated"));
		checkBoxUpdate.setLayoutData(gridData);
		checkBoxUpdate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println(checkBoxUpdate.getSelection());
			}
		});
		
		Button btNewEntry = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		btNewEntry.setText(resLunchy.getString("New_menu_entry"));
		btNewEntry.setLayoutData(gridData);
		btNewEntry.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				newMenuEntry();
			}
		});
		
		Button btEditEntry = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		btEditEntry.setText(resLunchy.getString("Edit_menu_entry"));
		btEditEntry.setLayoutData(gridData);
		btEditEntry.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = table.getSelection();
				if (items.length == 0) return;
				editMenuEntry(items[0]);
			}
		});
		
		Label categoryCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalIndent = 30;
		categoryCaption.setText(resLunchy.getString("Menu_category"));
		categoryCaption.setLayoutData(gridData);
		
		// ?????? Final ? Why ?
		categoryCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		String[] comboData = new String[LunchyMain.categoryDAO.getAllCategory().size()];
		for (Category cat: LunchyMain.categoryDAO.getAllCategory()) {
			comboData[cat.getId()] = cat.getName();
		}
		
		categoryCombo.setItems(comboData);
		categoryCombo.select(0);
		gridData = new GridData();
		
		//categoryCaption.setText("Menu category: ");
		//gridData.minimumWidth = 10;
		categoryCombo.setLayoutData(gridData);
		categoryCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Combo");
				refreshTable(categoryCombo.getSelectionIndex());
			}
		});
		
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		
		table = new Table(shell, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(gridData);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		//table.set
		//table.setMenu(createPopUpMenu());	
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = table.getSelection();
				if (items.length > 0)
					editMenuEntry(items[0]);
			}
		});
		
		for(int i = 0; i < columnLabels.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnLabels[i]);
			column.setWidth(150);
			final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
		
		TableColumn[] colummm = table.getColumns();
		colummm[0].setWidth(70);
		colummm[1].setWidth(140);
		colummm[2].setWidth(120);
		colummm[3].setWidth(170);
		colummm[4].setWidth(70);
		colummm[5].setWidth(110);
	}
	
	void refreshTable(int indexCombo) {
		//table.clearAll();
		int select = table.getSelectionIndex();
		table.clearAll();
		table.setItemCount(0);
		currentMenuList = LunchyMain.menuItemDAO.getMenuItemByCategory(indexCombo);
		if (indexCombo == 0)
			currentMenuList = new ArrayList<>(LunchyMain.menuItemDAO.getAllMenuItem());
		for (int i = 0; i < currentMenuList.size(); i++) {
			String[] temp = new String[6];
			TableItem item = new TableItem(table, SWT.NONE);
			temp = currentMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryDAO.getAllCategory().get(currentMenuList.get(i).getCategory()).getName();
			//temp[2] = LunchyMain.categoryList.get(currentMenuList.get(i).getCategory()).getName();
			//temp[2] = "888";
			temp[5] = currentMenuList.get(i).getAvailability() ? "Доступен" : "Не доступен";
			//item.setForeground(currentMenuList.get(i).getAvail() ? clGreen : clDarkGrey);
			if (!currentMenuList.get(i).getAvailability())
				item.setBackground(clRed);
			item.setText(temp);
		}
		table.setSelection(select);
		//System.out.println(indexCombo);
	}
	
	private void sort(int column) {
		if(table.getItemCount() <= 1) return;

		TableItem[] items = table.getItems();
		String[][] data = new String[items.length][table.getColumnCount()];
		for(int i = 0; i < items.length; i++) {
			for(int j = 0; j < table.getColumnCount(); j++) {
				data[i][j] = items[i].getText(j);
			}
		}
		
		Arrays.sort(data, new RowComparator(column));
		
		if (lastSortColumn != column) {
			table.setSortColumn(table.getColumn(column));
			table.setSortDirection(SWT.DOWN);
			for (int i = 0; i < data.length; i++) {
				items[i].setText(data[i]);
			}
			lastSortColumn = column;
		} else {
			// reverse order if the current column is selected again
			table.setSortDirection(SWT.UP);
			int j = data.length -1;
			for (int i = 0; i < data.length; i++) {
				items[i].setText(data[j--]);
			}
			lastSortColumn = -1;
		}
		
	}
	
	private class RowComparator implements Comparator<String[]> {
		private int column;
		
		/**
		 * Constructs a RowComparator given the column index
		 * @param col The index (starting at zero) of the column
		 */
		public RowComparator(int col) {
			column = col;
		}
		
		/**
		 * Compares two rows (type String[]) using the specified
		 * column entry.
		 * @param obj1 First row to compare
		 * @param obj2 Second row to compare
		 * @return negative if obj1 less than obj2, positive if
		 * 			obj1 greater than obj2, and zero if equal.
		 */
		public int compare(String[] obj1, String[] obj2) {
			String[] row1 = obj1;
			String[] row2 = obj2;
			
			// Sort by ID
			if (column == 0)
				return (Integer.parseInt(row1[0]))-(Integer.parseInt(row2[0]));
			// Sort by price
			if (column == 4)
				return (int)Math.round((Double.parseDouble(row1[0]))-(Double.parseDouble(row2[0])));
			// Sort by string fields	
			return row1[column].compareTo(row2[column]);
		}
	}

}
