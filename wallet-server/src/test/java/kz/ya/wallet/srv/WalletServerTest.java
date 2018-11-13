package kz.ya.wallet.srv;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * @author Yerlan
 */
public class WalletServerTest {

    /**
     * This rule manages automatic graceful shutdown for the registered channel at the end of test.
     */
//    @Rule
    @ClassRule
    public final static GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private static WalletServer server;
    protected static ManagedChannel inProcessChannel;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();
        // Use directExecutor for both InProcessServerBuilder and InProcessChannelBuilder can reduce the
        // usage timeouts and latches in test. But we still add timeout and latches where they would be
        // needed if no directExecutor were used, just for demo purpose.
        server = new WalletServer(InProcessServerBuilder.forName(serverName).directExecutor(), 0);
        server.start();
        // Create a client channel and register for automatic graceful shutdown.
        inProcessChannel = grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }

    @AfterClass
    public static void tearDownClass() {
        // close connections and shutdown the server
        DbConnection.closeEntityManagerFactory();
        server.stop();
    }
}