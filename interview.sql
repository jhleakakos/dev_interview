CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES customers(customer_id),
    status TEXT NOT NULL -- Example values: 'pending', 'completed', 'canceled'
);

CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(order_id),
    product_id INT NOT NULL,  -- Assume product_id references a products table (not included for simplicity)
    quantity INT NOT NULL CHECK (quantity >= 0)
);




SELECT c.*, o.*, oi.*
FROM customers c
JOIN (
    SELECT * FROM orders
) o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
WHERE 
    o.status = 'completed' 
    AND oi.quantity > 0 
    AND oi.product_id IN (
        SELECT product_id FROM order_items WHERE quantity > 0
    )
ORDER BY c.customer_id;