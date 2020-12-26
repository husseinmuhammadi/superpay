package com.superpay.wallet;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ir.asan.micro.MicroServiceGrpc;
import ir.asan.micro.RequestPr;
import ir.asan.micro.ResponsePr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOGGER= LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("Application started!");
        Application application = new Application();
        application.test();
    }

    // public static final String WALLET_IP = "172.25.25.73";
    public static final String WALLET_IP = "localhost";
    public static final int WALLET_PORT = 31033;

    // @Test
    void test() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(WALLET_IP, WALLET_PORT).usePlaintext().build();
        MicroServiceGrpc.MicroServiceBlockingStub blockingStub = MicroServiceGrpc.newBlockingStub(channel);
        ResponsePr responsePr = blockingStub.generalCall(
                RequestPr.newBuilder()
                        .setAccountId("185")
                        .build()
        );
        channel.shutdown();
    }
}
