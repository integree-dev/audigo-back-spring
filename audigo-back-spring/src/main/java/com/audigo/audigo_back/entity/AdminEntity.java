package com.audigo.audigo_back.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "admin")
@Table(name = "admin", schema = "users")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동생성
    @Column(name = "a_idx")
    private Integer aIdx;

    @Column(name = "org_cd")
    private String orgCd;

    @Column(name = "cmp_cd")
    private String cmpCd;

    @Column(name = "dept_cd")
    private String deptCd;

    @Column(name = "id")
    private String id;

}
