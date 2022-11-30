/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.dao.jdbc;

import co.g2academy.tokomurah.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSelectDaoJdbc {

    @Autowired
    private DataSource dataSource;
    private static final String SELECT
            = "select * from t_product";
    private static final String SELECT_BY_ID
            = "select * from t_product where id=?";

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(SELECT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(getProductFromResultSet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public Product getProductById(Integer id) {
        try {
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(SELECT_BY_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getProductFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } return null;
    } 
        public Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getInt("price"));
        p.setStock(rs.getInt("stock"));
        return p;
    }
}
