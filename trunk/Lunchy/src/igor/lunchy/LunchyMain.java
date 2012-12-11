/*
 *   Main Form (Menu) for current project 
 * 
 */

package igor.lunchy;


//import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private static Button btMenuEditor;
	private static Button btOrder;
	private static Button btSendOrder;
	private static Button btPrint;
	private static Button btOptions;
	
	private static ToolTip tip = null;
	private static Tray tray = null;
	private static int logined = 0;
	
	private static boolean changed = false;
	public static String status = "";
	public Label statusLabel;
	public static Properties options;
	public static boolean orderSentStatus = false;
	
	private static boolean makeOrderAvailability = false;
	
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
	
	
	public static void setMakeOrderAvailability() {
		System.out.println("setMakeOrderAvailability");
		boolean menuUpdateStatus = getMenuUpdateStatus();
		boolean dateNotExpired = true;
		boolean timeNotExpired = true;
		
		Date menuExpirationDate = null;
		Date menuExpirationTime = null;
		
		Date currentDate = new Date();
		Date currentTime = new Date();
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		
		String tempDate = sdfDate.format(currentDate);
		String tempTime = sdfTime.format(currentTime);
		
		System.out.println(tempDate);
		System.out.println(tempTime);
		
		try {
			menuExpirationDate = sdfDate.parse(getMenuExpirationDate());
			menuExpirationTime = sdfTime.parse(options.getProperty("LastTimePreparationOrder"));
			
			currentDate = sdfDate.parse(tempDate);
			currentTime = sdfTime.parse(tempTime);
			//String tempTime2 = sdfTime.format(currentTime);
			
		} catch (ParseException e) {
			System.out.println("Date Parse Error");
		}
		
		if (currentDate.after(menuExpirationDate)) {
			dateNotExpired = false;
			System.out.println("Date expired");
		}
		if (currentTime.after(menuExpirationTime)) {
			timeNotExpired = false;
			System.out.println("Time expired");
		}
				
		makeOrderAvailability = menuUpdateStatus && dateNotExpired && timeNotExpired;
	}
	
	public static boolean getMenuAvailability() {
		return makeOrderAvailability;
	}
	
	
	public static void setMenuUpdateStatus(boolean value) {
		if (value) {
			options.setProperty("MenuUpdated", "Yes");
		} else {
			options.setProperty("MenuUpdated", "No");
		}
		changed = true;
	}
	
	public static boolean getMenuUpdateStatus() {
		if (options.getProperty("MenuUpdated").equals("Yes")) {
			return true;			
		} else {
			return false;
		}
	}
	
	public static void setMenuExpirationDate(int year, int month, int day) {
		String strMonth = ((month+1)<10) ? ("0" + String.valueOf(month+1)) : String.valueOf(month+1);
		String strDay = ((day)<10) ? ("0" + String.valueOf(day)) : String.valueOf(day);
		String result = year + "-" + strMonth + "-" + strDay;
		//System.out.println(result);
		options.setProperty("MenuExpirationDate", result);
	}
	
	public static String getMenuExpirationDate() {
		return options.getProperty("MenuExpirationDate");
	}
	
	public static void showTrayToolTip(String caption, String message) {
		if ((tray != null) && (tip != null)) {
			tip.setText(caption);
			tip.setMessage(message);
			tip.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		
		/// Loading application options
		//btMenuEditor.
		options = lunchyOptions.loadOptions();
		if (options.getProperty("Language").equals("English"))
			resLunchy = ResourceBundle.getBundle("lunchy_en");
		else if (options.getProperty("Language").equals("Russian"))
			resLunchy = ResourceBundle.getBundle("lunchy_ru");
		else
			resLunchy = ResourceBundle.getBundle("lunchy_en");
		
		setMakeOrderAvailability();
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
		tip = new ToolTip(shell, SWT.BALLOON | SWT.ICON_INFORMATION);
		tray = display.getSystemTray();
		if (tray != null) {
			TrayItem item = new TrayItem(tray, SWT.NONE);
			Image image = new Image(display, "icon2.jpg");
			item.setImage(image);
			item.setToolTip(tip);
		}
		
		if (logined == 0) {
			//showTrayToolTip("Login failed", "Login failed");
			shell.close();
		}
		
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) {
				display.sleep();
				
				if (changed) {
					System.out.println("Changed");
					setMakeOrderAvailability();
					if (makeOrderAvailability) {
						btOrder.setEnabled(true);
					} else {
						btOrder.setEnabled(false);
					}
					changed = false;
				}
				//System.out.println("FFF");
			}
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
		logined = openFormLogin();
		
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
		btMenuEditor = new Button(shell, SWT.PUSH);
		btMenuEditor.setText(resLunchy.getString("Edit_menu"));
		btMenuEditor.setLayoutData(new RowData(170, 40));
		btMenuEditor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//showTrayToolTip("Caption", "Message");
				openFormMenuListEdit();
			}
		});
		
		// "Make Order" button
		btOrder = new Button(shell, SWT.PUSH);
		btOrder.setText(resLunchy.getString("Make_order"));
		btOrder.setEnabled(makeOrderAvailability);
		btOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderCreation();
			}
		});
		
		// "Send order" button
		btSendOrder = new Button(shell, SWT.PUSH);
		btSendOrder.setText(resLunchy.getString("Send_order"));
		btSendOrder.setEnabled(true);
		btSendOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openFormOrderSending();
			}
		});
		
		// "Print order" button
		btPrint = new Button(shell, SWT.PUSH);
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
 		btOptions = new Button(shell, SWT.PUSH);
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
	
	private int openFormLogin() {
		FormLogin loginForm = new FormLogin(shell);
		return loginForm.open();
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
