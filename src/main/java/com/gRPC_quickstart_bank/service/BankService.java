package com.gRPC_quickstart_bank.service;

import com.gRPC_quickstart_bank.AccountRequest;
import com.gRPC_quickstart_bank.AccountResponse;
import com.gRPC_quickstart_bank.BankServiceGrpc;
import com.gRPC_quickstart_bank.UserPrivacyDataServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides custom operations for bank.
 */
public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private final UserPrivacyDataServiceGrpc.UserPrivacyDataServiceBlockingStub userPrivacyDataService;

    public BankService(final UserPrivacyDataServiceGrpc.UserPrivacyDataServiceBlockingStub userPrivacyDataService) {
        this.userPrivacyDataService = userPrivacyDataService;
    }

    @Override
    public void getCurrent(AccountRequest request, StreamObserver<AccountResponse> responseObserver) {

        AccountResponse response = AccountResponse.newBuilder()
                .setUserPrivacyData(userPrivacyDataService.getCurrent(request))
                .setTariff(RandomStringUtils.randomAlphabetic(10))
                .setAmountOfMoney(ThreadLocalRandom.current().nextInt(1, 10000))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
