package forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import igor.lunchy.LunchyMain;
import igor.lunchy.PrintOrder;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.GeneralOrder;
import entities.MenuItemPersonalOrder;
import entities.PersonalOrder;

public class FormOrderSending {
	
	Shell shell;
	private Table table;
	
	private int currentGeneralOrderID = 0;
	
	//String[] columnNames = {"#", "Name", "Category", "Price", "Quantity", "Summ  price"};
	Label totalOrderSumText = null;
	Label avgOrderSumText = null;
	
	Properties options = LunchyMain.options;
	private static ResourceBundle resLunchy = LunchyMain.resLunchy;
	
	String[] columnLabels = { 	resLunchy.getString("ID"),
								resLunchy.getString("Menu_item_name"),
								resLunchy.getString("Menu_cat"),
								resLunchy.getString("Price"),
								resLunchy.getString("Quantity"),
								resLunchy.getString("Sum_price")
								};
	
	String[] columnNames = {columnLabels[0], columnLabels[1], columnLabels[2],
			columnLabels[3], columnLabels[4], columnLabels[5]};
	
	public FormOrderSending(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText(resLunchy.getString("Order_sending"));
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}

	
	public void open() {
		createWidgets();
		
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
			System.out.println(generalOrder.getID() + " " + generalOrder.getOrderDate());
			currentGeneralOrderID = generalOrder.getID();
		}
		
		getTableItems();
		
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
	
