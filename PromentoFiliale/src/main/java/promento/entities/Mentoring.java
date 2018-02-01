package promento.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Mentoring implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	
    @JsonIgnore
    @ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
    @ManyToOne
	@JoinColumn(name="mentor_id")
	private Partner mentor;
	
	
	
     @Temporal(TemporalType.DATE)
     @JsonFormat(pattern="yyyy-MM-dd")
	 @DateTimeFormat(pattern = "dd/MM/yyyy")
	 private Date dateBegin;	
	 
	
 	
     @Temporal(TemporalType.DATE)
     @JsonFormat(pattern="yyyy-MM-dd")
	 @DateTimeFormat(pattern = "dd/MM/yyyy")	
	 private Date dateEnd;	
	

     
	private double remuneration;



	public Mentoring(Company company, Partner mentor, Date dateBegin, Date dateEnd, double remuneration) {
		super();
		this.company = company;
		this.mentor = mentor;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.remuneration = remuneration;
	}



	public Mentoring() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Company getCompany() {
		return company;
	}



	public void setCompany(Company company) {
		this.company = company;
	}



	public Partner getMentor() {
		return mentor;
	}



	public void setMentor(Partner mentor) {
		this.mentor = mentor;
	}



	public Date getDateBegin() {
		return dateBegin;
	}



	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}



	public Date getDateEnd() {
		return dateEnd;
	}



	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}



	public double getRemuneration() {
		return remuneration;
	}



	public void setRemuneration(double remuneration) {
		this.remuneration = remuneration;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
