/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.dao.jdbc;

import co.g2academy.tokomurah.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteDaoJdbc {

    private static final String DELETE
            = "delete from t_product where id=?";
    private static final String DELETE_BY_ID
            = "delete from t_product where id=?";
    private PreparedStatement deletePs;

    @Autowired
    private DataSource dataSource;
    public void deleteProductById(Integer id) {
        try {
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(DELETE_BY_ID);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProducts() {
        try {
            Connection c = dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(DELETE);
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
