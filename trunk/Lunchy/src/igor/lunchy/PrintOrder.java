package igor.lunchy;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.printing.*;
import org.eclipse.swt.widgets.Shell;

import entities.PersonalOrder;

public class PrintOrder {
	
	public static boolean PrintGeneralOrder(Shell shell, ArrayList<String[]> data) {
		
		PrintDialog dialog = new PrintDialog (shell, SWT.NONE);
		PrinterData result = dialog.open ();
		
		if (result == null) {
			System.out.println("Warning: No printer.");
			return false;
		}
		Printer printer = new Printer(result);
		if (printer.startJob("General Order")) {
			Color black = printer.getSystemColor(SWT.COLOR_BLACK);
			Color white = printer.getSystemColor(SWT.COLOR_WHITE);
			//Color red = printer.getSystemColor(SWT.COLOR_RED);
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			Point dpi = printer.getDPI();
			int leftMargin = dpi.x + trim.x; // one inch from left side of paper
			if (leftMargin < 0) leftMargin = -trim.x;  // make sure to print on the printable area 
			int topMargin = dpi.y / 2 + trim.y; // one-half inch from top edge of paper
			if (topMargin < 0) topMargin = -trim.y;  // make sure to print on the printable area 
			GC gc = new GC(printer);
			if (printer.startPage()) {
				gc.setBackground(white);
				gc.setForeground(black);
				String testString = "General Order:";
				@SuppressWarnings("unused")
				Point extent = gc.stringExtent(testString);
				gc.drawString(testString, leftMargin, topMargin);
				
				int i = 130;
				for (String[] str : data) {
					String testString2 = str[0] + " " + str[1] + " " + str[2] + " " + str[3] + " " + str[4] +
							" " + str[5];
					@SuppressWarnings("unused")
					Point extent2 = gc.stringExtent(testString2);
					gc.drawString(testString2, leftMargin, topMargin + i);
					i = i + 90;
				}
				
				//gc.setForeground(red);
				//gc.drawRectangle(leftMargin, topMargin, extent.x, extent.y);
				printer.endPage();
			}
			gc.dispose();
			printer.endJob();
		}
		
		return true;
	}
	
public static boolean PrintPersonalOrders(Shell shell) {
		
		PrintDialog dialog = new PrintDialog (shell, SWT.NONE);
		PrinterData result = dialog.open ();
		
		if (result == null) {
			System.out.println("Warning: No printer.");
			return false;
		}
		Printer printer = new Printer(result);
		if (printer.startJob("Personal Orders")) {
			Color black = printer.getSystemColor(SWT.COLOR_BLACK);
			Color white = printer.getSystemColor(SWT.COLOR_WHITE);
			//Color red = printer.getSystemColor(SWT.COLOR_RED);
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			Point dpi = printer.getDPI();
			int leftMargin = dpi.x + trim.x; // one inch from left side of paper
			if (leftMargin < 0) leftMargin = -trim.x;  // make sure to print on the printable area 
			int topMargin = dpi.y / 2 + trim.y; // one-half inch from top edge of paper
			if (topMargin < 0) topMargin = -trim.y;  // make sure to print on the printable area 
			GC gc = new GC(printer);
			if (printer.startPage()) {
				gc.setBackground(white);
				gc.setForeground(black);
				String testString = "Personal Orders";
				@SuppressWarnings("unused")
				Point extent = gc.stringExtent(testString);
				gc.drawString(testString, leftMargin, topMargin);
				int i = 130;
				for (PersonalOrder po : LunchyMain.personalOrderDAO.getAllPersonalOrder()) {
					String testString2 = po.getID() + " " + LunchyMain.workerDAO.getWorkerByID(po.getWorkerID()).getName() +
							" " + po.getGeneralOrderID();
					@SuppressWarnings("unused")
					Point extent2 = gc.stringExtent(testString2);
					gc.drawString(testString2, leftMargin, topMargin + i);
					i = i + 90;
				}
				
				//gc.setForeground(red);
				//gc.drawRectangle(leftMargin, topMargin, extent.x, extent.y);
				printer.endPage();
			}
			gc.dispose();
			printer.endJob();
		}
		
		return true;
	}

}
