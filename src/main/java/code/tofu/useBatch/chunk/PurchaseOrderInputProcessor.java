package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.entity.PurchaseOrder;
import code.tofu.useBatch.chunk.model.FlatFileInput;
import code.tofu.useBatch.chunk.model.FlatFileLineItem;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class PurchaseOrderInputProcessor implements ItemProcessor<FlatFileInput, PurchaseOrder> {

    @Override
    public PurchaseOrder process(FlatFileInput input) {

        return PurchaseOrder.builder().purchaseOrderNumber(input.getPurchaseOrderNumber())
                .orderDatetime(input.getOrderDateTime())
                .vendorId(input.getFileFlatFileVendorDetails().getVendorId())
                .totalCost(tabulateTotalCost(input.getLineItems()))
                .build();
    } //return null if filters

    public Double tabulateTotalCost( List<FlatFileLineItem> inputItems){
        Double orderTotalCost = 0.0;
        for(FlatFileLineItem fileitem: inputItems) {
            orderTotalCost += fileitem.getTotalCost();
        }
        return orderTotalCost;
    }
}


