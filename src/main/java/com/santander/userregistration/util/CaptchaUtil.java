package com.santander.userregistration.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.userregistration.model.RecaptchaResponse;
import com.santander.userregistration.proxy.CaptchaProxy;

import zipkin2.internal.JsonCodec.JsonReader;

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
		try {
			
			
			//this.validateCaptcha(recaptchaSecret,response,"172.17.188.34");
			
			
			  HttpHeaders headers = new HttpHeaders(); headers.set("Accept",
			  MediaType.APPLICATION_JSON_VALUE);
			  
			  recaptchaVerifyUrl += "?secret=" + recaptchaSecret + "&response=" + response;
			  
			  System.out.println("recaptchaVerifyUrl " + recaptchaVerifyUrl);
			  
			  UriComponentsBuilder builder =
			  UriComponentsBuilder.fromHttpUrl(recaptchaVerifyUrl);
			  
			  HttpEntity<?> entity = new HttpEntity<>(headers);
			  
			  HttpEntity<RecaptchaResponse> resp =
			  restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,
			  RecaptchaResponse.class); System.out.println(resp.getBody());
			  System.out.println(resp.toString());
			 
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		
		return true;
		

		/*
		 * RecaptchaRequest recaptchaRequest=new RecaptchaRequest();
		 * recaptchaRequest.setResponse(response);
		 * recaptchaRequest.setSecret(recaptchaSecret);
		 */

		/*
		 * Map<String, String> body = new HashMap<>(); body.put("secret",
		 * recaptchaSecret); body.put("response", response);
		 * 
		 * ResponseEntity<RecaptchaResponse> recaptchaResponse = null; try {
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.set("Accept",
		 * MediaType.APPLICATION_JSON_VALUE);
		 * 
		 * HttpEntity<?> entity = new HttpEntity<>(headers);
		 * 
		 * UriComponentsBuilder builder =
		 * UriComponentsBuilder.fromUriString(recaptchaVerifyUrl) // Add query parameter
		 * .queryParam("secret", recaptchaSecret).queryParam("response", response);
		 * 
		 * System.out.println("Uri Builder:   " + builder.buildAndExpand(body).toUri());
		 * 
		 * recaptchaResponse =
		 * restTemplate.postForEntity(builder.buildAndExpand(body).toUri(), entity,
		 * RecaptchaResponse.class);
		 */

			// recaptchaResponse = captchaProxy.captchaCheck(recaptchaSecret, response,
			// "172.17.188.34");

			// String url = recaptchaVerifyUrl + "?secret=" + recaptchaSecret + "&response="
			// + response;
			// recaptchaResponse = this.restTemplate.postForEntity(url, param,
			// RecaptchaResponse.class);
		/*
		 * } catch (RestClientException e) { System.out.print(e.getMessage()); }
		 * 
		 * if (recaptchaResponse.getBody().isSuccess()) { return true; } else { return
		 * false; }
		 */
	}
	
	
	/*
	 * private String validateCaptcha(String secret, String response, String
	 * remoteip) {
	 * 
	 * URLConnection connection = null; InputStream is = null; String charset =
	 * java.nio.charset.StandardCharsets.UTF_8.name();
	 * 
	 * String url = "https://www.google.com/recaptcha/api/siteverify"; try { String
	 * query = String.format("secret=%s&response=%s&remoteip=%s",
	 * URLEncoder.encode(secret, charset), URLEncoder.encode(response, charset),
	 * URLEncoder.encode(remoteip, charset));
	 * 
	 * connection = new URL(url + "?" + query).openConnection(); is =
	 * connection.getInputStream(); System.out.println("finally connected");
	 * 
	 * } catch (IOException ex) { // Logger.getLogger().log(Level.SEVERE, null, ex);
	 * System.out.println(ex); } finally { if (is != null) { try { is.close(); }
	 * catch (IOException e) { }
	 * 
	 * } } return "connected"; }
	 */
}
