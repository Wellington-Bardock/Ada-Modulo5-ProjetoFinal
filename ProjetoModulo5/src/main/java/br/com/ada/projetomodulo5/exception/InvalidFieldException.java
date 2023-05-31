package br.com.ada.projetomodulo5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFieldException extends RuntimeException{

    public InvalidFieldException(String msg) {
        super(msg);
    }

}
