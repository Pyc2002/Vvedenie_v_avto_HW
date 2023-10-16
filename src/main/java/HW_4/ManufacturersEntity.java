package HW_4;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "manufacturers", schema = "main", catalog = "")
public class ManufacturersEntity {
    private short manufacturerId;
    private String manufacturerName;

    @Id
    @Column(name = "manufacturer_id")
    public short getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(short manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    
    @Column(name = "manufacturer_name")
    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManufacturersEntity that = (ManufacturersEntity) o;
        return manufacturerId == that.manufacturerId && Objects.equals(manufacturerName, that.manufacturerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerId, manufacturerName);
    }
}
