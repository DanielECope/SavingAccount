package com.nttdata.saving_account.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModelNotFoundException extends RuntimeException{
    public ModelNotFoundException(String mensaje){
        super(mensaje);
    }
}
