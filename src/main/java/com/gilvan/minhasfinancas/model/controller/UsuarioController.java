package com.gilvan.minhasfinancas.model.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gilvan.minhasfinancas.exception.ErroAutenticacao;
import com.gilvan.minhasfinancas.exception.RegraNegocioException;
import com.gilvan.minhasfinancas.model.dto.UsuarioDTO;
import com.gilvan.minhasfinancas.model.entity.Usuario;
import com.gilvan.minhasfinancas.model.service.LancamentoService;
import com.gilvan.minhasfinancas.model.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@SuppressWarnings("rawtypes")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private LancamentoService lancamentoService;

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {

		try {

			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);

		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {

		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
		try {
			Usuario usuarioSlavo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSlavo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo(@PathVariable("id") Long id ) {
		Optional<Usuario> usuario = service.obterPorId(id);
		
		if(!usuario.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND); 
		}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}

}
