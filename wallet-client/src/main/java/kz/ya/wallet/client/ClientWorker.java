/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.wallet.client;

import kz.ya.wallet.Currency;
import kz.ya.wallet.WalletServiceGrpc;

/**
 *
 * @author yerlana
 */
public class ClientWorker {

    private final long userId = 11111L;

    private void doRoundA(WalletServiceGrpc.WalletServiceBlockingStub stub) {
        ClientApi clientApi = new ClientApi(stub);
        
        // Deposit 100 USD
        clientApi.deposit(userId, Currency.USD, 100);
        
        // Withdraw 200 USD
        clientApi.withdraw(userId, Currency.USD, 200);

        // Deposit 100 EUR
        clientApi.deposit(userId, Currency.EUR, 100);

        // Get Balance
        clientApi.balance(userId);

        // Withdraw 100 USD
        clientApi.withdraw(userId, Currency.USD, 100);

        // Get Balance
        clientApi.balance(userId);
        
        // Withdraw 100 USD
        clientApi.withdraw(userId, Currency.USD, 100);
    }

    private void doRoundB(WalletServiceGrpc.WalletServiceBlockingStub stub) {
        ClientApi clientApi = new ClientApi(stub);
        
        // Withdraw 100 GBP
        clientApi.withdraw(userId, Currency.GBP, 100);
        
        // Deposit 300 GPB
        clientApi.deposit(userId, Currency.GBP, 300);
        
        // Withdraw 100 GBP
        clientApi.withdraw(userId, Currency.GBP, 100);
        
        // Withdraw 100 GBP
        clientApi.withdraw(userId, Currency.GBP, 100);
        
        // Withdraw 100 GBP
        clientApi.withdraw(userId, Currency.GBP, 100);
    }

    private void doRoundC(WalletServiceGrpc.WalletServiceBlockingStub stub) {
        ClientApi clientApi = new ClientApi(stub);
        
        // Get Balance
        clientApi.balance(userId);
        
        // Deposit 100 USD
        clientApi.deposit(userId, Currency.USD, 100);
        
        // Deposit 100 USD
        clientApi.deposit(userId, Currency.USD, 100);
        
        // Withdraw 100 USD
        clientApi.withdraw(userId, Currency.USD, 100);
        
        // Deposit 100 USD
        clientApi.deposit(userId, Currency.USD, 100);

        // Get Balance
        clientApi.balance(userId);

        // Withdraw 200 USD
        clientApi.withdraw(userId, Currency.USD, 200);

        // Get Balance
        clientApi.balance(userId);
    }
}
