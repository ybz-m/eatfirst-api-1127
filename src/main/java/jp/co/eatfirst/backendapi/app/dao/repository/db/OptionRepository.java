package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query(value = "select a " +
            "from Option a, ProductOption c, ProductCategory d where a.optionGroupId = c.optionGroupId and c.productId = d.productId and d.categoryId = :categoryId" +
            " order by a.optionGroupId asc, a.sortOrder asc")
    List<Option> findByCategoryId(Long categoryId);
}
