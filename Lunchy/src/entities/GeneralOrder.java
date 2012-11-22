package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "general_order")
public class GeneralOrder {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "date")
	private String orderDate;
	
	@OneToMany
	@JoinColumn(name="gen_order_id")
	private Set<PersonalOrder> personalOrders = new HashSet<>();
	
	private static int curID = 1;
	
	public GeneralOrder() {	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public Set<PersonalOrder> getPersonalOrders() {
		return personalOrders;
	}
	
	public void setPersonalOrders(Set<PersonalOrder> value) {
		this.personalOrders = value;
	}
	
	public GeneralOrder(int id, String orderDate) {
		this.id = id;
		this.orderDate = orderDate;
	}
	
	public GeneralOrder(int id, Date orderDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.orderDate = sdf.format(orderDate);
		this.id = id;
	}
	
	public GeneralOrder(String orderDate) {
		id = newId();
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
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(id);
		result[1] = orderDate;
		return result;		
	}
	
	public String toString() {
		return String.valueOf(id)+" "+orderDate;
	}
	
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}

}
