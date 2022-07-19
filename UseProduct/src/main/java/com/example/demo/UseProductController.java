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

public class UseProductController {
	@Autowired
	RestTemplate restTemp;

	@GetMapping(value = "/ProductWithGst")
	public List<Product> getByHsn() {
		String Url1 = "http://localhost:8080/getAll";
		String Url2 = "http://localhost:8081/findHsno/";
		ResponseEntity<List<Product>> response1 = restTemp.exchange(Url1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Product>>() {
				});

		List<Product> products = response1.getBody();
		products.forEach(x -> {
			int hsn = x.getProduct_hsno();

			ResponseEntity<Integer> response2 = restTemp.exchange(Url2 + hsn, HttpMethod.GET, null, Integer.class);
			Integer percentage = response2.getBody();
			x.setProduct_price(x.getProduct_price() + (x.getProduct_price() * percentage / 100));
		});
		return products;
	}

}
