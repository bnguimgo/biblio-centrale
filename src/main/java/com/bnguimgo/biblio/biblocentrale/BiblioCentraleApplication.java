package com.bnguimgo.biblio.biblocentrale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Ref-1: https://medium.com/@pratik.941/building-rest-api-using-spring-boot-a-comprehensive-guide-3e9b6d7a8951
//Ref-2: lombok: https://www.geeksforgeeks.org/best-practices-while-making-rest-apis-in-spring-boot-application/
//Ref-3: webclient: https://www.geeksforgeeks.org/spring-boot-webclient-with-example/

@SpringBootApplication
public class BiblioCentraleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioCentraleApplication.class, args);
	}

}
