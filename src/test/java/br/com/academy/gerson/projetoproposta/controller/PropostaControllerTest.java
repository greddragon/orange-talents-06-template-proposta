package br.com.academy.gerson.projetoproposta.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.academy.gerson.projetoproposta.controller.feignClient.FeignApiCartoes;

@SpringBootTest
class PropostaControllerTest {

	private final Logger logger = LoggerFactory.getLogger("teste");

	@Autowired
	private FeignApiCartoes cartoes;

	@Test
	void test() {
		/*
		 * String base64 =
		 * "amZqb2VqcnRlcmh0d29yZ2hnbGtyamdsd2psZ3Jnb3JqZ3dpcmpnaXJqZ29qZ2dyamlncmk=";
		 * String base6 = "gerson";
		 * 
		 * Boolean teste = true; String someString = "...";
		 * 
		 * String pattern =
		 * "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		 * Pattern r = Pattern.compile(pattern); Matcher m = r.matcher(base64); if
		 * (m.find()) { System.out.println("true"); } else { teste = false;
		 * System.out.println("false"); }
		 * 
		 * 
		 * Assert.assertTrue(teste);
		 * 
		 * 
		 * 
		 * String string = "2538-5579-4201-9875"; Map<String, Object> cartao =
		 * cartoes.consultaNumeroCartao(string);
		 * 
		 * Assert.assertEquals(string, cartao.get("id").toString());
		 */

	}

}
