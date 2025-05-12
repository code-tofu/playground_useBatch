package code.tofu.useBatch.chunk.model;
import lombok.Data;

@Data
public class FlatFileLineItem {

    Long itemId;
    String itemName;
    Double unitCost;
    Integer quantity;
    Double totalCost;


}
