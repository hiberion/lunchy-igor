package entities;

public class MenuItemPersonalOrder {
	private int menuItemID;
	private int personalOrderID;
	private int itemCount;
		
	public MenuItemPersonalOrder() {
	}
	
	public MenuItemPersonalOrder(int menuItemID, int personalOrderID, int itemCount) {
		this.menuItemID = menuItemID;
		this.personalOrderID = personalOrderID;
		this.itemCount = itemCount;
	}
	
	public int getMenuItemID() {
		return menuItemID;
	}
	
	public int getPersonalOrderID() {
		return personalOrderID;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public void setMenuItemID(int value) {
		menuItemID = value;
	}
	
	public void setPersonalOrderID(int value) {
		personalOrderID = value;
	}
	
	public void setItemCount(int value) {
		itemCount = value;
	}
	
	public String toString() {
		return menuItemID + " " + personalOrderID + " " + itemCount;
	}

}