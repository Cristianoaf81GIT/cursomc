package com.cristianoaf81.cursomc.services.exceptions;
import java.io.Serializable;

public class FileException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -6825942793732941415L;

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

}
