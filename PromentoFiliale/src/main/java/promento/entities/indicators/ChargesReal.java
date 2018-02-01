package promento.entities.indicators;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import promento.entities.Company;
import promento.entities.Indicator;



@Entity
@DiscriminatorValue("ChargesReal")
public  class ChargesReal extends Indicator{

	public ChargesReal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChargesReal(Double value, Date date, Company company) {
		super(value, date, company);
		// TODO Auto-generated constructor stub
	}

	
	
}
