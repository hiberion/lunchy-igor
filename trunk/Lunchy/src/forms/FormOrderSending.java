package forms;

import igor.lunchy.LunchyMain;
import igor.lunchy.ParseTextFile;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class FormOrderSending {
	
	Shell shell;
	private Table table;
	String[] columnNames = {"#", "Name", "Category", "Price", "Quantity", "Common price"};
	
	public FormOrderSending(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText("Sending order");
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
		
		//String[][] Data = ParseTextFile.getStringTable("menu.txt", "_", 6);
		//MenuEnrty[] menu = MenuEnrty.getMenuEntries("menu.txt");
		Color clGreen = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		for (int i = 0; i < LunchyMain.menuList.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(LunchyMain.menuList.get(i).ToStrArr());
			item.setForeground(clGreen);
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
		layout.numColumns = 2;
		shell.setLayout(layout);
		
		Label companyNameLabel = new Label(shell, SWT.NONE);
		GridData gridData = new GridData();
		gridData.minimumWidth = 40;
		
		//Font font1 = new Font(Display.getCurrent(), "Tahoma", 14, SWT.BOLD);
		//Color clGreen = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		//TextStyle textstyle = new TextStyle(font1, clGreen, null);
		//statusCaption.setForeground(clGreen);
		
		companyNameLabel.setText("Organization Name: ");
		companyNameLabel.setLayoutData(gridData);
		
		Label companyNameText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		companyNameText.setText("Name");
		companyNameText.setLayoutData(gridData);
		
		Label companyPhoneLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		companyPhoneLabel.setText("Company Phone: ");
		companyPhoneLabel.setLayoutData(gridData);
		
		Label companyPhoneText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		companyPhoneText.setText("Company Phone: ");
		companyPhoneText.setLayoutData(gridData);
		
		Label orderDateLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		orderDateLabel.setText("Order date: ");
		orderDateLabel.setLayoutData(gridData);
		
		Label orderDateText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		orderDateText.setText("31-12-2012");
		orderDateText.setLayoutData(gridData);
		
		Label auxCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalIndent = 50;
		auxCaption.setText("Common order: ");
		auxCaption.setLayoutData(gridData);
		
		
		
		/*
		Combo categoryCombo = new Combo(shell, SWT.NONE);
		gridData = new GridData();
		//categoryCaption.setText("Menu category: ");
		//gridData.minimumWidth = 10;
		categoryCombo.setLayoutData(gridData);
		*/
		
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
		TableColumn[] colummm = table.getColumns();
		colummm[0].setWidth(30);
		colummm[1].setWidth(120);
		//Button btt = new Button(shell, SWT.PUSH);
		//btt.setText("8888");
		//colummm[1].setData(new Button(shell, SWT.PUSH));
		colummm[2].setWidth(100);
		colummm[3].setWidth(90);
		colummm[4].setWidth(90);
		colummm[5].setWidth(90);
		
		Label avgOrderSumLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		//gridData.horizontalIndent = 50;
		avgOrderSumLabel.setText("Average order sum: ");
		avgOrderSumLabel.setLayoutData(gridData);
		
		Label avgOrderSumText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		avgOrderSumText.setText("000");
		avgOrderSumText.setLayoutData(gridData);
		
		Button sendButton = new Button(shell, SWT.PUSH);
		sendButton.setText("Send order");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 170;
		gridData.horizontalAlignment = GridData.CENTER;
		sendButton.setLayoutData(gridData);
	}

}
