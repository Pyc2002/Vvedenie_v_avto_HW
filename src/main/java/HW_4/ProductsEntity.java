package HW_4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "main", catalog = "")
public class ProductsEntity {
    private short productId;
    private String productName;
    private int productQuantity;
    private float productPrice;
    private short manufacturer;


    @Column(name = "manufacturer_id")
    public short getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(short manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Id
    @Column(name = "product_id")
    public short getProductId() {
        return productId;
    }

    public void setProductId(short productId) {
        this.productId = productId;
    }


    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    @Column(name = "product_quantity")
    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }


    @Column(name = "product_price")
    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && manufacturer == that.manufacturer && Objects.equals(productName, that.productName) && Objects.equals(productQuantity, that.productQuantity) && Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productQuantity, productPrice, manufacturer);
    }
}
