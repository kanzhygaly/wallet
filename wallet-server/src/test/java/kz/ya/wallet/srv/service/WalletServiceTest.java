package kz.ya.wallet.srv.service;

import kz.ya.wallet.*;
import kz.ya.wallet.srv.WalletServerTest;
import kz.ya.wallet.srv.dao.AccountDAO;
import kz.ya.wallet.srv.dao.impl.AccountDAOImpl;
import kz.ya.wallet.srv.exception.InsufficientFundsException;
import kz.ya.wallet.srv.entity.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

/**
 * @author Yerlan
 */
@RunWith(JUnit4.class)
public class WalletServiceTest extends WalletServerTest {

    private final AccountDAO accountDAO = new AccountDAOImpl();
    private final long userId = 1L;

    @Before
    public void setUp() {
        // USD account
        Account usd = accountDAO.saveOrUpdate(new Account(userId, Currency.USD.name(), BigDecimal.ZERO));
        // EUR account
        Account eur = accountDAO.saveOrUpdate(new Account(userId, Currency.EUR.name(), BigDecimal.ZERO));
        // GBP account
        Account gbp = accountDAO.saveOrUpdate(new Account(userId, Currency.GBP.name(), BigDecimal.ZERO));
    }

    @After
    public void tearDown() {
        int num = accountDAO.deleteByUserId(userId);
        System.out.println(num + " Accounts for User " + userId + " were deleted");
    }

    @Test
    public void integrationTest() {
        WalletServiceGrpc.WalletServiceBlockingStub stub = WalletServiceGrpc.newBlockingStub(inProcessChannel);

        // 1. Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        TransactionRequest transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        exceptionRule.expect(InsufficientFundsException.class);
        stub.withdraw(transactionRequest);

        // 2. Make a deposit of USD 100 to user with id 1.
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.USD)
                .build();

        stub.deposit(transactionRequest);

        // 3. Check that all balances are correct
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setUserId(userId)
                .build();

        BalanceResponse balanceResponse = stub.balance(balanceRequest);
        double balanceUSD = balanceResponse.getBalanceByCurrencyOrThrow(Currency.USD.name());
        Assert.assertEquals(100, balanceUSD, 1);
        double balanceEUR = balanceResponse.getBalanceByCurrencyOrThrow(Currency.EUR.name());
        Assert.assertEquals(0, balanceEUR, 1);
        double balanceGBP = balanceResponse.getBalanceByCurrencyOrThrow(Currency.GBP.name());
        Assert.assertEquals(0, balanceGBP, 1);

        // 4. Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        exceptionRule.expect(InsufficientFundsException.class);
        stub.withdraw(transactionRequest);

        // 5. Make a deposit of EUR 100 to user with id 1.
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.EUR)
                .build();

        stub.deposit(transactionRequest);

        // 6. Check that all balances are correct
        balanceResponse = stub.balance(balanceRequest);
        balanceUSD = balanceResponse.getBalanceByCurrencyOrThrow(Currency.USD.name());
        Assert.assertEquals(100, balanceUSD, 1);
        balanceEUR = balanceResponse.getBalanceByCurrencyOrThrow(Currency.EUR.name());
        Assert.assertEquals(100, balanceEUR, 1);
        balanceGBP = balanceResponse.getBalanceByCurrencyOrThrow(Currency.GBP.name());
        Assert.assertEquals(0, balanceGBP, 1);

        // 7. Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        exceptionRule.expect(InsufficientFundsException.class);
        stub.withdraw(transactionRequest);

        // 8. Make a deposit of USD 100 to user with id 1.
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.USD)
                .build();

        stub.deposit(transactionRequest);

        // 9. Check that all balances are correct
        balanceResponse = stub.balance(balanceRequest);
        balanceUSD = balanceResponse.getBalanceByCurrencyOrThrow(Currency.USD.name());
        Assert.assertEquals(200, balanceUSD, 1);
        balanceEUR = balanceResponse.getBalanceByCurrencyOrThrow(Currency.EUR.name());
        Assert.assertEquals(100, balanceEUR, 1);
        balanceGBP = balanceResponse.getBalanceByCurrencyOrThrow(Currency.GBP.name());
        Assert.assertEquals(0, balanceGBP, 1);

        // 10. Make a withdrawal of USD 200 for user with id 1. Must return "ok".
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        stub.withdraw(transactionRequest);

        // 11. Check that all balances are correct
        balanceResponse = stub.balance(balanceRequest);
        balanceUSD = balanceResponse.getBalanceByCurrencyOrThrow(Currency.USD.name());
        Assert.assertEquals(0, balanceUSD, 1);
        balanceEUR = balanceResponse.getBalanceByCurrencyOrThrow(Currency.EUR.name());
        Assert.assertEquals(100, balanceEUR, 1);
        balanceGBP = balanceResponse.getBalanceByCurrencyOrThrow(Currency.GBP.name());
        Assert.assertEquals(0, balanceGBP, 1);

        // 12. Make a withdrawal of USD 200 for user with id 1. Must return "insufficient_funds".
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        exceptionRule.expect(InsufficientFundsException.class);
        stub.withdraw(transactionRequest);
    }
}
