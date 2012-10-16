package igor.lunchy;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class MenuEditForm {
	
	Shell shell;
	private Table table;
	String[] columnNames = {"ID", "Name", "Category", "Description", "Price", "Availability"};
	
	public MenuEditForm(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText("Menu Editor");
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	private void newMenuEntry() {
		EntryMenuDialog dialog = new EntryMenuDialog(shell);
		String[] data = dialog.open();
		if (data != null) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(data);
			//isModified = true;
		}
	}
	
	public void open() {
		//createTextWidgets();
		//createControlButtons();
		//shell.pack();
		createWidgets();
		
		String[][] Data = ParseTextFile.getStringTable("menu.txt", "_", 6);
		//MenuEnrty[] menu = MenuEnrty.getMenuEntries("menu.txt");
		for (int i = 0; i < Data.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(Data[i]);
		}
		
		shell.open();
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void createWidgets() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		shell.setLayout(layout);
		
		Label statusCaption = new Label(shell, SWT.NONE);
		GridData gridData = new GridData();
		gridData.minimumWidth = 40;
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
		
		Button checkBoxUpdate = new Button(shell, SWT.CHECK | SWT.RIGHT_TO_LEFT);
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
		
		Label categoryCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalIndent = 30;
		categoryCaption.setText("Menu category: ");
		categoryCaption.setLayoutData(gridData);
		
		Combo categoryCombo = new Combo(shell, SWT.NONE);
		gridData = new GridData();
		//categoryCaption.setText("Menu category: ");
		//gridData.minimumWidth = 10;
		categoryCombo.setLayoutData(gridData);
		
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
		//table.setMenu(createPopUpMenu());	
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = table.getSelection();
				if (items.length > 0) {}//editEntry(items[0]);
			}
		});
		for(int i = 0; i < columnNames.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnNames[i]);
			column.setWidth(150);
			//final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {		
				public void widgetSelected(SelectionEvent e) {
					//sort(columnIndex);
					System.out.println("ddd");
				}
			});
		}
	}

}
