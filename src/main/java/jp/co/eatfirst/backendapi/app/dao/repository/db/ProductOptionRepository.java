package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.ProductOption;
import jp.co.eatfirst.backendapi.app.dao.entity.ProductOptionPK;
import jp.co.eatfirst.backendapi.app.dao.entity.dto.ProductOptionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, ProductOptionPK> {

//    @Query(value = "select new jp.co.eatfirst.backendapi.app.dao.entity.dto.ProductOptionDto(b.productId, a) " +
//            "from OptionGroup a, ProductOption b, ProductCategory c where a.optionGroupId = b.optionGroupId and b.productId = c.productId and c.categoryId = :categoryId" +
//            " order by a.optionGroupId asc")
//    List<ProductOptionDto> findByCategoryId(Long categoryId);
}
