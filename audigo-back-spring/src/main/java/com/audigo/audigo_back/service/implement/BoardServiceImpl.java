package com.audigo.audigo_back.service.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.audigo.audigo_back.dto.request.board.PostBoardRequestDto;
import com.audigo.audigo_back.dto.request.board.PostCommentRequestDto;
import com.audigo.audigo_back.dto.response.CommonResponseDto;
import com.audigo.audigo_back.dto.response.ResponseDto;
import com.audigo.audigo_back.dto.response.board.GetBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.GetCommentListResponseDto;
import com.audigo.audigo_back.dto.response.board.GetFavoriteListResponseDto;
import com.audigo.audigo_back.dto.response.board.PostBoardResponseDto;
import com.audigo.audigo_back.dto.response.board.PostCommentResponseDto;
import com.audigo.audigo_back.dto.response.board.PutFavoriteResponseDto;
import com.audigo.audigo_back.entity.BoardEntity;
import com.audigo.audigo_back.entity.CommentEntity;
import com.audigo.audigo_back.entity.FavoriteEntity;
import com.audigo.audigo_back.entity.ImageEntity;
import com.audigo.audigo_back.mapper.BoardMapper;
import com.audigo.audigo_back.repository.BoardRepository;
import com.audigo.audigo_back.repository.CommentRepository;
import com.audigo.audigo_back.repository.FavoriteRepository;
import com.audigo.audigo_back.repository.ImageRepository;
import com.audigo.audigo_back.repository.UserRepository;
import com.audigo.audigo_back.repository.resultSet.GetBoardResultSet;
import com.audigo.audigo_back.repository.resultSet.GetCommentListResultSet;
import com.audigo.audigo_back.repository.resultSet.GetFavoriteListResultSet;
import com.audigo.audigo_back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository; //jpa case
    private final BoardMapper boardMapper;         //mybatis case
    private final UserRepository userRepository;
    private final ImageRepository imgRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;


    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        try {
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail)
                return PostBoardResponseDto.notExistedUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            // 1.게시물 내용 저장
            boardRepository.save(boardEntity);

            // 2.저장 후 auto 생성된 Board number 를 가져옴
            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }
            // 3.게시물과 연결된 이미지들 저장
            imgRepository.saveAll(imageEntities);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            resultSet = boardRepository.getBoard(boardNumber);// BoardRepository interface sql 호출
            if (resultSet == null)
                return GetBoardResponseDto.noExistBoard();

            imageEntities = imgRepository.findByBoardNumber(boardNumber);

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBoardResponseDto.success(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
        try {
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return PutFavoriteResponseDto.notExistedUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return PutFavoriteResponseDto.notExistedBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();// 좋아요 증가
            } else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();// 좋아요 감소
            }

            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
        // interface 객체 List
        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard)
                return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);
            if (resultSets == null)
                return GetFavoriteListResponseDto.noExistBoard();

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    /**
     * 댓글 목록 조회
     */
    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

        // interface 객체 List 배열
        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard)
                return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRepository.getCommentList(boardNumber);
            if (resultSets == null)
                return GetCommentListResponseDto.noExistBoard();

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
    }

    /**
     * 댓글등록 후 success return
     */
    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            PostCommentRequestDto dto, Integer boardNum, String email) {
        try {
            // 1)exception 처리
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNum);
            if (boardEntity == null)
                return PostCommentResponseDto.notExistedBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return PostCommentResponseDto.notExistedUser();

            // 2)comment 등록
            CommentEntity cmtEntity = new CommentEntity(dto, boardNum, email);
            commentRepository.save(cmtEntity);

            // 3)comment 수 증가
            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }

    /**
     * 게시판 정보 전체 가져오기
     */
    @Override
    public CommonResponseDto<List<Map<String, Object>>> getAllBoard() {
        List<Map<String, Object>> boards = new ArrayList<Map<String, Object>>();
        try {
            boards = boardMapper.selectAllBoard();
            if (boards == null)
                return CommonResponseDto.failure("ND", "No Data");
                
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.databaseError();
        }
        
        return CommonResponseDto.success(boards);
    }

    /**
     * 게시판 정보 하나 가져오기
     */
    @Override
    public CommonResponseDto<Map<String, Object>> getOneBoard(Integer boardNum) {
        Map<String, Object> board = new HashMap<String, Object>();
        try {
            board = boardMapper.selectOneBoard(boardNum);
            if (board == null)
                return CommonResponseDto.failure("ND", "No Data");
                
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponseDto.databaseError();
        }
        
        // @Param("articleId") String articleId
        //return CommonResponseDto.<Map<String, Object>>success(board); //명시적 지정1
        //CommonResponseDto<Map<String, Object>> result = CommonResponseDto.success(board);//명시적 지정2
        //return result; //명시적 지정2
        return CommonResponseDto.success(board); //자동으로 타입 추론
    }

}