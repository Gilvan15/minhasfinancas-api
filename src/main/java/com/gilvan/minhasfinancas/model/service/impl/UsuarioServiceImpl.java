package com.gilvan.minhasfinancas.model.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gilvan.minhasfinancas.exception.ErroAutenticacao;
import com.gilvan.minhasfinancas.exception.RegraNegocioException;
import com.gilvan.minhasfinancas.model.entity.Usuario;
import com.gilvan.minhasfinancas.model.repository.UsuarioRepository;
import com.gilvan.minhasfinancas.model.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

//	@Autowired
	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário nao encontrado para o Email informado!");
		}
		
		if(!usuario.get().getSenha().equals(senha) ) {
			throw new ErroAutenticacao("Senha inválida!");
		}
 
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);

		
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Email já cadastrado no sistema!");
		}

	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
 
		return repository.findById(id);
	}

}
