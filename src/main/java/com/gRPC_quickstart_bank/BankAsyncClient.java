package com.gRPC_quickstart_bank;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Subscribes to bank service with async stub and prints its responses to the "standard" output stream.
 */
public class BankAsyncClient {

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        BankServiceGrpc.BankServiceStub client = BankServiceGrpc.newStub(channel);

        AccountRequest request = AccountRequest.newBuilder()
                .setClientId(ThreadLocalRandom.current().nextInt(1, 10000))
                .build();


        Semaphore exitSemaphore = new Semaphore(0);
        client.getCurrent(request, new StreamObserver<AccountResponse>() {

            @Override
            public void onNext(AccountResponse value) {
                System.out.printf("Async client. Current client data for %s%n%s %n", request, value);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                System.out.printf("Blocking client. Can not get client data for %s", request);
                exitSemaphore.release();
            }

            @Override
            public void onCompleted() {
                System.out.print("Async client. Stream completed%n");
                exitSemaphore.release();
            }
        });

        exitSemaphore.acquire();
    }
}
