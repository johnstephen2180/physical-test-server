package com.loankim.examonline.om;

public class Role {
	private int id;
	private String role;
	private String name;

	public Role() {
	}

	public Role(int id, String role, String name) {
		this.id = id;
		this.role = role;
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
