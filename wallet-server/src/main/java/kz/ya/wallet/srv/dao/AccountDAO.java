package kz.ya.wallet.srv.dao;

import java.math.BigDecimal;
import kz.ya.wallet.srv.dto.UserTransfer;
import kz.ya.wallet.srv.exception.CommonException;
import kz.ya.wallet.srv.model.Account;

/**
 *
 * @author Yerlan
 */
public interface AccountDAO {
    
    Account getAccountById(long accountId);
    
    Account createAccount(String accountNo) throws CommonException;
    
    void deleteAccount(long accountId) throws CommonException;
    
    void updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CommonException;
    
    void transferAccountBalance(UserTransfer dto) throws CommonException;
}
