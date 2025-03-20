package br.edu.zup.tax_calc_api.exceptions;

public class TaxNotFoundException extends RuntimeException{
    public TaxNotFoundException(String message) {
        super(message);
    }
}
