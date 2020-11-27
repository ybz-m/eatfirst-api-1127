package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "option_group")
public class OptionGroup extends BaseEntity{
    @Id
    @Column(name = "option_group_id")
    private Long optionGroupId;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_cn")
    private String nameCn;

}
