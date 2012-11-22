package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	private static int curID = 1;
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany  //(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Set<MenuItem> menuItems = new HashSet<>();
	
	public Category() {	}
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Category(String name) {
		id = newId();
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(id);
		result[1] = name;
		return result;		
	}
	
	public String toString() {
		return String.valueOf(id)+" "+name;
	}
	
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
}
