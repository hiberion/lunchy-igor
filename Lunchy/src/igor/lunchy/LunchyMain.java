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

//import entities.MenuItem;
import forms.*;
import dao.*;



public class LunchyMain {
	
	private Shell shell;
	public static String status = "";
	public Label statusLabel;
	public static Properties options;
	
	public static boolean menuAvailability = true;
	
	private static LunchyOptions lunchyOptions = new LunchyOptions();
	
	// Reading resource file (Default value = lunchy_en)
	public static ResourceBundle resLunchy = ResourceBundle.getBundle("lunchy_en");
	
	// Work data
	// Way #1
	/*
	public static ArrayList<MenuItem> menuList;
	public static ArrayList<Category> categoryList;
	public static ArrayList<Worker> workerList;
	public static ArrayList<GeneralOrder> generalOrderList;
	public static ArrayList<PersonalOrder> personalOrderList;
	public static ArrayList<MenuItemPersonalOrder> menuItemPersonalOrderList;
	*/
	
	// Way #2
	public static IMenuItemDAO menuItemDAO;
	public static ICategoryDAO categoryDAO;
	public static IWorkerDAO workerDAO;
	public static IGeneralOrderDAO generalOrderDAO;
	public static IPersonalOrderDAO personalOrderDAO;
	public static IMenuItemPersonalOrderDAO menuItemPersonalOrderDAO;
	
	
	public static void setMenuAvailability(boolean value) {
		menuAvailability = value;
	}
	
