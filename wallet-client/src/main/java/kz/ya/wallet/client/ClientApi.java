/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.wallet.client;

import io.grpc.StatusRuntimeException;
import kz.ya.wallet.BalanceRequest;
import kz.ya.wallet.BalanceResponse;
import kz.ya.wallet.Currency;
import kz.ya.wallet.TransactionRequest;
import kz.ya.wallet.WalletServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yerlana
 */
public class ClientApi {
    
    private final Logger logger = LoggerFactory.getLogger(ClientApi.class);
    
    private final WalletServiceGrpc.WalletServiceBlockingStub stub;

    public ClientApi(WalletServiceGrpc.WalletServiceBlockingStub stub) {
        this.stub = stub;
    }
    
    public void balance(Long userId) {
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setUserId(userId)
                .build();
        try {
            BalanceResponse balanceResponse = stub.balance(balanceRequest);
            
            double balanceUSD = balanceResponse.getBalanceByCurrencyOrThrow(Currency.USD.name());
            logger.info("User [" + userId + "]: USD balance " + balanceUSD);
            
            double balanceEUR = balanceResponse.getBalanceByCurrencyOrThrow(Currency.EUR.name());
            logger.info("User [" + userId + "]: EUR balance " + balanceEUR);
            
            double balanceGBP = balanceResponse.getBalanceByCurrencyOrThrow(Currency.GBP.name());
            logger.info("User [" + userId + "]: GBP balance " + balanceGBP);
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public void withdraw(Long userId, Currency currency, double amount) {
        TransactionRequest transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(amount)
                .setCurrency(currency)
                .build();
        try {
            stub.withdraw(transactionRequest);

            logger.info("User [" + userId + "]: Withdraw " + amount + " " + currency.name());
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    public void deposit(Long userId, Currency currency, double amount) {
        TransactionRequest transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(amount)
                .setCurrency(currency)
                .build();
        try {
            stub.deposit(transactionRequest);

            logger.info("User [" + userId + "]: Deposit " + amount + " " + currency.name());
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }
    }
}
