package com.audigo.audigo_back.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
    //목록 가져오기
    List<Map<String, Object>> selectAllBoard();

    //하나만 가져오기
    Map<String, Object> selectOneBoard(@Param("brdNum") Integer brdNum);
}
