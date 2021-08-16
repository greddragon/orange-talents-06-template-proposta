package br.com.academy.gerson.projetoproposta.controller.feignClient.model;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

class SolicitacaoAvisoViagemTest {
	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = validatorFactory.getValidator();

	@Test
	void test() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		
		 SolicitacaoAvisoViagem emp = new SolicitacaoAvisoViagem("Krishna", Calendar.getInstance().getTime());
		  System.out.println("Validation errors on bean emp1");
		  
		  System.out.println("************************************");
		  Set<ConstraintViolation<SolicitacaoAvisoViagem>> validationErrors = validator.validate(emp);

		  if (validationErrors.size() == 0) {
		   System.out.println("No validation errors....");
		  }

		  for (ConstraintViolation<SolicitacaoAvisoViagem> violation : validationErrors) {
		   System.out.println(violation.getPropertyPath() + "," + violation.getMessage());
		  }
		  System.out.println("");
	
	}

}
