/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.dao.jdbc;

import co.g2academy.tokomurah.model.Product;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSaveDaoJdbc {

    private static final String INSERT
            = "insert into t_product(id, name, description, price, stock) values(?, ?, ?, ?, ?)";
    private static final String UPDATE
            = "update t_product set name = ?, description = ?,  price= ?, stock= ?, where id = ?";
    private PreparedStatement updatePs;

    @Autowired
    private DataSource dataSource;

    public void save(Product product) {
        try {
            Connection c = dataSource.getConnection();
            if (product.getId() == null) {
                PreparedStatement ps = c.prepareStatement(INSERT);
                setParameterToPreparedStatement(ps, product);
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement(UPDATE);
                setParameterToPreparedStatement(ps, product);
                ps.setInt(5, product.getId());
                ps.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setParameterToPreparedStatement(
            PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setString(2, p.getDescription());
        ps.setInt(3, p.getPrice());
        ps.setInt(4, p.getStock());
    }
}
