/*
 * Implementation of WalletService
 */
package kz.ya.wallet.server;

import io.grpc.stub.StreamObserver;
import kz.ya.wallet.BalanceRequest;
import kz.ya.wallet.BalanceResponse;
import kz.ya.wallet.TransactionRequest;
import kz.ya.wallet.TransactionResponse;
import kz.ya.wallet.WalletServiceGrpc;

/**
 *
 * @author yerlana
 */
public class WalletService extends WalletServiceGrpc.WalletServiceImplBase {

    @Override
    public void balance(BalanceRequest request, StreamObserver<BalanceResponse> responseObserver) {
        super.balance(request, responseObserver); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void withdraw(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        super.withdraw(request, responseObserver); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deposit(TransactionRequest request, StreamObserver<TransactionResponse> responseObserver) {
        super.deposit(request, responseObserver); //To change body of generated methods, choose Tools | Templates.
    }
    
}
