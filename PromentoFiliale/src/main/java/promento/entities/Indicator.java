package promento.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="indicator_type")
public class Indicator implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	
	private Double value;
	
	
    @JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
	private Date date;	
    
    
    
    @JsonIgnore
    @ManyToOne
	@JoinColumn(name="company_id")
    private Company company;

	public Indicator(Double value, Date date, Company company) {
		super();
		this.value = value;
		this.date = date;
		this.company = company;
	}

	public Indicator() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
    
    
    
	
	
}
