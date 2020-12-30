package com.superpay.wallet;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ir.asan.micro.MicroServiceGrpc;
import ir.asan.micro.RequestPr;
import ir.asan.micro.ResponsePr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("Application started!");
        Application application = new Application();
        application.test();
    }

    void test() {
        for (int i = 0; i < 100000; i++) {
            TaskExecutor executor = new TaskExecutor();
            Thread thread = new Thread(executor, String.format("executor %d", i));
            thread.start();
            sleep(10);
        }
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TaskExecutor implements Runnable {
    Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    public static final String WALLET_IP = "172.25.25.73";
    public static final int WALLET_PORT = 31032;
//    public static final String WALLET_IP = "localhost";
//    public static final int WALLET_PORT = 9870;

    @Override
    public void run() {
        logger.info("Thread started: {}", Thread.currentThread().getName());
        ManagedChannel channel = ManagedChannelBuilder.forAddress(WALLET_IP, WALLET_PORT).usePlaintext().build();
        MicroServiceGrpc.MicroServiceBlockingStub blockingStub = MicroServiceGrpc.newBlockingStub(channel);
        ResponsePr responsePr = blockingStub.generalCall(RequestPr.newBuilder()
                .setProtocolVersion("v1")
                .setLangauge("en")
                .setHostId(3010)
                .setHostReqId(278800001754L)
                .setHostReqTime(1609052230L)
                .setOpCode(310)
                .setMobileNo("05520000020")
                .setClientTypeInfo("{\"os\": 1, \"version\": \"1.0.0\", \"IP\": \"172.22.0.1\", \"distribution\": 3001}")
                .setPayload("{\"wal\":5}")
                .setAccountId("164")
                .build());
        channel.shutdown();
    }
}