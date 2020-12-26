package com.superpay.wallet.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ir.asan.micro.MicroServiceGrpc;
import ir.asan.micro.RequestPr;
import ir.asan.micro.ResponsePr;
import org.junit.jupiter.api.Test;

public class MicroServiceTest {

    public static final String WALLET_IP = "172.25.25.73";
    public static final int WALLET_PORT = 31033;

    // @Test
    void given_when_then() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(WALLET_IP, WALLET_PORT).usePlaintext().build();
        MicroServiceGrpc.MicroServiceBlockingStub blockingStub = MicroServiceGrpc.newBlockingStub(channel);
        ResponsePr responsePr = blockingStub.generalCall(
                RequestPr.newBuilder()
                        .setAccountId("185")
                        .build()
        );
    }
}
