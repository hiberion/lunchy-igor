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
@Table(name = "worker")
public class Worker {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany
	@JoinColumn(name="worker_id")
	private Set<PersonalOrder> personalOrders = new HashSet<>();
	
	private static int curID = 1;
	
	public Worker() {  }
	
	public Worker(int id, String fullName) {
		this.id = id;
		this.name = fullName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<PersonalOrder> getPersonalOrders() {
		return personalOrders;
	}
	
	public void setPersonalOrders(Set<PersonalOrder> value) {
		this.personalOrders = value;
	}
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(id);
		result[1] = name;
		return result;		
	}
	
	public String toString() {
		String[] p = toStringArray(); // in-between result
		return p[0]+" "+p[1];
	}
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
	
}
