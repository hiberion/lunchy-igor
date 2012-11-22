package entities;

import java.io.Serializable;

public class MenuItemPersonalOrderID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int menuItemId;
	int personalOrderId;

    public boolean equals(Object instance) {
    	if (instance == null)
            return false;

        if (!(instance instanceof MenuItemPersonalOrderID))
            return false;

        final MenuItemPersonalOrderID other = (MenuItemPersonalOrderID) instance;
        if (menuItemId != other.menuItemId)
            return false;

        if (personalOrderId != other.personalOrderId)
            return false;

        return true;
    }

    public int hashCode(){
    	int hash = 7;
        hash = 47 * hash + (this.menuItemId != 0 ? this.menuItemId : 0);
        hash = 47 * hash + (this.personalOrderId != 0 ? this.personalOrderId : 0);
        return hash;
    }
}
