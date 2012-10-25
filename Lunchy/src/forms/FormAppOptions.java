package forms;


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
	char[] chars = { '1','9' };
	
	public FormAppOptions(Shell parent) {
		shell = new Shell(parent, SWT.SHELL_TRIM);		
		shell.setText("Application options");
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
		
		// Company Name
		Label companyNameLabel = new Label(shell, SWT.NONE);
		GridData gridData = new GridData();
		companyNameLabel.setText("Company Name: ");
		companyNameLabel.setLayoutData(gridData);
		
		Text companyNameText = new Text(shell, SWT.BORDER);
		companyNameText.setText("Company");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		companyNameText.setLayoutData(gridData);
		
		// Company Phone
		Label companyPhoneLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		companyPhoneLabel.setText("Company Phone: ");
		companyPhoneLabel.setLayoutData(gridData);
				
		Text companyPhoneText = new Text(shell, SWT.BORDER);
		companyPhoneText.setText("555-55-55");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		companyPhoneText.setLayoutData(gridData);
		
		// Menu expiration period
		Label menuExpPeriodLabel = new Label(shell, SWT.NONE);
		gridData = new GridData();
		menuExpPeriodLabel.setText("Menu expiration period: ");
		menuExpPeriodLabel.setLayoutData(gridData);
		
		Text menuExpTime = new Text(shell, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		menuExpTime.setText("48");
		menuExpTime.setLayoutData(gridData);
		
		// Data source group
		Group sourceGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sourceGroup.setText("Data Source");
		sourceGroup.setLayoutData(gridData);
		sourceGroup.setLayout(layout);
		
		// Radiobuttons
		Button textSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		textSource.setText("Text file");
		textSource.setLayoutData(gridData);
		textSource.setSelection(true);
		
		Button JDBCSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		JDBCSource.setText("JDBC (PostgreSQL)");
		JDBCSource.setLayoutData(gridData);
		
		Button HibernateSource = new Button(sourceGroup, SWT.RADIO);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		HibernateSource.setText("Hibernate (PostgreSQL)");
		HibernateSource.setLayoutData(gridData);
		/////////////////////////////////////////
		
		// Discoount Group
		Group discountGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		discountGroup.setText("Discount");
		discountGroup.setLayoutData(gridData);
		discountGroup.setLayout(layout);
		
		// Discount sum
		Label discSumLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discSumLabel.setText("Discount sum: ");
		discSumLabel.setLayoutData(gridData);
		
		Text discSumText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discSumText.setText("1000");
		discSumText.setLayoutData(gridData);
		
		// Discount diff = order sum - discount sum
		Label discSumDiffLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discSumDiffLabel.setText("Discount sum diff: ");
		discSumDiffLabel.setLayoutData(gridData);
		
		Text discSumDiffText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discSumDiffText.setText("100");
		discSumDiffText.setLayoutData(gridData);
		
		// Percent discount
		Label discPercentLabel = new Label(discountGroup, SWT.NONE);
		gridData = new GridData();
		discPercentLabel.setText("Discount %: ");
		discPercentLabel.setLayoutData(gridData);
		
		Text discPercentText = new Text(discountGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		discPercentText.setText("15");
		discPercentText.setLayoutData(gridData);
		
		// Order settings
		Group orderSetsGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		orderSetsGroup.setText("Order settings");
		orderSetsGroup.setLayoutData(gridData);
		orderSetsGroup.setLayout(layout);
		
		// Last time for sending order
		Label lastTimeSendLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		lastTimeSendLabel.setText("Last time send order: ");
		lastTimeSendLabel.setLayoutData(gridData);
		
		DateTime lastTimeSendTime = new DateTime(orderSetsGroup, SWT.TIME|SWT.SHORT|SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		lastTimeSendTime.setTime(16, 0, 0);
		lastTimeSendTime.setLayoutData(gridData);
		
		// Last time for preparation single order
		Label lastTimePrepLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		lastTimePrepLabel.setText("Last time preparation order: ");
		lastTimeSendLabel.setLayoutData(gridData);
		
		DateTime lastTimePrepTime = new DateTime(orderSetsGroup, SWT.TIME|SWT.SHORT|SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		lastTimePrepTime.setTime(15, 0, 0);
		lastTimePrepTime.setLayoutData(gridData);
		
		// Max sum single orders
		Label singleOrderMaxSumLabel = new Label(orderSetsGroup, SWT.NONE);
		gridData = new GridData();
		singleOrderMaxSumLabel.setText("Single order max sum: ");
		singleOrderMaxSumLabel.setLayoutData(gridData);
		
		Text singleOrderMaxSumText = new Text(orderSetsGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		singleOrderMaxSumText.setText("200");
		singleOrderMaxSumText.setLayoutData(gridData);
		
		// Warning settings
		Group warningGroup = new Group(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		gridData = new GridData(GridData.FILL_HORIZONTAL|GridData.FILL_VERTICAL);
		gridData.horizontalSpan = 2;
		warningGroup.setText("Warning settings");
		warningGroup.setLayoutData(gridData);
		warningGroup.setLayout(layout);
		
		Button warnButton = new Button(warningGroup, SWT.CHECK);
		warnButton.setText("Show warnings");
		gridData = new GridData();
		warnButton.setLayoutData(gridData);
		
		// Save options button
		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setText("Save settings");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.widthHint = 170;
		gridData.horizontalAlignment = GridData.CENTER;
		saveButton.setLayoutData(gridData);
		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Click!!!");
			}
		});
		
	}
}


