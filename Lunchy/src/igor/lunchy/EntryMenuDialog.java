package igor.lunchy;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class EntryMenuDialog {
	Shell shell;
	String[] labels =
		{   "Entry ID: ",
			"Entry name: ",
			"Entry description: ",
			"Entry price: ",
			"Entry category: ",
			"Entry availability: "
			};
	String[] values;
	
	public EntryMenuDialog(Shell parent) {
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
			values = new String[labels.length];
		
		for (int i = 0; i < labels.length; i++) {
			Label label = new Label(shell, SWT.RIGHT);
			label.setText(labels[i]);	
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
		
		Label statusCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		statusCaption.setText("Current menu status: ");
		statusCaption.setLayoutData(gridData);
		
		Label statusText = new Label(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		statusText.setText("Current menu status");
		statusText.setLayoutData(gridData);
		
		Label validCaption = new Label(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validCaption.setText("Menu valid date: ");
		validCaption.setLayoutData(gridData);
		
		DateTime validDate = new DateTime(shell, SWT.NONE);
		gridData = new GridData();
		gridData.minimumWidth = 40;
		validDate.setLayoutData(gridData);
		
		Button checkBoxUpdate = new Button(shell, SWT.CHECK | SWT.RIGHT_TO_LEFT);
		gridData = new GridData();
		gridData.horizontalIndent = 30;
		gridData.horizontalSpan = 2;
		checkBoxUpdate.setText("Menu updated: ");
		checkBoxUpdate.setLayoutData(gridData);
	}
	
	public String[] getLabels() {
		return labels;
	}
	public String getTitle() {
		return shell.getText();
	}
	
	public String[] getValues() {
		return values;
	}
	
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public void setTitle(String title) {
		shell.setText(title);
	}
	
	public void setValues(String[] itemInfo) {
		if (labels == null) return;
		
		if (values == null)
			values = new String[labels.length];

		int numItems = Math.min(values.length, itemInfo.length);
		for(int i = 0; i < numItems; i++) {
			values[i] = itemInfo[i];
		}	
	}
}

