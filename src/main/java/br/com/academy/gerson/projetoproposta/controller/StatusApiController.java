package br.com.academy.gerson.projetoproposta.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignHealthActuator;

@RestController
@RequestMapping("api/propostas/saude-api")
public class StatusApiController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FeignHealthActuator health;

	@GetMapping
	public ResponseEntity<?> saudeApi() {

		Map<String, Object> status = health.healthStatus();

		if (!status.containsValue("UP")) {
			logger.error("API não esta funcionando corretamente, status: " + status.get("status"));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
		}

		return ResponseEntity.ok().body(status);
	}
}
