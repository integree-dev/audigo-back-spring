package com.audigo.audigo_back.repository;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.audigo.audigo_back.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, BigInteger>{
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    MemberEntity findByEmail(String email);

    /**
     * DB 함수호출
     * @param email
     * @param nickname
     * @param birthDt
     * @param gender
     * @param snsDiv
     * @param invitationCd
     * @param inviterCd
     * @param missionYn
     * @param pushTkn
     * @param snsVal
     * @param model
     * @param appVers
     * @param osVers
     * @param osName
     * @param dupliTkn
     * @param lang
     * @param mobileNumb
     * @param regionCd
     * @param pushAlive
     */
    @Query(value = "SELECT * FROM users.register_member(" +
           ":email, :nickname, :birthDt, :gender, :snsDiv, " +
           ":invitationCd, :inviterCd, :missionYn, :pushTkn, :snsVal, " +
           ":model, :appVers, :osVers, :osName, :dupliTkn, " +
           ":lang, :mobileNumb, :regionCd, :pushAlive)", nativeQuery = true)
    Map<String, Object> registerMember(
        @Param("email") String email,
        @Param("nickname") String nickname,
        @Param("birthDt") String birthDt,
        @Param("gender") String gender,
        @Param("snsDiv") String snsDiv,
        @Param("invitationCd") String invitationCd,
        @Param("inviterCd") String inviterCd,
        @Param("missionYn") String missionYn,
        @Param("pushTkn") String pushTkn,
        @Param("snsVal") String snsVal,
        @Param("model") String model,
        @Param("appVers") String appVers,
        @Param("osVers") String osVers,
        @Param("osName") String osName,
        @Param("dupliTkn") String dupliTkn,
        @Param("lang") String lang,
        @Param("mobileNumb") String mobileNumb,
        @Param("regionCd") String regionCd,
        @Param("pushAlive") String pushAlive
    );
}