package com.loankim.examonline.om;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account")
public class Account {
	@Id
	private long id;
	@Indexed
	private String username;
	private String password;
	private String fullName;
	private Set<Role> roles;
	private long createTime;
	private boolean isActive;
	private String provider;
	private long clientId;
	private String avatar;
	@Transient
	private String token;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getRoleString() {
		if (roles == null || roles.size() <= 0)
			return "ROLE_GUEST";

		return StringUtils.join(roles.stream().map(Role::getRole).collect(Collectors.toList()), ",");
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public long getClientId() {
		return clientId;
	}


	public void setClientId(long clientId) {
		this.clientId = clientId;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	// role nen la Enum vi role nay ko bi doi
	public void updateRoles(List<String> roleList) {
		HashSet<Role> roles = new HashSet<>();
		for (String role : roleList) {
			if (role.equals("ROLE_GUEST")) {
				roles.add(new Role(2, "ROLE_GUEST", "Khach"));
			} else if (role.equals("ROLE_USER")) {
				roles.add(new Role(1, "ROLE_USER", "Hoc Sinh"));
			} else if (role.equals("ROLE_ADMIN")) {
				roles.add(new Role(0, "ROLE_ADMIN", "Giao Vien"));

			}
		}

		setRoles(roles);
	}

}
