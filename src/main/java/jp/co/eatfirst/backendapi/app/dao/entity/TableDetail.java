package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "table_detail")
public class TableDetail extends BaseEntity{
    @Id
    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "seat_capacity_max")
    private Long seatCapacityMax;

    @Column(name = "seat_capacity_min")
    private Long seatCapacityMin;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "smoking_setting")
    private Long smokingSetting;

    @Column(name = "seat_type")
    private Long seatType;

    @Column(name = "seat_partition")
    private Long seatPartition;

    @Column(name = "display_flg")
    private Long displayFlg;

}
