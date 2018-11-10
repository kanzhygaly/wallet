package kz.ya.wallet.srv.dao.impl;

import java.math.BigDecimal;
import javax.persistence.LockModeType;
import kz.ya.wallet.srv.DbConnection;
import kz.ya.wallet.srv.dao.AccountDAO;
import kz.ya.wallet.srv.dto.UserTransfer;
import kz.ya.wallet.srv.exception.CommonException;
import kz.ya.wallet.srv.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yerlan
 */
public class AccountDAOImpl implements AccountDAO {
    
    private final Logger logger = LoggerFactory.getLogger(AccountDAOImpl.class);
    
    /**
     * Get account by id.
     * 
     * @param accountId
     * @return existing Account if found, else NULL
     */
    @Override
    public Account getAccountById(long accountId) {
        return DbConnection.getEntityManager().find(Account.class, accountId);
    }

    /**
     * Create new account.
     *
     * @param accountNo
     * @return new Account
     * @throws CommonException
     */
    @Override
    public Account createAccount(String accountNo) throws CommonException {
        Account account = null;
        try {
            DbConnection.beginTransaction();

//            account = DbConnection.getEntityManager().merge(new Account(accountNo));
            
            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            logger.error(e.getMessage());
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
        return account;
    }
    
    /**
     * Delete account by id.
     * 
     * @param accountId
     * @throws CommonException 
     */
    @Override
    public void deleteAccount(long accountId) throws CommonException {
        try {
            DbConnection.beginTransaction();
            
            Account targetAccount = getAccountById(accountId);

            DbConnection.getEntityManager().remove(targetAccount);

            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            logger.error(e.getMessage());
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
    }

    /**
     * Update account balance.
     *
     * @param accountId
     * @param deltaAmount
     * @throws CommonException
     */
    @Override
    public void updateAccountBalance(long accountId, BigDecimal deltaAmount) throws CommonException {
        try {
            DbConnection.beginTransaction();

            Account targetAccount = DbConnection.getEntityManager().find(
                    Account.class, accountId, LockModeType.READ);

            if (targetAccount == null) {
                throw new CommonException("updateAccountBalance(): failed to lock account : " + accountId);
            }

            // update account upon success locking
            BigDecimal balance = targetAccount.getBalance().add(deltaAmount);
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new CommonException("Not enough funds on account : "
                        + targetAccount.getUserId());
            }

            // proceed with update
            targetAccount.setBalance(balance);

            DbConnection.getEntityManager().merge(targetAccount);

            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            logger.error(e.getMessage());
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
    }

    /**
     * Transfer balance between two accounts.
     *
     * @param dto
     * @throws CommonException
     */
    @Override
    public void transferAccountBalance(UserTransfer dto) throws CommonException {
        try {
            DbConnection.beginTransaction();

            Account fromAccount = DbConnection.getEntityManager().find(
                    Account.class, dto.getFromAccountId(), LockModeType.READ);

            Account toAccount = DbConnection.getEntityManager().find(
                    Account.class, dto.getToAccountId(), LockModeType.READ);

            // check locking status
            if (fromAccount == null) {
                throw new CommonException("transferAccountBalance(): failed to lock account : "
                        + dto.getFromAccountId());
            }
            if (toAccount == null) {
                throw new CommonException("transferAccountBalance(): failed to lock account : "
                        + dto.getToAccountId());
            }

            // check enough balance in source account
            BigDecimal leftAmount = fromAccount.getBalance().subtract(dto.getAmount());
            if (leftAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new CommonException("Not enough funds on account : "
                        + fromAccount.getUserId());
            }

            // proceed with update
            fromAccount.setBalance(leftAmount);
            toAccount.getBalance().add(dto.getAmount());

            DbConnection.getEntityManager().merge(fromAccount);
            DbConnection.getEntityManager().merge(toAccount);

            DbConnection.commit();
        } catch (RuntimeException e) {
            if (DbConnection.getEntityManager() != null && DbConnection.getEntityManager().isOpen()) {
                DbConnection.rollback();
            }
            logger.error(e.getMessage());
            throw new CommonException(e);
        } finally {
            DbConnection.closeEntityManager();
        }
    }
}
