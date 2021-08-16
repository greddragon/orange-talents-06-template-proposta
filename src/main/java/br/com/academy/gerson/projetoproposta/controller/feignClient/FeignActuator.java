package br.com.academy.gerson.projetoproposta.controller.feignClient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "health-actuator", url = "http://localhost:8080/actuator")
public interface FeignActuator {

	@GetMapping(value = "/health", consumes = "application/json", headers = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> healthStatus();
	
	@GetMapping(value = "/prometheus", consumes = "text/plain", headers = MediaType.TEXT_PLAIN_VALUE)
	String prometheus();

}
