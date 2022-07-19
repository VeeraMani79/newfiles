package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class UseBankController {
	@Autowired
	RestTemplate restTemp;

	@GetMapping(value="/loanWithInterest")
	public List<Bank> getBank() {
		String Url1 = "http://localhost:8080/getAll";
		String Url2 = "http://localhost:8081/getAcno/";
		ResponseEntity<List<Bank>> response1 = restTemp.exchange(Url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Bank>>() {
				});

		List<Bank> banks = response1.getBody();
		banks.forEach(x -> {
			int acno= x.getBank_acno();

			ResponseEntity<Integer> response2 = restTemp.exchange(Url2 +acno, HttpMethod.GET, null, Integer.class);
			Integer percentage = response2.getBody();
			x.setBank_loan(x.getBank_loan() + (x.getBank_loan() * percentage / 100));
		});
		return banks;
	}
}
