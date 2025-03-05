package br.edu.zup.tax_calc_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxCalcApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxCalcApiApplication.class, args);

        System.out.print(
				"\n" +
				"==========================================\n" +
				"The Tax Calc API Application is running...\n" +
				"==========================================\n" +
				"\n"
		);
	}

}
