package one.digitalinnovation.gof.service.impl;


import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.service.validation.ClienteValidationChain;
import one.digitalinnovation.gof.service.validation.ValidarCepHandler;
import one.digitalinnovation.gof.service.validation.ValidarCpfHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

import javax.annotation.PostConstruct;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author falvojr
 */

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	@Autowired
	private ClienteValidationChain validationChain;

	@Override
	public void inserir(Cliente cliente) {
		validationChain.validar(cliente);

		Endereco endereco = cliente.getEndereco();
		if (endereco != null) {
			Endereco enderecoPersistido = enderecoRepository.findById(endereco.getCep())
					.orElseGet(() -> enderecoRepository.save(endereco));

			// Preenche apenas os campos que estão nulos no JSON
			if (endereco.getLogradouro() == null) endereco.setLogradouro(enderecoPersistido.getLogradouro());
			if (endereco.getBairro() == null) endereco.setBairro(enderecoPersistido.getBairro());
			if (endereco.getLocalidade() == null) endereco.setLocalidade(enderecoPersistido.getLocalidade());
			if (endereco.getComplemento() == null) endereco.setComplemento(enderecoPersistido.getComplemento());

			cliente.setEndereco(endereco);
		}

		clienteRepository.save(cliente);
	}


	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		clienteRepository.findById(id).ifPresent(c -> {
			validationChain.validar(cliente);

			Endereco endereco = cliente.getEndereco();
			if (endereco != null) {
				Endereco enderecoPersistido = enderecoRepository.findById(endereco.getCep())
						.orElseGet(() -> enderecoRepository.save(endereco));

				// Preenche apenas os campos nulos
				if (endereco.getLogradouro() == null) endereco.setLogradouro(enderecoPersistido.getLogradouro());
				if (endereco.getBairro() == null) endereco.setBairro(enderecoPersistido.getBairro());
				if (endereco.getLocalidade() == null) endereco.setLocalidade(enderecoPersistido.getLocalidade());
				if (endereco.getComplemento() == null) endereco.setComplemento(enderecoPersistido.getComplemento());

				cliente.setEndereco(endereco);
			}

			clienteRepository.save(cliente);
		});
	}


	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}
}
