package com.audigo.audigo_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audigo.audigo_back.dto.request.board.PostBoardRequestDto;
import com.audigo.audigo_back.dto.request.board.PostCommentRequestDto;
import com.audigo.audigo_back.dto.response.CommonResponseDto;
import com.audigo.audigo_back.dto.response.board.GetBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.GetCommentListResponseDto;
import com.audigo.audigo_back.dto.response.board.GetFavoriteListResponseDto;
import com.audigo.audigo_back.dto.response.board.PostBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.PostCommentResponseDto;
import com.audigo.audigo_back.dto.response.board.PutFavoriteResponseDto;
import com.audigo.audigo_back.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Board API", description = "게시판 API")
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    // spring이 실제 injection 하는 객체 BoardService boardService = new BoardServiceImpl(...);
    private final BoardService boardService;

    
    
    /**
     * 선택한 게시물 조회
     * @param bIdx
     * @return
     */
    @Operation(summary = "선택 게시물 조회", description = "게시물 목록 중 선택 시 해당 게시물 정보를 조회.")
    @ApiResponses({
        @ApiResponse(responseCode = "SU", description = "조회성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "NB", description = "NOT_EXISTED_BOARD", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "DBE", description = "DATABASE_ERROR", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{bIdx}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("bIdx") Integer bIdx) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(bIdx);

        return response;
    }

    /**
     * 게시물 등록
     * @param requestBody
     * @param email
     * @return
     */
    @Operation(summary = "게시물 등록", description = "관리자 게시물 등록 기능")
    @ApiResponses({
        @ApiResponse(responseCode = "SU", description = "등록성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "NU", description = "해당 ID 를 가진 관리자가 없음", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "DBE", description = "DATABASE_ERROR", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
            @RequestBody @Valid PostBoardRequestDto requestBody,
            @AuthenticationPrincipal String email) {

        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard_my(requestBody, email);

        return response;
    }

    /**
     * 좋아요 등록
     * @param bIdx
     * @param email
     * @return
     */
    @PutMapping("/{bIdx}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
            @PathVariable("bIdx") Integer bIdx,
            @AuthenticationPrincipal String email) {

        ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(bIdx, email);
        return response;
    }

    /**
     * 등록된 좋아요 조회 List
     * 
     * @param bIdx
     * @return
     */
    @GetMapping("/{bIdx}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
            @PathVariable("bIdx") Integer bIdx) {
        ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(bIdx);

        return response;
    }

    /**
     * 댓글 목록 조회
     * 
     * @param bIdx
     * @return
     */
    @GetMapping("/{bIdx}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
            @PathVariable("bIdx") Integer bIdx) {
        ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(bIdx);

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
    @PostMapping("/{bIdx}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestBody,
            @AuthenticationPrincipal String email,
            @PathVariable("bIdx") Integer boardNum) {

        ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(requestBody, boardNum,
                email);

        return response;
    }

    /**
     * 게시물 전체 가져오기 mybatis
     * @return
     */
    @GetMapping("/get_all")
    public ResponseEntity<CommonResponseDto<List<Map<String, Object>>>> getAllBoard() {
        CommonResponseDto<List<Map<String, Object>>> response = boardService.getAllBoard();
        return ResponseEntity.ok(response);
    }

    /**
     * 게시물 1개 가져오기 mybatis
     * @param brdNum
     * @return
     */
    @GetMapping("/get_one/{brdNum}")
    public ResponseEntity<CommonResponseDto<Map<String, Object>>> getOneBoard(@PathVariable("brdNum") Integer brdNum) {
        CommonResponseDto<Map<String, Object>> response = boardService.getOneBoard(brdNum);
        return ResponseEntity.ok(response);
    }

}
