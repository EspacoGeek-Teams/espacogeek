package com.espacogeek.geek.exception;

public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message); // * @AbigailGeovana define a mensagem que será mostrada no erro para o cliente
    }
}
