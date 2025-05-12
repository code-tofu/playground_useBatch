package code.tofu.useBatch.chunk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlatFileInput {

    String purchaseOrderNumber;
    Date orderDateTime;
    FlatFileVendorDetails fileFlatFileVendorDetails;
    List<FlatFileLineItem> lineItems;


}