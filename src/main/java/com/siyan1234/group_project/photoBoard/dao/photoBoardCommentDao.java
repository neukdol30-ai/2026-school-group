package com.siyan1234.group_project.photoBoard.dao;

import com.siyan1234.group_project.photoBoard.dto.photoBoardCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class photoBoardCommentDao {

    private final JdbcTemplate jdbcTemplate;

    public List<photoBoardCommentDto> findByBoardNo(int boardNo) {
        String sql = """
                SELECT *
                FROM board_comment
                WHERE board_no = ?
                ORDER BY no ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        photoBoardCommentDto.builder()
                                .no(rs.getInt("no"))
                                .boardNo(rs.getInt("board_no"))
                                .memberNo(rs.getInt("member_no"))
                                .content(rs.getString("content"))
                                .regdate(rs.getString("regdate"))
                                .build(),
                boardNo
        );
    }

    public void write(photoBoardCommentDto commentDto) {
        String sql = """
                INSERT INTO board_comment (
                    no,
                    board_no,
                    member_no,
                    content,
                    regdate
                )
                VALUES (
                    board_comment_seq.nextval,
                    ?,
                    ?,
                    ?,
                    SYSDATE
                )
                """;

        jdbcTemplate.update(sql,
                commentDto.getBoardNo(),
                commentDto.getMemberNo(),
                commentDto.getContent()
        );
    }
}