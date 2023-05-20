package br.com.fitai.core.exceptions;

public record RestError(
        int cod,
        String message
) {

}