	private void getTableItems() {
		
		double totalSum = 0;
		double avgSum = 0;
		int personalOrders = 0;
		
		int curGeneralOrderID = currentGeneralOrderID;
		
		// Forming list of ordered menuItems for current general Order
		ArrayList<Integer> miList = new ArrayList<>();
		ArrayList<Integer> miListCount = new ArrayList<>();
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderID() == curGeneralOrderID) {
				//personalOrders++;
				for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
					if (mi_po.getPersonalOrderID() == po.getID()) {
						miList.add(mi_po.getMenuItemID());
						miListCount.add(mi_po.getItemCount());
					}
				}
			}
		}
		
		// Finding no empty personal ordrs
		for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
			if (po.getGeneralOrderID() == curGeneralOrderID) {
				MenuItemPersonalOrder result =
						LunchyMain.menuItemPersonalOrderDAO.getMenuItemPersonalOrderByPersonalOrderID(po.getID());
				if (result != null) {
					personalOrders++;
					continue;
				}
			}
		}
		
		// Search unique ordered menuItems
		ArrayList<Integer> uniqMI = new ArrayList<>();
		for (Integer i : miList) {
			if (!uniqMI.contains(i)) {
				uniqMI.add(i);
			}
		}
		
		/*
		for (Integer i : uniqMI)
			System.out.println("uniq: " + i);
		*/
		
		
		// Filling common order table
		for (Integer mi_id: uniqMI) {
			int quant = 0;
			String[] temp = new String[6];
			TableItem item = new TableItem(table, SWT.NONE);
			for (PersonalOrder po: LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
				if (po.getGeneralOrderID() == curGeneralOrderID) {
					for (MenuItemPersonalOrder mi_po : LunchyMain.menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder()) {
						if ((mi_po.getPersonalOrderID() == po.getID()) & (mi_po.getMenuItemID() == mi_id)) {
							quant = quant + mi_po.getItemCount();
						}						
					}
				}
			}
			temp[0] = String.valueOf(mi_id);
			temp[1] = LunchyMain.menuItemDAO.getAllMenuItem().get(mi_id).getName();
			temp[2] = LunchyMain.categoryDAO.getAllCategory().get(LunchyMain.menuItemDAO.getAllMenuItem().get(mi_id).getCategory()).getName();
			temp[3] = String.valueOf(LunchyMain.menuItemDAO.getAllMenuItem().get(mi_id).getPrice());
			temp[4] = String.valueOf(quant);
			totalSum = totalSum + quant * (LunchyMain.menuItemDAO.getAllMenuItem().get(mi_id).getPrice());
			temp[5] = String.valueOf(quant * (LunchyMain.menuItemDAO.getAllMenuItem().get(mi_id).getPrice()));
			item.setText(temp);
			
			System.out.println(mi_id + ": " +quant);
			quant = 0;
		}
		
		totalOrderSumText.setText(String.valueOf(totalSum));
		if (personalOrders == 0)
			personalOrders = 1;
		avgSum = totalSum / personalOrders;
		avgSum = (Math.round(avgSum * 100)) / 100;
		avgOrderSumText.setText(String.valueOf(avgSum) + "\t\t" +
			"(" + totalSum + " / " + personalOrders + " customers )");
		
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
		
		companyNameLabel.setText(resLunchy.getString("Company_Name"));
		companyNameLabel.setLayoutData(gridData);
		
		Label companyNameText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		companyNameText.setText(options.getProperty("CompanyName"));
		companyNameText.setLayoutData(gridData);
		
		Label companyPhoneLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		companyPhoneLabel.setText(resLunchy.getString("Company_Phone"));
		companyPhoneLabel.setLayoutData(gridData);
		
		Label companyPhoneText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		companyPhoneText.setText(options.getProperty("CompanyPhone"));
		companyPhoneText.setLayoutData(gridData);
		
		Label orderDateLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		orderDateLabel.setText(resLunchy.getString("Order_date"));
		orderDateLabel.setLayoutData(gridData);
		
		Label orderDateText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		orderDateText.setText("31-12-2012");
		orderDateText.setLayoutData(gridData);
		
		Label auxCaption = new Label(shell, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalIndent = 50;
		gridData.horizontalSpan = 2;
		auxCaption.setText(resLunchy.getString("Com_order"));
		auxCaption.setLayoutData(gridData);
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		//gridData.horizontalIndent = 30;
		gridData.horizontalSpan = 4;
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
					//System.out.println("ddd");
				}
			});
		}
		
		TableColumn[] colummm = table.getColumns();
		colummm[0].setWidth(30);
		colummm[1].setWidth(120);
		colummm[2].setWidth(100);
		colummm[3].setWidth(90);
		colummm[4].setWidth(90);
		colummm[5].setWidth(90);
		
		Label avgOrderSumLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		//gridData.horizontalIndent = 50;
		avgOrderSumLabel.setText(resLunchy.getString("Avg_order_sum"));
		avgOrderSumLabel.setLayoutData(gridData);
		
		avgOrderSumText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		avgOrderSumText.setText("000");
		avgOrderSumText.setLayoutData(gridData);
		
		Label totalOrderSumLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		//gridData.horizontalIndent = 50;
		totalOrderSumLabel.setText(resLunchy.getString("Total_order_sum"));
		totalOrderSumLabel.setLayoutData(gridData);
		
		totalOrderSumText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		totalOrderSumText.setText("000");
		totalOrderSumText.setLayoutData(gridData);
		
		Label warningLabel = new Label(shell, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		warningLabel.setText("");
		warningLabel.setLayoutData(gridData);
		
		Button sendButton = new Button(shell, SWT.PUSH);
		sendButton.setText(resLunchy.getString("Send_order"));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 170;
		gridData.horizontalAlignment = GridData.CENTER;
		sendButton.setLayoutData(gridData);
		sendButton.addSelectionListener(new SelectionAdapter() {		
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = table.getItems();
				ArrayList<String[]> printData = new ArrayList<>();
				for (int i = 0; i < items.length; i++) {
					String[] res = new String[6];
					res[0] = items[i].getText(0);
					res[1] = items[i].getText(1);
					res[2] = items[i].getText(2);
					res[3] = items[i].getText(3);
					res[4] = items[i].getText(4);
					res[5] = items[i].getText(5);
					printData.add(res);
				}
				
				PrintOrder.PrintGeneralOrder(shell, printData);
			}
		});
		
	}

}
