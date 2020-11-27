package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "code_mst")
public class CodeMst extends BaseEntity{
    @Id
    @Column(name = "code_mst_id")
    private Long codeMstId;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "code_nm")
    private String codeNm;

    @Column(name = "code_val")
    private String codeVal;

}
