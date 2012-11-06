package forms;


import igor.lunchy.LunchyMain;

import java.util.Properties;
import java.util.ResourceBundle;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class FormAppOptions {
	
	Shell shell;
	Properties options = LunchyMain.options;
	private static ResourceBundle resLunchy = LunchyMain.resLunchy;
	
	public FormAppOptions(Shell parent) {
		
		shell = new Shell(parent, SWT.SHELL_TRIM);		
		shell.setText(resLunchy.getString("Application_options"));
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	public void open() {
		
		createWidgets();
		
		Rectangle clientArea = shell.getClientArea();
		shell.setLocation(clientArea.width/2+50, clientArea.height/2-50);
		shell.pack();
		shell.open();
		shell.setSize(400, shell.getSize().y);
		
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void createWidgets() {
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		shell.setLayout(layout);
		
		// Company Name Section
		Label companyNameLabel = new Label(shell, SWT.NONE);
		GridData gridData = new GridData();
		companyNameLabel.setText(resLunchy.getString("Company_Name"));
		companyNameLabel.setLayoutData(gridData);
		
		final Text companyNameText = new Text(shell, SWT.BORDER);
		companyNameText.setText(options.getProperty("CompanyName"));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		companyNameText.setLayoutData(gridData);
		companyNameText.setTextLimit(150);
		////////////////////////////
		
		// Company Phone Section
		Label companyPhoneLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		companyPhoneLabel.setText(resLunchy.getString("Company_Phone"));
		companyPhoneLabel.setLayoutData(gridData);
				
		final Text companyPhoneText = new Text(shell, SWT.BORDER);
		companyPhoneText.setText(options.getProperty("CompanyPhone"));
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		companyPhoneText.setLayoutData(gridData);
		companyPhoneText.setTextLimit(15);
		////////////////////////////////////////
		
		// Menu expiration period Section
		Label menuExpPeriodLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		menuExpPeriodLabel.setText(resLunchy.getString("Menu_expiration_period"));
		menuExpPeriodLabel.setLayoutData(gridData);
		
		final Text menuExpTime = new Text(shell, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		menuExpTime.setText(options.getProperty("MenuExpirationTime"));
		menuExpTime.setLayoutData(gridData);
		menuExpTime.setTextLimit(3);
		menuExpTime.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;				
				//System.out.println("Handle " + str);
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
		//////////////////////////////////
		
		// Data source group Section
		///// Group
		Group sourceGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sourceGroup.setText(resLunchy.getString("Data_Source"));
		sourceGroup.setLayoutData(gridData);
		sourceGroup.setLayout(layout);
		String dataSource = options.getProperty("DataSource");
		
		// Radiobuttons 
		///// Button 1
		final Button textSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		textSource.setText(resLunchy.getString("Text_file"));
		textSource.setLayoutData(gridData);
		textSource.setSelection((dataSource.equals("TextFile")) ? true : false);
		
	///// Button 2
		final Button JDBCSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		JDBCSource.setText("JDBC (PostgreSQL)");
		JDBCSource.setLayoutData(gridData);
		JDBCSource.setSelection((dataSource.equals("JDBC")) ? true : false);
		
	///// Button 3
		final Button HibernateSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		HibernateSource.setText("Hibernate (PostgreSQL)");
		HibernateSource.setLayoutData(gridData);
		HibernateSource.setSelection((dataSource.equals("Hibernate")) ? true : false);
		/////////////////////////////////////////
		
		// Discoount Group Section
		///// Group
		Group discountGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		discountGroup.setText(resLunchy.getString("Discount"));
		discountGroup.setLayoutData(gridData);
		discountGroup.setLayout(layout);
		
		// Discount sum Section
		Label discSumLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discSumLabel.setText(resLunchy.getString("Discount_sum"));
		discSumLabel.setLayoutData(gridData);
		
		final Text discSumText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discSumText.setText(options.getProperty("DiscountSumm"));
		discSumText.setLayoutData(gridData);
		discSumText.setTextLimit(5);
		discSumText.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;				
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
		/////////////////////////////////
		
		// Discount diff = order sum - discount sum
		Label discSumDiffLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discSumDiffLabel.setText(resLunchy.getString("Discount_sum_diff"));
		discSumDiffLabel.setLayoutData(gridData);
		
		final Text discSumDiffText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discSumDiffText.setText(options.getProperty("DiscountSummDiff"));
		discSumDiffText.setLayoutData(gridData);
		discSumDiffText.setTextLimit(5);
		discSumDiffText.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;				
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
		//////////////////////////////////////
		
		// Percent discount Section
		Label discPercentLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discPercentLabel.setText(resLunchy.getString("Discount_percent"));
		discPercentLabel.setLayoutData(gridData);
		
		final Text discPercentText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discPercentText.setText(options.getProperty("DiscountPercent"));
		discPercentText.setLayoutData(gridData);
		discPercentText.setTextLimit(2);
		discPercentText.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;				
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
		////////////////////////////////////////
		
		// Order settings Section
		////// Group
		Group orderSetsGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		orderSetsGroup.setText(resLunchy.getString("Order_settings"));
		orderSetsGroup.setLayoutData(gridData);
		orderSetsGroup.setLayout(layout);
		
		// Last time for sending order Section
		Label lastTimeSendLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		lastTimeSendLabel.setText(resLunchy.getString("Last_time_send_order"));
		lastTimeSendLabel.setLayoutData(gridData);
		
		final DateTime lastTimeSendTime = new DateTime(orderSetsGroup, SWT.TIME|SWT.SHORT|SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		String time = options.getProperty("LastTimeSendOrder");
		int [] intTime = stringTimeToInt(time);
		lastTimeSendTime.setTime(intTime[0], intTime[1], 0);
		lastTimeSendTime.setLayoutData(gridData);
		//////////////////////////////////////////
		
		// Last time for preparation single order Section
		Label lastTimePrepLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		lastTimePrepLabel.setText(resLunchy.getString("Last_time_preparation_order"));
		lastTimeSendLabel.setLayoutData(gridData);
		
		final DateTime lastTimePrepTime = new DateTime(orderSetsGroup, SWT.TIME|SWT.SHORT|SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		String time2 = options.getProperty("LastTimePreparationOrder");
		int [] intTime2 = stringTimeToInt(time2);
		lastTimePrepTime.setTime(intTime2[0], intTime2[1], 0);
		lastTimePrepTime.setLayoutData(gridData);
		///////////////////////////////////////////////
		
		// Max sum single orders Section
		Label singleOrderMaxSumLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		singleOrderMaxSumLabel.setText(resLunchy.getString("Single_order_max_sum"));
		singleOrderMaxSumLabel.setLayoutData(gridData);
		
		final Text singleOrderMaxSumText = new Text(orderSetsGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		singleOrderMaxSumText.setText(options.getProperty("SingleOrderMaxSum"));
		singleOrderMaxSumText.setLayoutData(gridData);
		singleOrderMaxSumText.setTextLimit(5);
		singleOrderMaxSumText.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;				
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
		/////////////////////////////////////
		
		// Warning settings Section
		Group otherSettingsGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		gridData = new GridData(GridData.FILL_HORIZONTAL|GridData.FILL_VERTICAL);
		gridData.horizontalSpan = 2;
		otherSettingsGroup.setText(resLunchy.getString("Other_settings"));
		otherSettingsGroup.setLayoutData(gridData);
		otherSettingsGroup.setLayout(layout);
		
		final Button warnButton = new Button(otherSettingsGroup, SWT.CHECK);
		warnButton.setText(resLunchy.getString("Show_warnings"));
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		warnButton.setLayoutData(gridData);
		warnButton.setSelection((options.getProperty("Warnings").equals("Yes")) ? true : false);
		
		Label languageLabel = new Label(otherSettingsGroup, SWT.NONE);
		gridData = new GridData();
		languageLabel.setText(resLunchy.getString("Language"));
		languageLabel.setLayoutData(gridData);
		
		final Combo comboLanguage = new Combo(otherSettingsGroup, SWT.READ_ONLY);
		gridData = new GridData();
		gridData.widthHint = 100;
		comboLanguage.setLayoutData(gridData);
		comboLanguage.setItems(new String[] { "English", "Русский" });
		if (options.getProperty("Language").equals("English"))
			comboLanguage.select(0);
		else if (options.getProperty("Language").equals("Russian"))
			comboLanguage.select(1);
		else
			comboLanguage.select(0);
		
		//////////////////////////////////////
		
		// Save options button Section
		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setText("Save settings");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 170;
		gridData.horizontalAlignment = GridData.CENTER;
		saveButton.setLayoutData(gridData);
		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				///// Saving options
				
				// Save "Company Name"
				String result = "";
				result = companyNameText.getText();
				if (result.length() == 0)
					result = "Company";
				options.setProperty("CompanyName", result);
				
				// Save "Company Phone"
				result = companyPhoneText.getText();
				options.setProperty("CompanyPhone", result);
				
				// Save Data source
				if (textSource.getSelection())
					result = "TextFile";
				else if (JDBCSource.getSelection())
					result = "JDBC";
				else if (HibernateSource.getSelection())
					result = "Hibernate";
				else result = "TextFile";
				options.setProperty("DataSource", result);
				
				// Save Discount Summ
				result = discSumText.getText();
				if (result.length() == 0)
					result = "0";
				options.setProperty("DiscountSumm", result);
				
				// Save Discount Summ Diff
				result = discSumDiffText.getText();
				if (result.length() == 0)
					result = "0";
				options.setProperty("DiscountSummDiff", result);
				
				// Save Discount Percent
				result = discPercentText.getText();
				if (result.length() == 0)
					result = "0";
				options.setProperty("DiscountPercent", result);
				
				// Save Last Time Send Order
				result = intTimeToString(lastTimeSendTime.getHours(), lastTimeSendTime.getMinutes());
				options.setProperty("LastTimeSendOrder", result);
				
				// Save Last Time Preparation Order
				result = intTimeToString(lastTimePrepTime.getHours(), lastTimePrepTime.getMinutes());
				options.setProperty("LastTimePreparationOrder", result);
				
				// Save Single Order Max Sum
				result = singleOrderMaxSumText.getText();
				if (result.length() == 0)
					result = "0";
				options.setProperty("SingleOrderMaxSum", result);
				
				// Save Warnings
				result = warnButton.getSelection() ? "Yes" : "No";
				options.setProperty("Warnings", result);
				
				// Save Language
				int selected = comboLanguage.getSelectionIndex();
				if (selected == 0)
					result = "English";
				else if (selected == 1)
					result = "Russian";
				else
					result = "English";
				options.setProperty("Language", result);
				
				displayWarning(resLunchy.getString("Options_warning1"));
				
				//shell.close();
			}
		});
		///////////////////////////
	}
	
	/// Convert time in string form to int array
	int[] stringTimeToInt(String time) {
		
		int [] result = new int[2];
		String hours = time.substring(0,2);
		String minutes = time.substring(3,5);
		
		try {
			result[0] = Integer.parseInt(hours);
			result[1] = Integer.parseInt(minutes);
		} catch (Exception e) {
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}
	
	/// Convert time in int form to string form
	String intTimeToString(int hours, int minutes) {
		
		String result = new String();
		
		if (hours > 23)
			hours = 23;
		if (minutes > 59)
			minutes = 59;
		
		String strHours = String.valueOf(hours);
		if (strHours.length() < 2)
			strHours = "0" + strHours;
		String strMinutes = String.valueOf(minutes);
		if (strMinutes.length() < 2)
			strMinutes = "0" + strMinutes;
		result = strHours + ":" + strMinutes;
		
		return result;
	}
	
	private void displayWarning(String msg) {
		MessageBox box = new MessageBox(shell, SWT.ICON_WARNING);
		box.setText(resLunchy.getString("MessageBoxCaption1"));
		box.setMessage(msg);
		box.open();
	}
}


