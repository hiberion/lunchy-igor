/*
 *   Main Form (Menu) for current project 
 * 
 */

package igor.lunchy;


//import java.io.*;
import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.Category;
import entities.MenuItem;
import entities.Worker;
import forms.*;
import dao.*;



public class LunchyMain {
	
	private Shell shell;
	public static String status = "";
	public Label statusLabel;
	
	private static ResourceBundle resLunchy = ResourceBundle.getBundle("lunchy_en");
	
	public static ArrayList<MenuItem> menuList;
	public static ArrayList<Category> categoryList;
	public static ArrayList<Worker> workerList;
	
	
	public static void main(String[] args) {
		DAOFactory textfileDAOFactory = 
				DAOFactory.getDAOFactory(DAOFactory.TEXTFILE);
		
		IMenuItemDAO menuitemDAO = textfileDAOFactory.getMenuItemDAO();
		ICategoryDAO categoryDAO = textfileDAOFactory.getCategoryDAO();
		IWorkerDAO workerDAO = textfileDAOFactory.getWorkerDAO();
		
		menuList = menuitemDAO.getMenuItemCol();
		categoryList = categoryDAO.getCategoryCol();
		workerList = workerDAO.getWorkerCol();
		
		System.out.println(menuList.size() + " " +
				categoryList.size() + " " + workerList.size());
		
		if ((menuList.size() > 0) && (categoryList.size() > 0) && (workerList.size() > 0))
			System.out.println("OK");
		
		//menuList.add(new MenuItem(MenuItem.curID++, "RR", 1, "D", 34.6, false));
		
		//boolean res4 = menuitemDAO.putMenuItemCol(menuList);
		//boolean res5 = categoryDAO.putCategoryCol(categoryList);
		//boolean res6 = workerDAO.putWorkerCol(workerList);		
		
		Display display = new Display();
		LunchyMain application = new LunchyMain();
		Shell shell = application.open(display);
		//System.out.println("OK");
		
		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
			
	}
	
	public Shell open(Display display) {
		shell = new Shell(display, SWT.DIALOG_TRIM | SWT.MIN);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.spacing = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginTop = 10;
		layout.marginBottom = 10;
		layout.pack = false;
		shell.setLayout(layout);
		
		createControlButtons();
		
		Rectangle clientArea = display.getClientArea();
		shell.setText("  Lunchy");
		shell.setLocation(clientArea.width/2-80, clientArea.height/2-150);
		shell.setImage(new Image(display, "icon2.jpg"));
		shell.pack();
		shell.open();
		
		return shell;
	}
	
	private void createControlButtons() {
		Button btMenuEditor = new Button(shell, SWT.PUSH);
		btMenuEditor.setText(resLunchy.getString("Edit_menu"));
		btMenuEditor.setLayoutData(new RowData(170, 40));
		btMenuEditor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormMenuListEdit();
			}
		});
		
		Button btOrder = new Button(shell, SWT.PUSH);
		btOrder.setText(resLunchy.getString("Make_order"));
		btOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderCreation();
			}
		});
		
		Button btSendOrder = new Button(shell, SWT.PUSH);
		btSendOrder.setText(resLunchy.getString("Send_order"));
		btSendOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderSending();
			}
		});
		
		Button btPrint = new Button(shell, SWT.PUSH);
		btPrint.setText(resLunchy.getString("Print_order"));
		btPrint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
				System.out.println("Printing the order");
			}
		});
		
		Button btOptions = new Button(shell, SWT.PUSH);
		btOptions.setText(resLunchy.getString("Options"));
		btOptions.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormAppOptions();
			}
		});
		
		statusLabel = new Label(shell, SWT.BORDER);
		statusLabel.setText(status);
		
		
				
		//DateTime dt = new DateTime(shell, SWT.CALENDAR);
		//statusLabel.setLayoutData(new RowData(150, 60));
		
		//return statusLabel;
	}
	
	private void openFormMenuListEdit() {
		//System.out.println("All OK");
		FormMenuListEdit menuForm = new FormMenuListEdit(shell);
		menuForm.open();
	}
	
	private void openFormOrderCreation() {
		FormOrderCreation orderForm = new FormOrderCreation(shell);
		orderForm.open();
	}
	
	private void openFormOrderSending() {
		FormOrderSending orderSendingForm = new FormOrderSending(shell);
		orderSendingForm.open();
	}
	
	private void openFormAppOptions() {
		FormAppOptions optionsForm = new FormAppOptions(shell);
		optionsForm.open();
	}
	
	
	static public void setStatusLabel() {
		
	}

}