	public static void main(String[] args) {
		
		/// Loading application options
		options = lunchyOptions.loadOptions();
		if (options.getProperty("Language").equals("English"))
			resLunchy = ResourceBundle.getBundle("lunchy_en");
		else if (options.getProperty("Language").equals("Russian"))
			resLunchy = ResourceBundle.getBundle("lunchy_ru");
		else
			resLunchy = ResourceBundle.getBundle("lunchy_en");
		//////////////////////////////////////////
		
		/// Getting data from datasource
		String dbPass = options.getProperty("DBPass");
		DAOFactory daoFactory = new DAOFactory(dbPass);
		
		// Selection data source
		String dataSource = options.getProperty("DataSource");
		if (dataSource.equals("TextFile"))
			daoFactory = daoFactory.getDAOFactory(IDAOFactory.TEXTFILE);
		else if (dataSource.equals("JDBC"))
			daoFactory = daoFactory.getDAOFactory(IDAOFactory.JDBC);
		else if (dataSource.equals("Hibernate"))
			daoFactory = daoFactory.getDAOFactory(IDAOFactory.HIBERNATE);
		else
			daoFactory = daoFactory.getDAOFactory(IDAOFactory.TEXTFILE);
		
		
		try {
			menuItemDAO = daoFactory.getMenuItemDAO();
			categoryDAO = daoFactory.getCategoryDAO();
			workerDAO = daoFactory.getWorkerDAO();
			
			generalOrderDAO = daoFactory.getGeneralOrderDAO();
			personalOrderDAO = daoFactory.getPersonalOrderDAO();
			menuItemPersonalOrderDAO = daoFactory.getMenuItemPersonalOrderDAO();
			
		} catch (Exception e) {
			System.out.println(e);
			status = "Error creation datasource";
			//return;
		}
		
		//// DEBUG  &   TEST
		//IMorderDAO morderDAO = daoFactory.getMorderDAO();
		//Morder morder = morderDAO.getMorderByID(2);
		//System.out.println(morder.getId() + " " + morder.getSumma() + " " + morder.getClientId());
		//Category category = categoryDAO.getCategoryByID(1);
		//System.out.println(category.getId() + " " + category.getName());
		//MenuItem mi = menuItemDAO.getMenuItemByID(11);
		//System.out.println(mi.getId() + " " + mi.getName());
		
		/*ArrayList<MenuItem> menu = menuItemDAO.getAllMenuItem();
		for (MenuItem item : menu) {
			System.out.println(item.toString());
		}*/
		
		///////////////////////////
		
		// (DEBUG)
		//MenuItem mi = new MenuItem(7, "YYY", 3, "XXX", 12.3, true);
		//menuItemDAO.addMenuItem(mi);
		/*
		menuList = menuItemDAO.getAllMenuItem();
		categoryList = categoryDAO.getAllCategory();
		workerList = workerDAO.getAllWorker();
		
		generalOrderList = generalOrderDAO.getAllGeneralOrder();
		personalOrderList = personalOrderDAO.getAllPersonalOrder();
		menuItemPersonalOrderList = menuItemPersonalOrderDAO.getAllMenuItemPersonalOrder();
		/*   Without part two
		generalOrderList = new ArrayList<>();
		Date date = new Date();
		generalOrderList.add(new GeneralOrder(0, date));
		personalOrderList = new ArrayList<>();
		mnItmPrsnlOrderList = new ArrayList<>();
		*/
		//////////////////////////////////////
		/*
		/// (DEBUG) Checking successful data reading
		System.out.println("DEBUG: " + menuList.size() + " " +
				categoryList.size() + " " + workerList.size());
		
		if ((menuList.size() > 0) && (categoryList.size() > 0) && (workerList.size() > 0))
			System.out.println("OK");
		*/
		/////////////////////////////
		
		/// Opening The Main Form of application 
		Display display = new Display();
		LunchyMain application = new LunchyMain();
		Shell shell = application.open(display);		
		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
		/// Saveing current application options
		lunchyOptions.saveOptions(options);
		
		/// Saving collection (needy for TEXTFILE DataSource)
		menuItemDAO.updateAll();
		
		generalOrderDAO.updateAll();
		personalOrderDAO.updateAll();
		menuItemPersonalOrderDAO.updateAll();
		
		/// DEBUG
		/*
		System.out.println("Personal Order:");
		for (PersonalOrder po : personalOrderList)
			System.out.println(po.toString());
		System.out.println("General Order:");
		for (GeneralOrder go : generalOrderList)
			System.out.println(go.toString());
		System.out.println("MI_PO:");
		for (MenuItemPersonalOrder mipo : menuItemPersonalOrderList)
			System.out.println(mipo.toString());
		*/
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
		
		// "Edit Menu" Button
		Button btMenuEditor = new Button(shell, SWT.PUSH);
		btMenuEditor.setText(resLunchy.getString("Edit_menu"));
		btMenuEditor.setLayoutData(new RowData(170, 40));
		btMenuEditor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormMenuListEdit();
			}
		});
		
		// "Make Order" button
		Button btOrder = new Button(shell, SWT.PUSH);
		btOrder.setText(resLunchy.getString("Make_order"));
		btOrder.setEnabled(menuAvailability);
		btOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderCreation();
			}
		});
		
		// "Send order" button
		Button btSendOrder = new Button(shell, SWT.PUSH);
		btSendOrder.setText(resLunchy.getString("Send_order"));
		btSendOrder.setEnabled(true);
		btSendOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderSending();
			}
		});
		
		// "Print order" button
		Button btPrint = new Button(shell, SWT.PUSH);
		btPrint.setText(resLunchy.getString("Print_order"));
		//btPrint.setEnabled(false);
		btPrint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
				System.out.println("Printing the order");
				//PrintOrder.PrintPersonalOrders(shell);
				PrintOrder2.printPersonalOrders();
			}
		});
		
		// "Options" button
 		Button btOptions = new Button(shell, SWT.PUSH);
		btOptions.setText(resLunchy.getString("Options"));
		btOptions.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormAppOptions();
			}
		});
		
		// Set current status
		statusLabel = new Label(shell, SWT.BORDER);
		statusLabel.setText(status);
		
		//DateTime dt = new DateTime(shell, SWT.CALENDAR);
		//statusLabel.setLayoutData(new RowData(150, 60));
		
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
