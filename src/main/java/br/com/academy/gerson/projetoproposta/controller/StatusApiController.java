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

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignActuator;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("api/propostas/saude-api")
public class StatusApiController {

	private final Tracer tracer;

	public StatusApiController(Tracer tracer) {
		this.tracer = tracer;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FeignActuator health;

	@GetMapping
	public ResponseEntity<?> saudeApi() {

		Span span = tracer.activeSpan();
		span.setTag("user.email", "saude-api@get.com");
		span.setBaggageItem("user.email", "saude-api@get.com");

		Map<String, Object> status = health.healthStatus();

		if (!status.containsValue("UP")) {
			logger.error("API não esta funcionando corretamente, status: " + status.get("status"));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
		}

		return ResponseEntity.ok().body(status);
	}
}
