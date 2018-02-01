package promento.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Company implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	
	@NotEmpty
	private String companyName;
	
	
	private int turnOver ;
	private String legalForm;
	
	
	private String generalDirector;
	
	
    @Temporal(TemporalType.DATE)
     @JsonFormat(pattern="dd/MM/yyyy")
	 @DateTimeFormat(pattern = "dd/MM/yyyy")	
	 private Date dateCreation;	
	 
	 
	private String logoFileName;
//	private String actionnariat;
	private int  RRI ;//rate of return on investment
	private int horizon;
//	private String mentors;
//	private String mentorRemunartionRate;
	private String comment;
	
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private Collection<Indicator> indicators ;//budget & perfermance(turnOver , charges real and estimated)
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private Collection<Mentoring> mentors ;

	
	
	
	public Collection<Mentoring> getMentors() {
		return mentors;
	}



	public void setMentors(Collection<Mentoring> mentors) {
		this.mentors = mentors;
	}



	public Collection<Indicator> getIndicators() {
		return indicators;
	}



	public void setIndicators(Collection<Indicator> indicators) {
		this.indicators = indicators;
	}



	public Company(String companyName, int turnOver,String legalForm, String generalDirector, Date dateCreation, String logo,
			int rRI, int horizon, String comment) {
		super();
		this.companyName = companyName;
		this.legalForm = legalForm;
		this.generalDirector = generalDirector;
		this.dateCreation = dateCreation;
		this.logoFileName = logo;
		RRI = rRI;
		this.horizon = horizon;
		this.comment = comment;
		this.turnOver = turnOver;
	}
	
	

	public Integer getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(int turnOver) {
		this.turnOver = turnOver;
	}
	public Company() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLegalForm() {
		return legalForm;
	}
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}
	public String getGeneralDirector() {
		return generalDirector;
	}
	public void setGeneralDirector(String generalDirector) {
		this.generalDirector = generalDirector;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getlogoFileName() {
		return logoFileName;
	}
	public void setlogoFileName(String logo) {
		this.logoFileName = logo;
	}
	public int getRRI() {
		return RRI;
	}
	public void setRRI(int rRI) {
		RRI = rRI;
	}
	
	
	public int getHorizon() {
		return horizon;
	}
	public void setHorizon(int horizon) {
		this.horizon = horizon;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


	


	
	
	
}
