package br.com.matheusassmann.produtoms.exceptions;

public class ProdutoNotFoundException extends RuntimeException {

    public ProdutoNotFoundException(String msg) {
        super(msg);
    }
}
