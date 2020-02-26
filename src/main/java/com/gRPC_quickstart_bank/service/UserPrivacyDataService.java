package com.gRPC_quickstart_bank.service;

import com.gRPC_quickstart_bank.AccountRequest;
import com.gRPC_quickstart_bank.UserPrivacyData;
import com.gRPC_quickstart_bank.UserPrivacyDataServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Provides custom operations to retrieve user privacy data.
 */
public class UserPrivacyDataService extends UserPrivacyDataServiceGrpc.UserPrivacyDataServiceImplBase {

    @Override
    public void getCurrent(AccountRequest request, StreamObserver<UserPrivacyData> responseObserver) {

        UserPrivacyData userPrivacyData = UserPrivacyData.newBuilder()
                .setClientFirstName(RandomStringUtils.randomAlphabetic(10))
                .setClientLastName(RandomStringUtils.randomAlphabetic(10))
                .build();

        responseObserver.onNext(userPrivacyData);
        responseObserver.onCompleted();
    }
}
