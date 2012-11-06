// Service class for parse text files
// Content static functions for converting text files to String[][]
// and converting String[][] to text file

package igor.lunchy;

import java.io.*;

public class ParseTextFile {
	
	// Service internal function
	// Encoding string array to single string ( String[] -> String )
	private static String encodeLine(String[] tableItems, String delimiter) {
		
		String line = "";
		for (int i = 0; i < tableItems.length - 1; i++) {
			line += tableItems[i] + delimiter;
		}
		line += tableItems[tableItems.length - 1] + "\r\n";
		
		return line;
	}
	
	// Service internal function
	// Decoding single string with delimiters to string array ( String -> String[] )
	private static String[] decodeLine(String line, String delimiter, int rowCount) {
		
		if(line == null)
			return null;
		
		String[] parsedLine = new String[rowCount];
		for(int i = 0; i < parsedLine.length - 1; i++) {
			int index = line.indexOf(delimiter);
			if (index > -1) {
				parsedLine[i] = line.substring(0, index);
				line = line.substring(index + delimiter.length(), line.length());
			} else {
				return null;
			}
		}
		
		if (line.indexOf(delimiter) != -1)
			return null;
		parsedLine[parsedLine.length - 1] = line;
		return parsedLine;
	}
	
	public static String[][] getStringTable(String filename, String delimiter, int rowCount) {

		if(filename == null)
			return null;
		
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("File not exist"); 
			return null;
		}
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String[] data = new String[0];
		try {
			fileReader = new FileReader(file.getAbsolutePath());
			bufferedReader = new BufferedReader(fileReader);
			String nextLine = bufferedReader.readLine();
			while (nextLine != null){
				String[] newData = new String[data.length + 1];
				System.arraycopy(data, 0, newData, 0, data.length);
				newData[data.length] = nextLine;
				data = newData;
				nextLine = bufferedReader.readLine();
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (IOException e ) {
			System.out.println("IO read error");
			return null;
		} finally {	
			if(fileReader != null) {
				try {
					fileReader.close();
				} catch(IOException e) {
					System.out.println("IO close error");
					return null;
				}
			}
		}
		
		String[][] tableInfo = new String[data.length][rowCount];
		int writeIndex = 0;
		for (int i = 0; i < data.length; i++) {
			String[] line = decodeLine(data[i], delimiter, rowCount);
			if (line != null) tableInfo[writeIndex++] = line;
		}
		if (writeIndex != data.length) {
			String[][] result = new String[writeIndex][rowCount];
			System.arraycopy(tableInfo, 0, result, 0, writeIndex);
			tableInfo = result;
		}
		return tableInfo;
	}
	
	public static boolean putStringTableToFile(String filename, String[][] data) {
		
		if(filename == null)
			return false;
				
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("File not exist"); 
			return false;
		}
		
		String[] lines = new String[data.length];
		for(int i = 0; i < data.length; i++) {
			lines[i] = encodeLine(data[i],"_");
		}
			
		FileWriter fileWriter = null;
		try { 
			fileWriter = new FileWriter(file.getAbsolutePath(), false);
			for (int i = 0; i < lines.length; i++) {
				fileWriter.write(lines[i]);
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		} catch(IOException e ) {
			System.out.println("IO write error");
			return false;
		} finally {
			
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch(IOException e) {
					System.out.println("IO close error");
					return false;
				}
			}
		}
		
		return true;
	}
}
