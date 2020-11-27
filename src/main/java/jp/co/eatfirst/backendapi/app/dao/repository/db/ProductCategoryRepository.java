package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.ProductCategory;
import jp.co.eatfirst.backendapi.app.dao.entity.ProductCategoryPK;
import jp.co.eatfirst.backendapi.app.dao.entity.ProductOption;
import jp.co.eatfirst.backendapi.app.dao.entity.ProductOptionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryPK> {

    public List<ProductCategory> findByProductId(Long productId);


    @Modifying
    @Query("delete from ProductCategory a where a.productId = :productId")
    public void deleteByProductId(Long productId);

}
