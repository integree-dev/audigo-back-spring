package com.audigo.audigo_back.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    List<Map<String, Object>> selectAllBoard();
}
