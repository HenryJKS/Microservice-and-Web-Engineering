package br.com.fiap.mspagamentos.service.exception;

public class ResourceNotFoundException extends RuntimeException{
    // classe para tratar exceções
    // Exception personalizada
    public ResourceNotFoundException(String messsage) {
        super(messsage);
    }
}
