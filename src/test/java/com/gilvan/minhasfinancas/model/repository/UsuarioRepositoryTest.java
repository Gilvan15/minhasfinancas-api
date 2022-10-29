package com.gilvan.minhasfinancas.model.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gilvan.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarAExistenciadoEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("Gilvan").email("gilvanx10@gmail.com").build();
		repository.save(usuario);
		
		//ação
		boolean result = repository.existsByEmail("gilvanx10@gmail.com");
		
		//verificação
		Assertions.assertTrue(result);
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		//cenário
		repository.deleteAll();
		
		//ação
		boolean result = repository.existsByEmail("gilvanx10@gmail.com");
		System.out.println(result);
		//verificação
		Assertions.assertFalse(result);
		
	}

}
