package code.tofu.useBatch.chunk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatFileOutput {
    String purchaseOrderNumber;
    Date orderDatetime;
    String vendorId;
    Double totalCost;
}