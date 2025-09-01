package one.digitalinnovation.gof.service.validation;
import one.digitalinnovation.gof.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ValidarCepHandler extends ClienteHandler {
    @Override
    public void handle(Cliente cliente) {
        if (cliente.getEndereco() == null || cliente.getEndereco().getCep() == null) {
            throw new IllegalArgumentException("CEP obrigatório!");
        }
        if (next != null) next.handle(cliente);
    }
}

