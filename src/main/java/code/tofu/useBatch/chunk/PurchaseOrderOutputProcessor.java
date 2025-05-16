package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.entity.PurchaseOrder;
import code.tofu.useBatch.chunk.model.FlatFileInput;
import code.tofu.useBatch.chunk.model.FlatFileLineItem;
import code.tofu.useBatch.chunk.model.FlatFileOutput;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class PurchaseOrderOutputProcessor implements ItemProcessor<PurchaseOrder,FlatFileOutput> {

    @Override
    public FlatFileOutput process(PurchaseOrder input) {

        return FlatFileOutput.builder()
                .purchaseOrderNumber(input.getPurchaseOrderNumber())
                .orderDatetime(input.getOrderDatetime())
                .vendorId(input.getVendorId())
                .totalCost(input.getTotalCost())
                .build();
    }

}


