/*
 *   Main Form (Menu) for current project 
 * 
 */

package igor.lunchy;


//import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static int lang = 2;
	
	private static Properties options;
	
	// Reading resource file 
	private static ResourceBundle resLunchy = (lang == 1) ? ResourceBundle.getBundle("lunchy_ru") :
		ResourceBundle.getBundle("lunchy_en");
	
	// Work data
	public static ArrayList<MenuItem> menuList;
	public static ArrayList<Category> categoryList;
	public static ArrayList<Worker> workerList;	
	
	private static Properties initOptions() throws IOException {
		Properties props = new Properties();
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream("options.dat");
		} catch (FileNotFoundException e) {
			System.out.println("File not exist");
			fout = new FileOutputStream("options.dat");
		}
		
		
		return props;
	}
	
	
	public static void main(String[] args) {
		try {
			initOptions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		//System.out.println(MenuItem.newId());
		
		if ((menuList.size() > 0) && (categoryList.size() > 0) && (workerList.size() > 0))
			System.out.println("OK");
		
		//menuList.add(new MenuItem(MenuItem.newId(), "444", 1, "D", 34.6, false));
		//menuList.add(new MenuItem(MenuItem.newId(), "555", 2, "A", 34.6, true));
		//categoryList.add(new Category(Category.newId(), "ttt"));
		//menuList.set(5, new MenuItem(5, "777", 1, "777", 34.6, false));
		
		boolean res4 = menuitemDAO.putMenuItemCol(menuList);
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
		
		menuitemDAO.putMenuItemCol(menuList);
			
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
