package kz.ya.wallet.srv.exception;

/**
 *
 * @author Yerlan
 */
public class UnknownCurrencyException extends Exception {

    public UnknownCurrencyException(String message) {
        super(message);
    }

    public UnknownCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
