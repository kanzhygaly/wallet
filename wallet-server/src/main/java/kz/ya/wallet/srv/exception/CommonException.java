package kz.ya.wallet.srv.exception;

/**
 *
 * @author Yerlan
 */
public class CommonException extends Exception {

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }
}
