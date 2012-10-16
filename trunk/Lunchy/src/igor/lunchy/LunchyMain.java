/*
 *   Main Form (Menu) for current project 
 * 
 */

package igor.lunchy;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class LunchyMain {
	
	private Shell shell;
	public static String status = "";
	public Label statusLabel;
	
	
	public static void main(String[] args) {
		Display display = new Display();
		LunchyMain application = new LunchyMain();
		Shell shell = application.open(display);
		System.out.println("OK");
		/*
		String[][] Data = ParseTextFile.getStringTable("menu.txt", "_", 6);
		int i = 0;
		System.out.println(Data.length);
		for (String[] str: Data) {
			for (String str2: str) {
				System.out.print(str2 + " ");
			}
			System.out.println();
		}
		
		MenuEnrty[] menu = MenuEnrty.getMenuEntries("menu.txt");
		System.out.println(menu[4].name);
		*/
		
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
		btMenuEditor.setText("Edit menu");
		btMenuEditor.setLayoutData(new RowData(170, 40));
		btMenuEditor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openEditMenu();
			}
		});
		
		Button btOrder = new Button(shell, SWT.PUSH);
		btOrder.setText("Make order");
		btOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
			}
		});
		
		Button btSendOrder = new Button(shell, SWT.PUSH);
		btSendOrder.setText("Send Order");
		btSendOrder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
			}
		});
		
		Button btPrint = new Button(shell, SWT.PUSH);
		btPrint.setText("Print Order");
		btPrint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
			}
		});
		
		Button btOptions = new Button(shell, SWT.PUSH);
		btOptions.setText("Options");
		btOptions.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//openEditMenu();
			}
		});
		
		statusLabel = new Label(shell, SWT.BORDER);
		statusLabel.setText(status);
		
		
				
		//DateTime dt = new DateTime(shell, SWT.CALENDAR);
		//statusLabel.setLayoutData(new RowData(150, 60));
		
		//return statusLabel;
	}
	
	private void openEditMenu() {
		//System.out.println("All OK");
		MenuEditForm menuForm = new MenuEditForm(shell);
		menuForm.open();
	}
	
	
	static public void setStatusLabel() {
		
	}

}
