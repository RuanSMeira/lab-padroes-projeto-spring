package one.digitalinnovation.gof.service.validation;
import one.digitalinnovation.gof.model.Cliente;

public abstract class ClienteHandler {
    protected ClienteHandler next;

    public ClienteHandler setNext(ClienteHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(Cliente cliente);
}
