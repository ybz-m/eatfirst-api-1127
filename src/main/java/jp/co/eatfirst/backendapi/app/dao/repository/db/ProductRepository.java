package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select a " +
            "from Product a, ProductCategory b where a.productId = b.productId  and b.categoryId = :categoryId" +
            " order by b.sortOrder asc")
    List<Product> findByCategoryId(Long categoryId);
}
