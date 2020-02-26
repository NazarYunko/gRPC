package com.gRPC_quickstart_bank;

import com.gRPC_quickstart_bank.service.BankService;
import com.gRPC_quickstart_bank.service.UserPrivacyDataService;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;

/**
 * Starts gRPC server with {@link BankService}.
 */
public class BankServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server userPrivacyDataServer = NettyServerBuilder.forPort(8081).addService(new UserPrivacyDataService()).build().start();
        UserPrivacyDataServiceGrpc.UserPrivacyDataServiceBlockingStub userPrivacyDataServiceStub =
                UserPrivacyDataServiceGrpc.newBlockingStub(NettyChannelBuilder.forAddress("localhost", 8081)
                        .usePlaintext()
                        .build());

        BankService bankService = new BankService(userPrivacyDataServiceStub);
        Server bankServer = NettyServerBuilder.forPort(9090)
                .addService(bankService)
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bankServer.shutdownNow();
            userPrivacyDataServer.shutdownNow();
        }));

        bankServer.awaitTermination();
    }
}
