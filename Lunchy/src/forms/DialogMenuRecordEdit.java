package forms;

//import java.lang.reflect.Field;

import igor.lunchy.LunchyMain;

import java.util.ResourceBundle;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import entities.Category;

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
	String[] values;
	
	public DialogMenuRecordEdit(Shell parent) {
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setText("Menu Entry Editor");
		shell.setImage(new Image(shell.getDisplay(), "icon2.jpg"));
	}
	
	private void addTextListener(final Text text) {
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e){
				Integer index = (Integer)(text.getData("index"));
				values[index.intValue()] = text.getText();
			}
		});
	}
	
	public String[] open() {
		//createTextWidgets();
		//createControlButtons();
		//shell.pack();
		createWidgets();
		//setTitle("New menu entry");
		
		shell.pack();
		shell.open();
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		
		return values;
	}
	
	public void createWidgets() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		shell.setLayout(layout);
		GridData gridData = new GridData();
		
		if (values == null)
			values = new String[columnLabels.length];
		
		//MenuEntry entry = null;
		
		//Field[] fields = entry.getClass().getDeclaredFields();
		//System.out.println(columnName);		
		//for (Field field: entry.getClass().getDeclaredFields()) {
			//Column column = field.getAnnotation(Column.class);
			//String columnName = column != null ? column.name() : field.getName();
		
			/*
			Label label = new Label(shell, SWT.RIGHT);
			label.setText(columnName);	
			Text text = new Text(shell, SWT.BORDER);
			gridData = new GridData();
			gridData.widthHint = 400;
			text.setLayoutData(gridData);
			text.setText(field.getName());
			text.setText(field.get(entry).toString());
			addTextListener(text);
			*/
		//}
		
		
		for (int i = 0; i < columnLabels.length; i++) {
			if (i == 2) {
				Label label = new Label(shell, SWT.RIGHT);
				label.setText(columnLabels[i]);
				
				Combo categoryCombo = new Combo(shell, SWT.NONE);
				String[] comboData = new String[LunchyMain.categoryList.size()];
				for (Category cat: LunchyMain.categoryList) {
					comboData[cat.getID()] = cat.getName();
				}
				categoryCombo.setItems(comboData);
				if (values[0] != null)
					categoryCombo.select(LunchyMain.menuList.get(Integer.parseInt(values[0])).getCategory());
				continue;
			}
			
			if (i == 5) {
				Label label = new Label(shell, SWT.RIGHT);
				label.setText(columnLabels[i]);
				
				Button availButton = new Button(shell, SWT.CHECK);
				availButton.setSelection(LunchyMain.menuList.get(Integer.parseInt(values[0])-1).getAvail());
				//if (values[0] != null)
					//categoryCombo.select(LunchyMain.menuList.get(Integer.parseInt(values[0])).getCategory());
				continue;
			}
			
			if (i == 0) {
				Label label = new Label(shell, SWT.RIGHT);
				label.setText(columnLabels[i]);
				
				Text text = new Text(shell, SWT.BORDER);
				gridData = new GridData();
				gridData.widthHint = 400;
				text.setLayoutData(gridData);
				text.setEnabled(false);
				if (values[i] != null) {
					text.setText(values[i]);
				}
				text.setData("index", new Integer(i));
				addTextListener(text);
				continue;
			}
			
			Label label = new Label(shell, SWT.RIGHT);
			label.setText(columnLabels[i]);	
			Text text = new Text(shell, SWT.BORDER);
			gridData = new GridData();
			gridData.widthHint = 400;
			text.setLayoutData(gridData);
			if (values[i] != null) {
				text.setText(values[i]);
			}
			text.setData("index", new Integer(i));
			addTextListener(text);
		}
		
		
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
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DialogMenuRecordEdit.this.shell.close();
			}
		});
		
		Button btCancel = new Button(shell, SWT.PUSH);
		gridData = new GridData();
		//gridData.horizontalIndent = 30;
		//gridData.horizontalSpan = 2;
		btCancel.setText("Cancel");
		btCancel.setLayoutData(gridData);
	}
	
	public String[] getLabels() {
		return columnLabels;
	}
	public String getTitle() {
		return shell.getText();
	}
	
	public String[] getValues() {
		return values;
	}
	
	public void setLabels(String[] labels) {
		this.columnLabels = labels;
	}
	public void setTitle(String title) {
		shell.setText(title);
	}
	
	public void setValues(String[] itemInfo) {
		if (columnLabels == null) return;
		
		if (values == null)
			values = new String[columnLabels.length];

		int numItems = Math.min(values.length, itemInfo.length);
		for(int i = 0; i < numItems; i++) {
			values[i] = itemInfo[i];
		}	
	}
}
