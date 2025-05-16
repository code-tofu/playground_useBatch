CREATE TABLE purchase_orders (
    purchase_order_number VARCHAR(7) PRIMARY KEY,
    order_datetime DATETIME,
    vendor_id VARCHAR(6),
    total_cost DECIMAL(10, 2)
);