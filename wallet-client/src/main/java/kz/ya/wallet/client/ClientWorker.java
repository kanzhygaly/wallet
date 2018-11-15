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
public class ClientWorker {

    private final Logger logger = LoggerFactory.getLogger(ClientWorker.class);
    private final long userId = 11111L;

    private void doRoundA(WalletServiceGrpc.WalletServiceBlockingStub stub) {
        // Deposit 100 USD
        TransactionRequest transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.USD)
                .build();
        try {
            stub.deposit(transactionRequest);

            logger.info("User [" + userId + "]: Deposit 100 USD");
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }

        // Withdraw 200 USD
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();
        try {
            stub.withdraw(transactionRequest);

            logger.info("User [" + userId + "]: Withdraw 200 USD");
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }

        // Deposit 100 EUR
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.EUR)
                .build();
        try {
            stub.deposit(transactionRequest);

            logger.info("User [" + userId + "]: Deposit 100 EUR");
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }

        // Get Balance
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

        // Withdraw 100 USD
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.USD)
                .build();
        try {
            stub.withdraw(transactionRequest);

            logger.info("User [" + userId + "]: Withdraw 100 USD");
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }

        // Get Balance
        balanceRequest = BalanceRequest.newBuilder()
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
        
        // Withdraw 100 USD
        transactionRequest = TransactionRequest.newBuilder()
                .setUserId(userId)
                .setAmount(100)
                .setCurrency(Currency.USD)
                .build();
        try {
            stub.withdraw(transactionRequest);

            logger.info("User [" + userId + "]: Withdraw 100 USD");
        } catch (StatusRuntimeException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void doRoundB(WalletServiceGrpc.WalletServiceBlockingStub stub) {

    }

    private void doRoundC(WalletServiceGrpc.WalletServiceBlockingStub stub) {

    }
}
