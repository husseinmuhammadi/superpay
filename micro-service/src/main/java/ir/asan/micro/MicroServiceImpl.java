package ir.asan.micro;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicroServiceImpl extends MicroServiceGrpc.MicroServiceImplBase {
    Logger logger = LoggerFactory.getLogger(MicroServiceImpl.class);

    @Override
    public void generalCall(RequestPr request, StreamObserver<ResponsePr> responseObserver) {
        logger.info("General Call");
        // super.generalCall(request, responseObserver);
        responseObserver.onNext(ResponsePr.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void paymentNotify(RequestPr request, StreamObserver<ResponsePr> responseObserver) {
        super.paymentNotify(request, responseObserver);
    }

    @Override
    public void inquiry(RequestPr request, StreamObserver<ResponsePr> responseObserver) {
        super.inquiry(request, responseObserver);
    }
}
