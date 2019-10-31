package model;

import java.sql.Timestamp;
import java.util.Date;

public class Client implements Comparable<Client>{
	
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private String country;
	private int age;
	private Date regDate;
	private Timestamp createdTs;
	private Timestamp updatedTs;
	
	public Client() {
	}

	public Client(int id) {
		this.id = id;
	}
	
	public Client(int id, String firstName, String lastName, String gender, String country, int age, Date regDate,
			Timestamp createdTs, Timestamp updatedTs) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.country = country;
		this.age = age;
		this.regDate = regDate;
		this.createdTs = createdTs;
		this.updatedTs = updatedTs;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Timestamp getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}
	public Timestamp getUpdatedTs() {
		return updatedTs;
	}
	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", country=" + country + ", age=" + age + ", regDate=" + regDate + ", createdTs=" + createdTs
				+ ", updatedTs=" + updatedTs + "]";
	}

	@Override
	public int compareTo(Client arg0) {
		// TODO Auto-generated method stub
		return this.lastName.compareTo(arg0.getLastName());
	}

	
	
}
