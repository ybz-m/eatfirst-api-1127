package jp.co.eatfirst.backendapi.app.dao.repository.db;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreStaffRepository extends JpaRepository<StoreStaff, Long> {
    StoreStaff findByStaffName(String staffName);
    StoreStaff findByStaffId(Long staffId);
    List<StoreStaff> findByStoreId(Long storeId);
}
