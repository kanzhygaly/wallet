package kz.ya.wallet.srv.exception;

/**
 *
 * @author Yerlan
 */
public class NotValidAmountException extends Exception {

    public NotValidAmountException(String message) {
        super(message);
    }

    public NotValidAmountException(String message, Throwable cause) { super(message, cause); }
}
