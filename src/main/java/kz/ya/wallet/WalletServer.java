/*
 * gRPC server that serve the WalletService (see WalletService.proto) service
 */
package kz.ya.wallet;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import kz.ya.wallet.server.WalletService;

/**
 *
 * @author yerlana
 */
public class WalletServer {

    private final int port;
    private final Server server;

    public WalletServer(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }

    public WalletServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        server = serverBuilder.addService(new WalletService()).build();
    }

    /**
     * Start serving requests.
     * 
     * @throws java.io.IOException
     */
    public void start() throws IOException {
        server.start();
//        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                WalletServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        WalletServer server = new WalletServer(8980);
        server.start();
        server.blockUntilShutdown();
    }
}
