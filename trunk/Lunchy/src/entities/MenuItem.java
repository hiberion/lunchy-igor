package entities;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "menuitem")
public class MenuItem {
	private static int curID = 1;
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "availability")
	private boolean availability;
	
	public MenuItem() { }
	
	public int getId() {
		return id;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getCategory() {
		return categoryId;
	}
	
	public boolean getAvailability() {
		return availability;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	public void setCategory(int category) {
		this.categoryId = category;
	}
	
	public MenuItem(int id, String name, int category, String desc, double price,  boolean avail) {
		this.id = id;
		this.name = name;
		this.categoryId = category;
		this.description = desc;
		this.price = price;
		this.availability = avail;	
	}
	
	public MenuItem(MenuItem menuItem) {
		id = menuItem.id;
		name = menuItem.name;
		categoryId = menuItem.categoryId;
		description = menuItem.description;
		price = menuItem.price;
		availability = menuItem.availability;
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
			if (mi.getAvailability() == av)
				result.add(mi);
		}
		return result;
	}
	
	public String[] toStringArray() {
		String[] result = new String[6];
		result[0] = String.valueOf(id);
		result[1] = name;
		result[2] = String.valueOf(categoryId);
		result[3] = description;
		result[4] = String.valueOf(price);
		result[5] = availability ? "Y" : "N";
		return result;		
	}
	
	public String toString() {
		String[] p = toStringArray(); // in-between result
		return p[0]+" "+p[1]+" "+p[2]+" "+p[3]+" "+p[4]+" "+p[5];
	}
	
	/*public MenuItem() {
		super();
		id = curID;
		name = "Name";
		category = 0;
		description = "Description";
		price = 0.0;
		availability = true;
	}*/
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}

}
