package com.jlabs.wm.spockdemo.dto;

public class User {
	private Long id;
	private String userName;
	private String email;

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof User)) {
			return false;
		}

		User otherUser = (User)other;
		return userName != null && userName.equals(otherUser.getUserName());
	}

	@Override
	public int hashCode() {
		return userName != null ? userName.hashCode() : 777;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
