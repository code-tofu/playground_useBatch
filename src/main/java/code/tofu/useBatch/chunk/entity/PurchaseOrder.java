package code.tofu.useBatch.chunk.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="purchase_orders")
public class PurchaseOrder {

    @Id
    String purchaseOrderNumber;

    @Column
    Date orderDatetime;

    @Column
    String vendorId; //Many to Many, theoretically

    @Column
    Double totalCost;

}


