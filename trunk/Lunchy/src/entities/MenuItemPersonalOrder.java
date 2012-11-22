package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "menuitem_personalorder")
@IdClass(MenuItemPersonalOrderID.class)
public class MenuItemPersonalOrder {
		
	@Id
	@Column(name = "menuitem_id")
	private int menuItemId;
	
	@Id
	@Column(name = "personalorder_id")
	private int personalOrderId;
	
	@Column(name = "count")
	private int itemCount;
		
	public MenuItemPersonalOrder() {  }
	
	public MenuItemPersonalOrder(int menuItemID, int personalOrderID, int itemCount) {
		this.menuItemId = menuItemID;
		this.personalOrderId = personalOrderID;
		this.itemCount = itemCount;
	}
	
	public int getMenuItemId() {
		return menuItemId;
	}
	
	public int getPersonalOrderId() {
		return personalOrderId;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public void setMenuItemId(int value) {
		menuItemId = value;
	}
	
	public void setPersonalOrderId(int value) {
		personalOrderId = value;
	}
	
	public void setItemCount(int value) {
		itemCount = value;
	}
	
	public String toString() {
		return menuItemId + " " + personalOrderId + " " + itemCount;
	}
	
	public String[] toStringArray() {
		String[] result = new String[3];
		result[0] = String.valueOf(menuItemId);
		result[1] = String.valueOf(personalOrderId);;
		result[2] = String.valueOf(itemCount);
		return result;		
	}

}
