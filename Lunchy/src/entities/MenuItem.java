package entities;

import java.util.ArrayList;
import igor.lunchy.Column;

public class MenuItem {
	@Column(name = "ID")
	private int ID;
	private String name = "";
	private int category = 0;
	private String descript = "";
	private double price = 0.0;
	private boolean avail = true;

	private static int curID = 1;
	
	
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
		this.name = name;
		this.category = cat;
		this.descript = desc;
		this.price = price;
		this.avail = avail;	
	}
	
	static public ArrayList<MenuItem> byName(ArrayList<MenuItem> inAr, String name) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getName().equals(name))
				result.add(mi);
		}
		return result;
	}
	
	static public ArrayList<MenuItem> findByCat(ArrayList<MenuItem> inAr, int cat) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getCategory() == cat)
				result.add(mi);
		}
		return result;
	}
	
	static public ArrayList<MenuItem> findByAvail(ArrayList<MenuItem> inAr, boolean av) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: inAr) {
			if (mi.getAvail() == av)
				result.add(mi);
		}
		return result;
	}
	
	public String[] toStringArray() {
		String[] result = new String[6];
		result[0] = String.valueOf(ID);
		result[1] = name;
		result[2] = String.valueOf(category);
		result[3] = descript;
		result[4] = String.valueOf(price);
		result[5] = avail ? "Y" : "N";
		return result;		
	}
	
	public String toString() {
		String[] p = toStringArray(); // in-between result
		return p[0]+" "+p[1]+" "+p[2]+" "+p[3]+" "+p[4]+" "+p[5];
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
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}

}
