package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personal_order")
public class PersonalOrder {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "worker_id")
	private int workerId;
	
	@Column(name = "gen_order_id")
	private int generalOrderId;
	//private double orderSum;
	
	private static int curID = 0;
	
	public PersonalOrder() {  }
	
	public PersonalOrder(int id, int wID, int goID) {
		this.id = id;
		workerId = wID;
		generalOrderId = goID;
		//orderSum = 0.0;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getWorkerId() {
		return workerId;
	}
	
	public void setWorkerrId(int workerId) {
		this.workerId = workerId;
	}
	
	public int getGeneralOrderId() {
		return generalOrderId;
	}
	
	public void setGeneralOrderId(int generalOrderId) {
		this.generalOrderId = generalOrderId;
	}
	
	public synchronized static int newId() {
		return curID++;
	}
	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
	
	
	public String toString() {
		return id+" "+workerId+" "+generalOrderId;
	}
	
	public String[] toStringArray() {
		String[] result = new String[3];
		result[0] = String.valueOf(id);
		result[1] = String.valueOf(workerId);
		result[2] = String.valueOf(generalOrderId);
		return result;		
	}
}