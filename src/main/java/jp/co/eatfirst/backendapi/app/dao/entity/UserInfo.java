package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity{
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "identifier")
    private String identifier;

  }
