package com.rmit.sept.bk_loginservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Collection;

@Entity
public class User implements UserDetails {
    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Users full name
    @Size(min = 6, max = 50, message = "Please enter 6 to 50 characters")
    @NotBlank(message = "Please enter your full name")
    private String fullname;
    // Users address
    @Size(min = 10, max = 40, message = "Please enter 10 to 40 characters")
    @NotBlank(message = "Please enter your address")
    private String address;
    // users username in email format
    @Email(message = "Please enter your email address (username)")
    @NotBlank(message = "Email is required for a login username")
    @Column(unique = true)
    private String username;
    // users phone number
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a 10 digit phone number")
    @NotBlank(message = "Please enter your phone number")
    private String phonenumber;
    // users ABN (optional)
    @Pattern(regexp = "^\\d{11}$", message = "Please enter an 11 digit ABN")
    private String abn;
    // Account type of user
    @NotBlank(message = "Please enter your account type")
    private String accounttype;
    // users password
    @NotBlank(message = "Please enter a 6 character password")
    private String password;
    // users unique login security token
    @Column(unique = true)
    private String token;
    @NotBlank(message = "User must have a registration status")
    // Pending for a shop owner, otherwise Registered
    private String registerstatus;
    // confirm password field
    @Transient
    private String confirmpassword;
    // when user was created
    private Date createat;
    // when user was updated
    private Date updateat;

    // Getters and setters

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }
  
    public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getToken() { 
		return token;
	}

    public void setToken(String token) { 
    	this.token = token; 
    }

    public String getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(String registerstatus) {
		this.registerstatus = registerstatus;
	}

	public Date getCreateat() {
		return createat;
	}

	public void setCreateat(Date createat) {
		this.createat = createat;
	}

	public Date getUpdateat() {
		return updateat;
	}

	public void setUpdateat(Date updateat) {
		this.updateat = updateat;
	}

	@PrePersist
    protected void onCreate() {
        this.createat = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateat = new Date();
    }

    /*
    UserDetails interface methods
     */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

