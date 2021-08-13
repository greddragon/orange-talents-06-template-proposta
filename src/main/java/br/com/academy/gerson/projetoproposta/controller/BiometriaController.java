package br.com.academy.gerson.projetoproposta.controller;

import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.academy.gerson.projetoproposta.Dto.BiometriaDto;
import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;
import br.com.academy.gerson.projetoproposta.entidade.Biometria;
import br.com.academy.gerson.projetoproposta.repositorio.BiometriaRepository;

@RestController
@RequestMapping("/biometria/")
public class BiometriaController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BiometriaRepository repository;

	@Autowired
	private FeignApiCartoes cartoes;

	@PostMapping(value = "cadastro/{idCartao}")
	@Transactional
	public ResponseEntity<?> CadastroBiometria(@PathVariable String idCartao, @RequestBody BiometriaDto dto,
			UriComponentsBuilder uriBuilder) {

		if (!isValid(dto.getBiometria())) {
			logger.error("biometria inválida.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Map<String, Object> cartao;

		try {
			cartao = cartoes.consultaNumeroCartao(idCartao);

		} catch (Exception e) {
			logger.error("" + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		dto.setNumeroCartao(cartao.get("id").toString());
		Biometria biometria = dto.toBiometria(dto);

		repository.save(biometria);

		URI uriBiometria = uriBuilder.path("/biometria/cadastro/{idCartao}").build(biometria.getId());
		return ResponseEntity.created(uriBiometria).build();
	}

	private boolean isValid(String biometria) {
		String pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(biometria);
		if (m.find()) {
			return true;
		} else {
			return false;

		}
	}
}
