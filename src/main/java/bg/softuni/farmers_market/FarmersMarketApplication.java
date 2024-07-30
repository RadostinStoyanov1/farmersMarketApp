package bg.softuni.farmers_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FarmersMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmersMarketApplication.class, args);
	}

}
