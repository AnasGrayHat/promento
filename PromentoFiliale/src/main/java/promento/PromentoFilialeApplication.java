package promento;

import java.util.Date;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class PromentoFilialeApplication {

	public static void main(String[] args) {
	ApplicationContext ctx = SpringApplication.run(PromentoFilialeApplication.class, args);

	Locale.setDefault(new Locale("US"));
	
	
	
	
	
	
	}
}
