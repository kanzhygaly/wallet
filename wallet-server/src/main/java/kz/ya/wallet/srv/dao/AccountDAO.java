package kz.ya.wallet.srv.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import kz.ya.wallet.srv.exception.AccountNotFoundException;
import kz.ya.wallet.srv.exception.InsufficientFundsException;
import kz.ya.wallet.srv.entity.Account;

/**
 *
 * @author Yerlan
 */
public interface AccountDAO {

    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByUserIdAndCurrency(Long userId, String currencyCode);

    Optional<Account> findById(Long id);

    Account saveOrUpdate(Account entity);

    void delete(Long id);

    void updateAccountBalance(Long accountId, BigDecimal deltaAmount);
}
