package com.santander.userregistration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.santander.userregistration.model.RecaptchaResponse;

@CrossOrigin("*")
@FeignClient(name="recaptcha",url="https://www.google.com")
public interface CaptchaProxy {
	
	@PostMapping("recaptcha/api/siteverify")
	public RecaptchaResponse captchaCheck(@RequestParam String secret, @RequestParam String response, @RequestParam String remoteip);

}
