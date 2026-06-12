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
                        .imageUrl(rs.getString("image_url"))
                        .build()
        );
    }

    public void write(PhotoBoardDto photoBoardDto) {
        String sql = """
                INSERT INTO board (
                    no,
                    member_no,
                    board_type,
                    restaurant_no,
                    title,
                    content,
                    rating,
                    hit,
                    like_count,
                    regdate,
                    update_date,
                    image_url
                )
                VALUES (
                    board_seq.nextval,
                    ?,
                    'REVIEW',
                    ?,
                    ?,
                    ?,
                    ?,
                    0,
                    0,
                    SYSDATE,
                    SYSDATE,
                    ?
                )
                """;

        jdbcTemplate.update(sql,
                photoBoardDto.getMemberNo(),
                photoBoardDto.getRestaurantNo(),
                photoBoardDto.getTitle(),
                photoBoardDto.getContent(),
                photoBoardDto.getRating(),
                photoBoardDto.getImageUrl()
        );
    }

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
                                .imageUrl(rs.getString("image_url"))
                                .build(),
                no
        );
    }

    public void delete(int no) {
        String sql = """
                DELETE FROM board
                WHERE no = ?
                AND board_type = 'REVIEW'
                """;

        jdbcTemplate.update(sql, no);
    }

    public void like(int no) {
        String sql = """
                UPDATE board
                SET like_count = like_count + 1
                WHERE no = ?
                AND board_type = 'REVIEW'
                """;

        jdbcTemplate.update(sql, no);
    }
}