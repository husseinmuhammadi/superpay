package ir.asan.micro;

import io.grpc.Server;
import io.grpc.ServerInterceptors;
import io.grpc.netty.NettyServerBuilder;
import ir.asan.micro.concurrency.MonitoredThreadPoolExecutor;
import me.dinowernli.grpc.prometheus.MonitoringServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class WalletServer {
    static Logger logger = LoggerFactory.getLogger(WalletServer.class);

    Server microGrpc;

    MonitoringServerInterceptor monitoringInterceptor =
            MonitoringServerInterceptor.create(me.dinowernli.grpc.prometheus.Configuration.allMetrics());

    ThreadPoolExecutor executor =
            new MonitoredThreadPoolExecutor(
                    100,
                    100,
                    10,
                    100,
                    10000,
                    new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        try {
            WalletServer server = new WalletServer();
            server.start();
            server.awaitShutdown();
        } catch (Exception e) {
            logger.error("gRPC Error", e);
        }
    }

    public void start() throws IOException {
        microGrpc = NettyServerBuilder.forPort(31033)
                .addService(ServerInterceptors.intercept(
                        new MicroServiceImpl(),
                        monitoringInterceptor
                ))
                .executor(executor)
                .build()
                .start();
        logger.info("gRPC server started!");
    }

    public void awaitShutdown() throws InterruptedException {
        if (microGrpc != null) {
            microGrpc.awaitTermination();
        }
    }
}
