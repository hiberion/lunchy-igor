package entities;

import java.util.ArrayList;
import igor.lunchy.Column;

public class MenuItem {
	@Column(name = "ID")
	private int ID;
	
	@Column(name = "Name")
	private String name = "";
	
	@Column(name = "Category")
	private int category = 0;
	
	@Column(name = "Description")
	private String descript = "";
	
	@Column(name = "Price")
	private double price = 0.0;
	
	@Column(name = "Availability")
	private boolean avail = true;
	
	public static int curID = 1;
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescr() {
		return descript;
	}
	
	public int getCategory() {
		return category;
	}
	
	public boolean getAvail() {
		return avail;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setDescr(String descript) {
		this.descript = descript;
	}
	
	public void setAvail(boolean av) {
		this.avail = av;
	}
	
	public void setCategory(int cat) {
		this.category = cat;
	}
	
	
	public MenuItem(int id, String name, int cat, String desc, double price,  boolean avail) {
		this.ID = id;
		this.name = new String(name);
		this.category = cat;
		this.descript = new String(desc);
		this.price = price;
		this.avail = avail;	
	}
	
	static public ArrayList<MenuItem> ByName(ArrayList<MenuItem> inAr, String name) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getName().equals(name))
				result.add(mi);
		}
		return result;
	}
	
	static public ArrayList<MenuItem> FindByCat(ArrayList<MenuItem> inAr, int cat) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getCategory() == cat)
				result.add(mi);
		}
		return result;
	}
	
	static public ArrayList<MenuItem> FindByAvail(ArrayList<MenuItem> inAr, boolean av) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getAvail() == av)
				result.add(mi);
		}
		return result;
	}
	
	public String[] ToStrArr() {
		String[] result = new String[6];
		result[0] = String.valueOf(ID);
		result[1] = new String(name);
		result[2] = String.valueOf(category);
		result[3] = new String(descript);
		result[4] = String.valueOf(price);
		result[5] = avail ? "Y" : "N";
		return result;		
	}
	
	public String ToStr() {
		String result;
		result = ToStrArr()[0] + " " + ToStrArr()[1] + " " + ToStrArr()[2] + " " +
				ToStrArr()[3] + " " + ToStrArr()[4] + " " + ToStrArr()[5];
		return result;
	}
	
	public MenuItem() {
		super();
		ID = curID;
		name = "Name";
		category = 0;
		descript = "Description";
		price = 0.0;
		avail = true;
	}

}
