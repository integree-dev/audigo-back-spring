package com.audigo.audigo_back.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.audigo.audigo_back.dto.request.board.PostBoardRequestDto;
import com.audigo.audigo_back.dto.request.board.PostCommentRequestDto;
import com.audigo.audigo_back.dto.response.CommonResponseDto;
import com.audigo.audigo_back.dto.response.board.GetBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.GetCommentListResponseDto;
import com.audigo.audigo_back.dto.response.board.GetFavoriteListResponseDto;
import com.audigo.audigo_back.dto.response.board.PostBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.PostCommentResponseDto;
import com.audigo.audigo_back.dto.response.board.PutFavoriteResponseDto;

public interface BoardService {
    // 게시물 선택 시 해당 게시물 상세조회
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);

    // 좋아요 목록 조회
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);

    // 댓글 목록 조회
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);

    // 게시물 등록
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

    // 좋아요 등록
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

    // 댓글 등록
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber,
            String email);

    // 게시물 조회
    CommonResponseDto<List<Map<String, Object>>> getAllBoard();
    
}
