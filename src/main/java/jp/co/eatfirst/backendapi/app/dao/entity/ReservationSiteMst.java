package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "reservation_site_mst")
public class ReservationSiteMst extends BaseEntity{
    @Id
    @Column(name = "reservation_site_no")
    private Long reservationSiteNo;

    @Column(name = "reservation_site_name")
    private String reservationSiteName;

}
