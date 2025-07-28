package com.audigo.audigo_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audigo.audigo_back.dto.request.board.PostBoardRequestDto;
import com.audigo.audigo_back.dto.request.board.PostCommentRequestDto;
import com.audigo.audigo_back.dto.response.board.GetBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.GetCommentListResponseDto;
import com.audigo.audigo_back.dto.response.board.GetFavoriteListResponseDto;
import com.audigo.audigo_back.dto.response.board.PostBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.PostCommentResponseDto;
import com.audigo.audigo_back.dto.response.board.PutFavoriteResponseDto;
import com.audigo.audigo_back.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    // spring이 실제 injection 하는 객체 BoardService boardService = new BoardServiceImpl(...);
    private final BoardService boardService;

    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);

        return response;
    }

    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
            @RequestBody @Valid PostBoardRequestDto requestBody,
            @AuthenticationPrincipal String email) {

        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);

        return response;
    }

    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
            @PathVariable("boardNumber") Integer boardNumber,
            @AuthenticationPrincipal String email) {

        ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(boardNumber, email);
        return response;
    }

    /**
     * 좋아요 목록 조회
     * 
     * @param boardNumber
     * @return
     */
    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(boardNumber);

        return response;
    }

    /**
     * 댓글 목록 조회
     * 
     * @param boardNumber
     * @return
     */
    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(boardNumber);

        return response;
    }

    /**
     * 댓글 등록
     * 
     * @param requestBody
     * @param email
     * @param boardNum
     * @return
     */
    @PostMapping("/{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestBody,
            @AuthenticationPrincipal String email,
            @PathVariable("boardNumber") Integer boardNum) {

        ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(requestBody, boardNum,
                email);

        return response;
    }

}
