package br.com.academy.gerson.projetoproposta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignActuator;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("api/propostas/metricas")
public class MetricasController {

	private final Tracer tracer;

	public MetricasController(Tracer tracer) {
		this.tracer = tracer;
	}

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FeignActuator prometheus;

	@GetMapping
	public ResponseEntity<?> saudeApi() {

		Span span = tracer.activeSpan();
		span.setTag("user.email", "metricas@get.com");
		span.setBaggageItem("user.email", "metricas@get.com");

		String status = prometheus.prometheus();

		logger.info("metricas coletadas com sucesso");
		return ResponseEntity.ok().body(status);
	}

}
