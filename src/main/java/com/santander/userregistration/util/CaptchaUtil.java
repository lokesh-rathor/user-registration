package com.santander.userregistration.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.userregistration.model.RecaptchaResponse;

@Service
public class CaptchaUtil {

	@Value("${google.recaptcha.secret.key}")
	public String recaptchaSecret;
	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	public boolean verify(String response) {
		try {

			//IMP: This code is working fine without proxy so here we are bypassing the proxy
			
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println("System IP Address : " + (localhost.getHostAddress()).trim());

			if (localhost.getHostAddress().trim().indexOf("172.17") == -1) {//Cheking whether this system is of niit
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

				recaptchaVerifyUrl += "?secret=" + recaptchaSecret + "&response=" + response;

				System.out.println("recaptchaVerifyUrl " + recaptchaVerifyUrl);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(recaptchaVerifyUrl);

				HttpEntity<RecaptchaResponse> entity = new HttpEntity<>(headers);

				HttpEntity<RecaptchaResponse> resp = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
						entity, RecaptchaResponse.class);
				System.out.println(resp.getBody());
				System.out.println(resp.toString());

				return true;
			} else {
				
				return true;
			}
			
		} catch (RestClientException | UnknownHostException ex) {
			ex.printStackTrace();
		} 

		return false;

	}

}
