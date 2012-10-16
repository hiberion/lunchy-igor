package igor.lunchy;

public class MenuEnrty {
	int ID;
	String name = "";
	int category = 0;
	String descript = "";
	double price = 0.0;
	boolean avail = true;
	
	public MenuEnrty() {
		super();
		name = "Name";
		category = 0;
		descript = "Description";
		price = 0.0;
		avail = true;
	}
	
	public MenuEnrty(int ID, String name, int category,
			String descript, double price, boolean avail) {
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.descript = descript;
		this.price = price;
		this.avail = avail;		
	}
	
	static public MenuEnrty[] getMenuEntries(String filename) {
		String[][] stringTable = ParseTextFile.getStringTable("menu.txt", "_", 6);
		int entriesCount = stringTable.length;
		MenuEnrty[] result = new MenuEnrty[entriesCount];
		//MenuEnrty[] tempRes;
		
		int tempID;
		String tempName;
		int tempCategory;
		String tempDescript;
		double tempPrice;
		boolean tempAvail = true;
		
		for (int i = 0; i < entriesCount; i++) {
			tempID = Integer.parseInt(stringTable[i][0]);
			tempName = stringTable[i][1];
			tempCategory = Integer.parseInt(stringTable[i][2]);
			tempDescript = stringTable[i][3];
			tempPrice = Double.parseDouble(stringTable[i][4]);
			tempAvail = (stringTable[i][5] == "Y") ? true : false;
			result[i] = new MenuEnrty(tempID, tempName, tempCategory,
					tempDescript, tempPrice, tempAvail);			
		}
		
		return result;
	}

}
