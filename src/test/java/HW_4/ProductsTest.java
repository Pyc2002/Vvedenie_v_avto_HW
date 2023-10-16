package HW_4;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ProductsTest extends AbstractTest {

    @Test
    @Order(1)
    void getProductTest() throws SQLException {
        //given
        String sql = "SELECT * FROM products WHERE product_name ='пылесос'";
        Statement stmt  = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery("SELECT * FROM products").addEntity(ProductsEntity.class);
        //then
        Assertions.assertEquals(2, countTableSize);
        Assertions.assertEquals(5, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"телевизор, 50", "холодильник, 25", "пылесос, 30"})
    void getProductsByManufacturerTest(String product_name, int product_quantity) throws SQLException {
        //given
        String sql = "SELECT * FROM products WHERE manufacturer_id=1 AND product_name='" + product_name + "'";
        Statement stmt  = getConnection().createStatement();
        String nameString = "";
        int quantity = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            nameString = rs.getString(2);
            quantity = rs.getInt(3);
        }
        //then
        Assertions.assertEquals(product_name, nameString);
        Assertions.assertEquals(product_quantity, quantity );
    }

    @Test
    @Order(3)
    void addProductTest() {
        //given
        ProductsEntity entity = new ProductsEntity();
        entity.setProductId((short) 6);
        entity.setProductName("чайник");
        entity.setProductPrice((float) 1500);
        entity.setProductQuantity(20);
        entity.setManufacturer((short) 2);
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id="+6).addEntity(ProductsEntity.class);
        ProductsEntity productEntity = (ProductsEntity) query.uniqueResult();
        final Query query1 = getSession().createSQLQuery("SELECT * FROM products").addEntity(ProductsEntity.class); // Посмотреть как добавить к query

        //then
        Assertions.assertNotNull(productEntity);
        Assertions.assertEquals("чайник", productEntity.getProductName());
        Assertions.assertEquals(6, query1.list().size());
    }
    @Test
    @Order(4)
    void deleteProductTest() {
        //given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id=" + 6).addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productsEntity = (Optional<ProductsEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(productsEntity.isPresent());
        //when
        Session session = getSession();
        session.beginTransaction();
        session.delete(productsEntity.get());
        session.getTransaction().commit();
        final Query query1 = getSession().createSQLQuery("SELECT * FROM products").addEntity(ProductsEntity.class); // Посмотреть как добавить к query
        //then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id=" + 6).addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productEntityAfterDelete = (Optional<ProductsEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(productEntityAfterDelete.isPresent());
        Assertions.assertEquals(5, query1.list().size());
    }
}
