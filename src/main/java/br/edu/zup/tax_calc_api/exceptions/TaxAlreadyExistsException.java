package br.edu.zup.tax_calc_api.exceptions;

public class TaxAlreadyExistsException extends RuntimeException{
    public TaxAlreadyExistsException(String message) {
        super(message);
    }
}