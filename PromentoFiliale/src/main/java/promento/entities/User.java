package promento.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role")
public class User implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName;
	private String lastName;
    private String photoFileName;
    private String phone;
    private String email;
    
    @JsonIgnore
    private String username;
    
    @JsonIgnore
    private String password;
    
    @JsonIgnore
    private Boolean enable;

	public User(String firstName, String lastName, String photoFileName, String phone, String email, String username,
			String password, Boolean enabled) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoFileName = photoFileName;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enable = enabled;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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




	public Boolean getEnable() {
		return enable;
	}




	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

    
	
	
	
	
}
