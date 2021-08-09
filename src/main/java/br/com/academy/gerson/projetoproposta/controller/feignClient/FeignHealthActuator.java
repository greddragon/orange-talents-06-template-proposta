package br.com.academy.gerson.projetoproposta.controller.feignClient;


import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "health-actuator", url =  "http://localhost:8080/")
public interface FeignHealthActuator {
	
	@GetMapping(value = "actuator/health", consumes = "application/json", headers = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> healthStatus();

}
