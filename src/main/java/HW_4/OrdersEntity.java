package HW_4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "main", catalog = "")
public class OrdersEntity {
    private short orderId;
    private int quantity;
    private short customer;
    private short product;

    @Column(name = "customer_id")
    public short getCustomer() {
        return customer;
    }

    public void setCustomer(short customer) {
        this.customer = customer;
    }

    @Column(name = "product_id")
    public short getProduct() {
        return product;
    }

    public void setProduct(short product) {
        this.product = product;
    }

    @Id
    @Column(name = "order_id")
    public short getOrderId() {
        return orderId;
    }

    public void setOrderId(short orderId) {
        this.orderId = orderId;
    }

    
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId && customer == that.customer && product == that.product && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, product, quantity);
    }
}
