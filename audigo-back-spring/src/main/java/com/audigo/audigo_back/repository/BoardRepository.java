package com.audigo.audigo_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.audigo.audigo_back.entity.BoardEntity;
import com.audigo.audigo_back.repository.resultSet.GetBoardResultSet;

//PK type is Integer
@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    boolean existsByBoardNumber(Integer boardNumber);

    BoardEntity findByBoardNumber(Integer boardNumber);

    /**
     * BoardRepository 인스턴스로 쿼리 내부에서 select 후 update 같은 sql transaction도 가능함.
     * 
     * @param boardNumber
     * @return
     */
    @Query(value = "SELECT " +
            "B.board_number AS boardNumber " +
            ", B.title AS title " +
            ", B.content AS content " +
            ", B.write_datetime AS writerDatetime " +
            ", B.writer_email AS writerEmail " +
            ", U.nickname AS writerNickname " +
            ", U.profile_image AS writerProfileImage " +
            "FROM board AS B " +
            "INNER JOIN user AS U " +
            "ON B.writer_email = U.email " +
            "WHERE board_number = ?1 ", nativeQuery = true)
    GetBoardResultSet getBoard(Integer boardNumber);
}