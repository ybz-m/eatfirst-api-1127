package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.OrderDetailOption;
import jp.co.eatfirst.backendapi.app.dao.entity.OrderDetailOptionPK;
import jp.co.eatfirst.backendapi.app.dao.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailOptionRepository extends JpaRepository<OrderDetailOption, OrderDetailOptionPK> {
}
