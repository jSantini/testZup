package br.com.jsantini.testzup.model.exception;

/**
 * Created by jsantini on 14/03/16.
 * Exception Personalizada pra tratar idImdb unique error
 */
public class MovieUniqueIdImdbException extends Exception {

    public MovieUniqueIdImdbException() {
    }

    public MovieUniqueIdImdbException(String message) {
        super(message);
    }

    public MovieUniqueIdImdbException(Throwable cause) {
        super(cause);
    }

    public MovieUniqueIdImdbException(String message, Throwable cause) {
        super(message, cause);
    }
}
