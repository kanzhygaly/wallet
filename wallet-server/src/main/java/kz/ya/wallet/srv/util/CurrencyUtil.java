package kz.ya.wallet.srv.util;

import kz.ya.wallet.srv.exception.UnknownCurrencyException;

import java.util.Currency;

public class CurrencyUtil {

    /**
     * Validate Currency Code
     *
     * @param currencyCode Currency code to be validated
     * @throws UnknownCurrencyException if currency code is not valid
     */
    public static void validateCurrencyCode(String currencyCode) throws UnknownCurrencyException {
        try {
            Currency.getInstance(currencyCode);
        } catch (Exception e) {
            throw new UnknownCurrencyException("Cannot parse the input Currency Code, Validation Failed: ", e);
        }
    }
}
