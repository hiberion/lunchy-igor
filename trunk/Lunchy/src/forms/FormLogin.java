package forms;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class FormLogin {
	Shell shell;
	private int state = 0;
	
	public FormLogin(Shell parent) {
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	public int open() {
		createWidgets();
		
		shell.pack();
		shell.open();
		
		Rectangle clientArea = Display.getCurrent().getClientArea();
		shell.setLocation(clientArea.width/2-80, clientArea.height/2-150);
		
		//System.out.println(values[0]+" "+values[1]+" "+values[2]+" "+values[3]+" "+values[4]+" "+values[5]);
		
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		return state;
	}
	
	public void createWidgets() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		shell.setLayout(layout);
		GridData gridData = new GridData();
		
		// ID fields   "0"
		Label labelID = new Label(shell, SWT.RIGHT);
		labelID.setText("Login: ");
		
		Text textLogin = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 100;
		textLogin.setLayoutData(gridData);
		textLogin.setEnabled(true);
		textLogin.setText("Text");
		textLogin.setData("index", new Integer(0));
		/////////////////////////////
		
		// Name fields  "1"
		Label labelName = new Label(shell, SWT.RIGHT);
		labelName.setText("Password: ");	
		final Text textName = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		gridData = new GridData();
		gridData.widthHint = 100;
		textName.setLayoutData(gridData);
		textName.setTextLimit(30);
		textName.setText("TTT");
		textName.setData("index", new Integer(1));
		textName.selectAll();
		
		Button btOK = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		//gridData.horizontalSpan = 2;
		btOK.setText("Login");
		btOK.setLayoutData(gridData);
		btOK.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//values = null;
				//setState(CANCELED);
				state = 1;
				//System.out.println("cancel");
				shell.close();
			}
		});
		
		Button btCancel = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		//gridData.horizontalSpan = 2;
		btCancel.setText("Cancel");
		btCancel.setLayoutData(gridData);
		btCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//values = null;
				//setState(1);
				state = 0;
				//System.out.println("cancel");
				shell.close();
			}
		});
		
	}		
}
