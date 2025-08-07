package com.audigo.audigo_back.entity;

import java.math.BigInteger;

import com.audigo.audigo_back.dto.request.auth.SignUpRequestDto;

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
@Entity(name = "members")
@Table(name = "members", schema = "users")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동생성
    @Column(name = "m_idx")
    private BigInteger mIdx;

    @Column(name = "join_dt", insertable = false, updatable = false)
    private String joinDt;//자동생성
    
    @Column(name = "seq", insertable = false, updatable = false)
    private int seq;//자동생성
    
    @Column(name = "logical_id", insertable = false, updatable = false)
    private String logicalId;//자동생성
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "nickname")
    private String nickname;
    
    @Column(name = "birth_dt")
    private String birthDt;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "sns_div")
    private String snsDiv;
    
    @Column(name = "mobile_numb")
    private String mobileNumb;

    public MemberEntity(SignUpRequestDto dto) {
        this.status = dto.getStatus();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
        this.birthDt = dto.getBirthDt();
        this.gender = dto.getGender();
        this.snsDiv = dto.getSnsDiv();
        this.mobileNumb = dto.getMobileNumb();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[status: " + status + "]"
                + "[email: " + email + "]"
                + "[nickname: " + nickname + "]"
                + "[birthDt: " + birthDt + "]"
                + "[gender: " + gender + "]"
                + "[snsDiv: " + snsDiv + "]"
                + "[mobileNumb: " + mobileNumb + "]";
    }
}
