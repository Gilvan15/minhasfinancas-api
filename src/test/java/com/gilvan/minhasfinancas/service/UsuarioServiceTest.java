package com.gilvan.minhasfinancas.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gilvan.minhasfinancas.model.entity.Usuario;
import com.gilvan.minhasfinancas.model.repository.UsuarioRepository;
import com.gilvan.minhasfinancas.model.service.UsuarioService;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test()
	public void deveValidarEmail() {
		//cenário
		repository.deleteAll();
		
		//acao
		service.validarEmail("gilvanx10@gmail.com");
		
	}
	
	
	
	

}
