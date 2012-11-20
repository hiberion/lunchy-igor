package forms;

//import java.lang.reflect.Field;

import igor.lunchy.LunchyMain;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.Category;
import entities.MenuItem;

public class DialogMenuRecordEdit {
	Shell shell;
	private static ResourceBundle resLunchy = ResourceBundle.getBundle("lunchy_en");
	String[] columnLabels = { 	resLunchy.getString("ID"),
								resLunchy.getString("Menu_item_name"),
								resLunchy.getString("Menu_cat"),
								resLunchy.getString("Menu_descr"),
								resLunchy.getString("Price"),
								resLunchy.getString("Menu_aval")
								};
	MenuItem currentMenuItem;
	int changesCategory = 1;
	static final int ITEM_CHANGED = 1;
	static final int CANCELED = 2;
	//static final int MODIFIED = 1;
	int state;  // menu item state	
	boolean changesAvailability = true;
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public DialogMenuRecordEdit(Shell parent) {
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	public void open() {
		if (currentMenuItem == null)
				return;
		createWidgets();
		
		shell.pack();
		shell.open();
		
		//System.out.println(values[0]+" "+values[1]+" "+values[2]+" "+values[3]+" "+values[4]+" "+values[5]);
		
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
		GridData gridData = new GridData();
		
		// ID fields   "0"
		Label labelID = new Label(shell, SWT.RIGHT);
		labelID.setText(columnLabels[0]);
		
		Text textID = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400;
		textID.setLayoutData(gridData);
		textID.setEnabled(false);
		textID.setText(String.valueOf(currentMenuItem.getId()));
		textID.setData("index", new Integer(0));
		/////////////////////////////
		
		// Name fields  "1"
		Label labelName = new Label(shell, SWT.RIGHT);
		labelName.setText(columnLabels[1]);	
		final Text textName = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400;
		textName.setLayoutData(gridData);
		textName.setTextLimit(30);
		textName.setText(currentMenuItem.getName());
		textName.setData("index", new Integer(1));
		textName.selectAll();
		textName.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;
				if (string.indexOf("_") != -1)
				{
					//displayError(resLunchy.getString("Input_error1"));
					e.doit = false;
					return;
				}
			}
		});
		/*
		textName.add(new ModifyListener() {
			public void modifyText(ModifyEvent e){
				Integer index = (Integer)(text.getData("index"));
				values[index.intValue()] = text.getText();
			}
		});
		*/
		//addTextListener(textName);
		///////////////////
		
		// Category fields  "2"
		Label labelCat = new Label(shell, SWT.RIGHT);
		labelCat.setText(columnLabels[2]);
		
		final Combo categoryCombo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		// Вариант "Все категории" должен быть не доступен
		String[] comboData = new String[LunchyMain.categoryDAO.getAllCategory().size()-1];
		for (Category cat: LunchyMain.categoryDAO.getAllCategory()) {
			if (cat.getId() == 0)   // ID = 0 имеет "Все категории"
				continue;
			else
				comboData[cat.getId()-1] = cat.getName();
		}
		categoryCombo.setItems(comboData);
		categoryCombo.select(currentMenuItem.getCategory()-1);
		categoryCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Combo");
				changesCategory = categoryCombo.getSelectionIndex()+1;
				//currentMenuItem.setCategory(categoryCombo.getSelectionIndex()+1);
			}
		});
		
		//Display.getCurrent().post(arg0)
		/////////////////////
		
		// Description fields  "3"
		Label labelDescr = new Label(shell, SWT.RIGHT);
		labelDescr.setText(columnLabels[3]);	
		final Text textDescr = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400;
		textDescr.setLayoutData(gridData);
		textDescr.setTextLimit(80);
		textDescr.setText(currentMenuItem.getDescription());
		textDescr.setData("index", new Integer(3));
		textDescr.addListener (SWT.Verify, new Listener () {
			public void handleEvent (Event e) {
				String string = e.text;
				if (string.indexOf("_") != -1)
				{
					//displayError(resLunchy.getString("Input_error1"));
					e.doit = false;
					return;
				}
			}
		});
		//addTextListener(textDescr);
		///////////////////////
		
		// Price fields    "4"
		Label labelPrice = new Label(shell, SWT.RIGHT);
		labelPrice.setText(columnLabels[4]);	
		final Text textPrice = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint = 400;
		textPrice.setLayoutData(gridData);
		textPrice.setText(String.valueOf(currentMenuItem.getPrice()));
		textPrice.setTextLimit(7);
		textPrice.setData("index", new Integer(4));
		//textPrice.setText("777");
		textPrice.addListener (SWT.Verify, new Listener () {
			int countDots = 0;
			
			public void handleEvent (Event e) {
				countDots = 0;
				
				String string = e.text;				
				//System.out.println("Handle " + str);
				char [] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i=0; i<chars.length; i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9') | chars[i] == '.')) {
						e.doit = false;
						return;
					}
					if (chars[i] == '.')
						countDots++;
				}
				
				//if ((e.character == '\b') & (countDots == 1))
					//countDots = 0;
				
				if (countDots > 1) {
					System.out.println(">0");
					e.doit = false;
					return;
				}
				
				Pattern pattern = Pattern.compile(".*[\\.]{1,}.*");
				Matcher matcher = pattern.matcher(textPrice.getText());
				boolean f = matcher.matches();
				System.out.println(textPrice.getText() + " " + f);
				//System.out.println(textPrice.getText().length());
				
				
				if ((f) & (e.character != '\b')) {
					if (e.character == '.') {
						e.doit = false;
						return;
					}
				}
				
				
			}
		});
		//addTextListener(textPrice);
		////////////////////////// 
		
		// Availability fields   "5"
		Label labelAv = new Label(shell, SWT.RIGHT);
		labelAv.setText(columnLabels[5]);
		
		final Button availButton = new Button(shell, SWT.CHECK);
		availButton.setSelection(currentMenuItem.getAvailability());
		availButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//System.out.println("Combo");
				changesAvailability = availButton.getSelection();
				//currentMenuItem.setAvail(availButton.getSelection());
			}
		});
		//////////////////////////
		
		Button btOK = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		//gridData.horizontalSpan = 2;
		btOK.setText("OK");
		btOK.setLayoutData(gridData);
		
		btOK.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				//http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/UIAutomationfortestingtoolssnippetpostkeyevents.htm
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// Store MenuItem Name
				String str = textName.getText().trim();
				if (textName.getText().length() == 0) {
					displayError(resLunchy.getString("Empty_name_error"));
					return;
				} else currentMenuItem.setName(str);
				
				// Store MenuItem Category
				changesCategory = categoryCombo.getSelectionIndex()+1;
				currentMenuItem.setCategory(changesCategory);
				
				// Store MenuItem Availability
				changesAvailability = availButton.getSelection();
				currentMenuItem.setAvailability(changesAvailability);
				
				// Store MenuItem Description
				str = textDescr.getText().trim();
				currentMenuItem.setDescription(str);
				
				// Store MenuItem Price
				double d;
				try {
					d = Double.parseDouble(textPrice.getText());
					//d = Double.parseDouble(str);
				} catch (Exception e) {
					d = 0;
					//displayError(resLunchy.getString("Nan_error"));
				}
				currentMenuItem.setPrice(d);
				
				setState(ITEM_CHANGED);
				//System.out.println("ok");
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
				setState(CANCELED);
				System.out.println("cancel");
				shell.close();
			}
		});
		
		shell.setDefaultButton(btOK);
	}
	public void setEditableMenuItem(MenuItem mi) {
		currentMenuItem = mi;
	}
	
	
	public String getTitle() {
		return shell.getText();
	}
	
	public void setLabels(String[] labels) {
		this.columnLabels = labels;
	}
	public void setTitle(String title) {
		shell.setText(title);
	}
	
	private void displayError(String msg) {
		MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
		box.setMessage(msg);
		box.open();
	}
}

