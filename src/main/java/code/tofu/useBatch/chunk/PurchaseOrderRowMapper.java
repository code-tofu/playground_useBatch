package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.entity.PurchaseOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PurchaseOrderRowMapper implements RowMapper<PurchaseOrder> {
    @Override
    public PurchaseOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PurchaseOrder.builder().purchaseOrderNumber(rs.getString("purchase_order_number"))
                .orderDatetime(rs.getTimestamp("order_datetime"))
                .vendorId(rs.getString("vendor_id"))
                .totalCost(rs.getDouble("total_cost")).build();
    }
}
