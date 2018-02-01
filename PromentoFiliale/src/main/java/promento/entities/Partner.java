package promento.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@DiscriminatorValue("PARTNER")
public class Partner extends User{


    

    @JsonIgnore
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
	private Collection<Mentoring> mentors ;

	public Partner() {
		super();
	}

	public Partner(String firstName, String lastName, String photoFileName, String phone, String email, String username,
			String password, Boolean enabled) {
		super(firstName, lastName, photoFileName, phone, email, username, password, enabled);
	}

	public Collection<Mentoring> getMentors() {
		return mentors;
	}

	public void setMentors(Collection<Mentoring> mentors) {
		this.mentors = mentors;
	}
    
    
	
	
	
	
	
}
