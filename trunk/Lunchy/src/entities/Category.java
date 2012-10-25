package entities;

public class Category {
	private int ID;
	private String name;
	
	public static int curID = 1;
	
	public Category(int id, String name) {
		ID = id;
		this.name = new String(name);
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] ToStrArr() {
		String[] result = new String[2];
		result[0] = String.valueOf(ID);
		result[1] = new String(name);
		return result;		
	}
	
	public String ToStr() {
		String result;
		result = ToStrArr()[0] + " " + ToStrArr()[1];
		return result;
	}
	
}