package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store_staff")
public class StoreStaff extends BaseEntity{
    @Id
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "staff_no")
    private String staffNo;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "display_flg")
    private Integer displayFlg;

    @Column(name = "authority")
    private Integer authority;

    @Column(name = "password")
    private String password;

}
