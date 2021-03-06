package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInfomationRepository extends JpaRepository<StoreInformation, Long> {
}
