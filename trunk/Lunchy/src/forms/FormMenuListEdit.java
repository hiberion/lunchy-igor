package forms;

import java.util.ArrayList;
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
import entities.MenuItem;

public class FormMenuListEdit {
	
	Shell shell;
	private Table table;
	private static ResourceBundle resLunchy = ResourceBundle.getBundle("lunchy_en");
	String[] columnLabels = { 	resLunchy.getString("ID"),
								resLunchy.getString("Menu_item_name"),
								resLunchy.getString("Menu_cat"),
								resLunchy.getString("Menu_descr"),
								resLunchy.getString("Price"),
								resLunchy.getString("Menu_aval")
								};
	
	//String[] columnNames = {"ID", "Name", "Category", "Description", "Price", "Availability"};
	
	ArrayList<MenuItem> currentMenuList = new ArrayList<>(LunchyMain.menuList);
	
	public FormMenuListEdit(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText(resLunchy.getString("Menu_editor"));
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	private void newMenuEntry() {
		DialogMenuRecordEdit dialog = new DialogMenuRecordEdit(shell);
		String[] data = dialog.open();
		if (data != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(data);
			//isModified = true;
		}
	}
	
	private void editMenuEntry(TableItem item) {
		DialogMenuRecordEdit dialog = new DialogMenuRecordEdit(shell);
		String[] values = new String[table.getColumnCount()];
		for (int i = 0; i < values.length; i++) {
			values[i] = item.getText(i);
		}
		dialog.setValues(values);
		values = dialog.open();
		if (values != null) {
			item.setText(values);
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
			temp[2] = LunchyMain.categoryList.get(currentMenuList.get(i).getCategory()).getName();
			temp[5] = currentMenuList.get(i).getAvail() ? "Доступен" : "Не доступен";
			item.setText(temp);
			//item.setForeground(clGreen);
		}
			
		
		shell.pack();
		shell.open();
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
		
		//Font font1 = new Font(Display.getCurrent(), "Tahoma", 14, SWT.BOLD);
		//Color clGreen = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		//TextStyle textstyle = new TextStyle(font1, clGreen, null);
		//statusCaption.setForeground(clGreen);
		
		statusCaption.setText("Current menu status: ");
		statusCaption.setLayoutData(gridData);
		
		Label statusText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		statusText.setText("Current menu status");
		statusText.setLayoutData(gridData);
		
		Label validCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validCaption.setText("Menu valid date: ");
		validCaption.setLayoutData(gridData);
		
		DateTime validDate = new DateTime(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validDate.setLayoutData(gridData);
		
		Button checkBoxUpdate = new Button(shell, SWT.CHECK | SWT.LEFT_TO_RIGHT);
		gridData = new GridData();
		gridData.horizontalIndent = 30;
		gridData.horizontalSpan = 2;
		checkBoxUpdate.setText("Menu updated: ");
		checkBoxUpdate.setLayoutData(gridData);
		
		Button btNewEntry = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		btNewEntry.setText("New menu entry");
		btNewEntry.setLayoutData(gridData);
		btNewEntry.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				newMenuEntry();
			}
		});
		
		Button btEditEntry = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		btEditEntry.setText("Edit menu entry");
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
		categoryCaption.setText("Menu category: ");
		categoryCaption.setLayoutData(gridData);
		
		// ?????? Final ? Why ?
		final Combo categoryCombo = new Combo(shell, SWT.NONE);
		String[] comboData = new String[LunchyMain.categoryList.size()];
		for (Category cat: LunchyMain.categoryList) {
			comboData[cat.getID()] = cat.getName();
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
				if (items.length > 0) {}//editEntry(items[0]);
			}
		});
		for(int i = 0; i < columnLabels.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnLabels[i]);
			column.setWidth(150);
			//final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					//sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
		TableColumn[] colummm = table.getColumns();
		colummm[0].setWidth(30);
		colummm[1].setWidth(120);
		//Button btt = new Button(shell, SWT.PUSH);
		//btt.setText("8888");
		//colummm[1].setData(new Button(shell, SWT.PUSH));
		colummm[2].setWidth(100);
		colummm[3].setWidth(150);
		colummm[4].setWidth(50);
		colummm[5].setWidth(90);
	}
	
	void refreshTable(int indexCombo) {
		//table.clearAll();
		table.clearAll();
		table.setItemCount(0);
		currentMenuList = MenuItem.FindByCat(LunchyMain.menuList, indexCombo);
		if (indexCombo == 0)
			currentMenuList = new ArrayList<>(LunchyMain.menuList);
		for (int i = 0; i < currentMenuList.size(); i++) {
			String[] temp = new String[6];
			TableItem item = new TableItem(table, SWT.NONE);
			temp = currentMenuList.get(i).toStringArray();
			temp[2] = LunchyMain.categoryList.get(currentMenuList.get(i).getCategory()).getName();
			temp[5] = currentMenuList.get(i).getAvail() ? "Доступен" : "Не доступен";
			item.setText(temp);
			//item.setForeground(clGreen);
		}
		//System.out.println(indexCombo);
	}

}
