package igor.lunchy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LunchyOptions {
	private String fileName = "LunchyOptions.xml";
	
	public Properties loadOptions() {
		
		Properties defauiltOptions = new Properties();
		defauiltOptions.put("CompanyName", "Company");
		defauiltOptions.put("CompanyPhone", "Phone");
		defauiltOptions.put("MenuExpirationTime", "48");
		defauiltOptions.put("DataSource", "TextFile");
		defauiltOptions.put("DiscountSumm", "1000");
		defauiltOptions.put("DiscountSummDiff", "100");
		defauiltOptions.put("DiscountPercent", "15");
		defauiltOptions.put("LastTimeSendOrder", "16:00");
		defauiltOptions.put("LastTimePreparationOrder", "15:00");
		defauiltOptions.put("SingleOrderMaxSum", "200");
		defauiltOptions.put("Warnings", "No");
		defauiltOptions.put("Language", "English");
		defauiltOptions.put("DBPass", "sipher");
		
		Properties result = new Properties(defauiltOptions);
		FileInputStream fin = null;
		// Trying open options file
		try {
			fin = new FileInputStream(fileName);
			try {
				result.loadFromXML(fin);
				fin.close();
			} catch (IOException e) {
				System.out.println("Error load options file");
			}
			/// File not exist
		} catch (FileNotFoundException e) {
			System.out.println("File not exist");	
		}
		
		return result;
	}
	
	public boolean saveOptions(Properties options) {
		
		FileOutputStream fout = null;
		boolean result = false;
		
		try {
			fout = new FileOutputStream(fileName);
			try {
				options.storeToXML(fout, "LunchyOptions");
				fout.close();
				result = true;
			} catch (IOException e1) {
				System.out.println("Error saving options");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
		}
		
		return result;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
}
