package one.digitalinnovation.gof.service.validation;

import one.digitalinnovation.gof.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ValidarCpfHandler extends ClienteHandler {
    @Override
    public void handle(Cliente cliente) {
        // Converte o ID (long) para String
        String cpf = String.valueOf(cliente.getId());

        // Validação simples de tamanho (11 dígitos) para evitar erro
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido: deve conter 11 dígitos!");
        }

        // Aqui você poderia adicionar validação mais avançada se quiser
        // Ex: checar sequências inválidas ou aplicar algoritmo de validação do CPF

        if (next != null) {
            next.handle(cliente);
        }
    }
}

