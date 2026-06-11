package com.siyan1234.group_project.photoBoard.dao;

import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoBoardDao {

    private final JdbcTemplate jdbcTemplate;

    // 사진 리뷰 목록
    public List<PhotoBoardDto> findAll() {
        String sql = """
                SELECT *
                FROM board
                WHERE board_type = 'REVIEW'
                ORDER BY no DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                PhotoBoardDto.builder()
                        .no(rs.getInt("no"))
                        .memberNo(rs.getInt("member_no"))
                        .boardType(rs.getString("board_type"))
                        .restaurantNo(rs.getInt("restaurant_no"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .rating(rs.getDouble("rating"))
                        .hit(rs.getInt("hit"))
                        .likeCount(rs.getInt("like_count"))
                        .regdate(rs.getString("regdate"))
                        .updateDate(rs.getString("update_date"))
                        .build()
        );
    }

    // 사진 리뷰 상세보기
    public PhotoBoardDto findByNo(int no) {
        String sql = """
                SELECT *
                FROM board
                WHERE no = ?
                AND board_type = 'REVIEW'
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        PhotoBoardDto.builder()
                                .no(rs.getInt("no"))
                                .memberNo(rs.getInt("member_no"))
                                .boardType(rs.getString("board_type"))
                                .restaurantNo(rs.getInt("restaurant_no"))
                                .title(rs.getString("title"))
                                .content(rs.getString("content"))
                                .rating(rs.getDouble("rating"))
                                .hit(rs.getInt("hit"))
                                .likeCount(rs.getInt("like_count"))
                                .regdate(rs.getString("regdate"))
                                .updateDate(rs.getString("update_date"))
                                .build(),
                no
        );
    }

    // 사진 리뷰 등록
    public void insert(PhotoBoardDto photeBoardDto) {
        String sql = """
                INSERT INTO board (
                    no,
                    member_no,
                    board_type,
                    restaurant_no,
                    title,
                    content,
                    rating
                ) VALUES (
                    board_seq.NEXTVAL,
                    ?,
                    'REVIEW',
                    ?,
                    ?,
                    ?,
                    ?
                )
                """;

        jdbcTemplate.update(sql,
                photeBoardDto.getMemberNo(),
                photeBoardDto.getRestaurantNo(),
                photeBoardDto.getTitle(),
                photeBoardDto.getContent(),
                photeBoardDto.getRating()
        );
    }

    // 사진 리뷰 수정
    public void update(PhotoBoardDto photeBoardDto) {
        String sql = """
                UPDATE board
                SET title = ?,
                    content = ?,
                    rating = ?,
                    update_date = SYSDATE
                WHERE no = ?
                AND board_type = 'REVIEW'
                """;

        jdbcTemplate.update(sql,
                photeBoardDto.getTitle(),
                photeBoardDto.getContent(),
                photeBoardDto.getRating(),
                photeBoardDto.getNo()
        );
    }

    // 사진 리뷰 삭제
    public void delete(int no) {
        String sql = """
                DELETE FROM board
                WHERE no = ?
                AND board_type = 'REVIEW'
                """;

        jdbcTemplate.update(sql, no);
    }
}