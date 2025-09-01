package one.digitalinnovation.gof.service.validation;
import org.springframework.stereotype.Service;
import one.digitalinnovation.gof.model.Cliente;

@Service
public class ClienteValidationChain {

    private ValidarCpfHandler validarCpf;
    private ValidarCepHandler validarCep;

    public ClienteValidationChain(ValidarCpfHandler validarCpf,
                                  ValidarCepHandler validarCep) {
        this.validarCpf = validarCpf;
        this.validarCep = validarCep;
    }

    public void validar(Cliente cliente) {
        validarCpf.setNext(validarCep);
        validarCpf.handle(cliente);
    }
}

