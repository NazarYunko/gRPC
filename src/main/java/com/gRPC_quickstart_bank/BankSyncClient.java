package com.gRPC_quickstart_bank;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Subscribes to bank service with blocking stub and prints its responses to the "standard" output stream.
 */
public class BankSyncClient {

    public static void main(String[] args) {

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        BankServiceGrpc.BankServiceBlockingStub client = BankServiceGrpc.newBlockingStub(channel);

        AccountRequest request = AccountRequest.newBuilder()
                .setClientId(ThreadLocalRandom.current().nextInt(1, 10000))
                .build();

        AccountResponse response = client.getCurrent(request);
        System.out.printf("Blocking client. Current client data for %s%n%s %n", request, response);
    }
}
