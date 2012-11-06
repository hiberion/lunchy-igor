package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralOrder {
	private int ID;
	private String orderDate;
	
	private static int curID = 1;
	
	public GeneralOrder() {	
	}
	
	public GeneralOrder(int id, String orderDate) {
		ID = id;
		this.orderDate = orderDate;
	}
	
	public GeneralOrder(int id, Date orderDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.orderDate = sdf.format(orderDate);
		this.ID = id;
	}
	
	public GeneralOrder(String orderDate) {
		ID = newId();
		this.orderDate = orderDate;
	}
	
	public Date getOrderDateForm2() {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(orderDate);
		} catch (ParseException e) {
			System.out.println("getOrderDateForm2 Error");
		}
		return date;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(ID);
		result[1] = orderDate;
		return result;		
	}
	
	public String toString() {
		return String.valueOf(ID)+" "+orderDate;
	}
	
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}

}
