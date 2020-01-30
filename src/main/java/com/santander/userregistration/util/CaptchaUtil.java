package com.santander.userregistration.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.userregistration.model.RecaptchaResponse;
import com.santander.userregistration.proxy.CaptchaProxy;

@Service
public class CaptchaUtil {

	@Value("${google.recaptcha.secret.key}")
	public String recaptchaSecret;
	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;

	@Autowired
	private CaptchaProxy captchaProxy;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	public boolean verify(String response) {

		/*
		 * RecaptchaRequest recaptchaRequest=new RecaptchaRequest();
		 * recaptchaRequest.setResponse(response);
		 * recaptchaRequest.setSecret(recaptchaSecret);
		 */

		Map<String, String> body = new HashMap<>();
		body.put("secret", recaptchaSecret);
		body.put("response", response);

		ResponseEntity<RecaptchaResponse> recaptchaResponse = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			HttpEntity<?> entity = new HttpEntity<>(headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(recaptchaVerifyUrl)
					// Add query parameter
					.queryParam("secret", recaptchaSecret).queryParam("response", response);

			System.out.println("Uri Builder:   " + builder.buildAndExpand(body).toUri());

			recaptchaResponse = restTemplate.postForEntity(builder.buildAndExpand(body).toUri(), entity,
					RecaptchaResponse.class);

			// recaptchaResponse = captchaProxy.captchaCheck(recaptchaSecret, response,
			// "172.17.188.34");

			// String url = recaptchaVerifyUrl + "?secret=" + recaptchaSecret + "&response="
			// + response;
			// recaptchaResponse = this.restTemplate.postForEntity(url, param,
			// RecaptchaResponse.class);
		} catch (RestClientException e) {
			System.out.print(e.getMessage());
		}

		if (recaptchaResponse.getBody().isSuccess()) {
			return true;
		} else {
			return false;
		}
	}

}
