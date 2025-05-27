package com.grpc.service;

import com.gprc.model.StockRequest;
import com.gprc.model.StockResponse;
import com.gprc.model.StockTradingServiceGrpc;
import com.grpc.entity.Stock;
import com.grpc.repository.StockRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {
    private final StockRepository stockRepository;

    public StockTradingServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
//        super.getStockPrice(request, responseObserver);
        Stock stock = stockRepository.findByStockSymbol(request.getStockSymbol());
        System.out.println(stock);
        StockResponse stockResponse = StockResponse.newBuilder()
                .setPrice(stock.getPrice())
                .setTimestamp(stock.getLastUpdated().toString())
                .setStockSymbol(stock.getStockSymbol())
                .build();
        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();
    }
}
