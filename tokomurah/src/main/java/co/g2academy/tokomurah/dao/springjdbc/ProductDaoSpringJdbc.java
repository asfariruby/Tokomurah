/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.tokomurah.dao.springjdbc;

import co.g2academy.tokomurah.mapper.ProductRowMapper;
import co.g2academy.tokomurah.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoSpringJdbc {

    private static final String SELECT
            = "select * from t_product";
    private static final String SELECT_BY_ID
            = "select * from t_product where id=?";
    private static final String INSERT
            = "insert into t_product(name, description, price, stock) values(?, ?, ?, ?)";
    private static final String UPDATE
            = "update t_product set name = ?, description = ?,  price= ?, stock= ? where id = ?";
    private static final String DELETE
            = "delete from t_product";
    private static final String DELETE_BY_ID
            = "delete from t_product where id=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ProductRowMapper rowMapper = new ProductRowMapper();

    public List<Product> getProducts() {
        return jdbcTemplate.query(SELECT, rowMapper);
    }

    public Product getProductById(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id);
    }

    public void save(Product p) {
        if (p.getId() == null) {
            jdbcTemplate.update(
                    INSERT, p.getName(), p.getDescription(), p.getPrice(), p.getStock());
        } else {
            jdbcTemplate.update(
                    UPDATE, p.getName(), p.getDescription(), p.getPrice(), p.getStock(), p.getId());
        }
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    public void delete() {
        jdbcTemplate.update(DELETE);
    }
}
