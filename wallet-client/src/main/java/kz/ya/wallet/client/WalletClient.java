/*
 * gRPC client that consumes the WalletService (see WalletService.proto) service
 */
package kz.ya.wallet.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kz.ya.wallet.Currency;
import kz.ya.wallet.TransactionRequest;
import kz.ya.wallet.WalletServiceGrpc;

/**
 *
 * @author Yerlan
 */
public class WalletClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8980).usePlaintext().build();

        WalletServiceGrpc.WalletServiceBlockingStub stub = WalletServiceGrpc.newBlockingStub(channel);

        TransactionRequest transactionRequest = TransactionRequest.newBuilder()
                .setUserId(1L)
                .setAmount(200)
                .setCurrency(Currency.USD)
                .build();

        try {
            stub.withdraw(transactionRequest);
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
        }

        channel.shutdown();
    }
}
