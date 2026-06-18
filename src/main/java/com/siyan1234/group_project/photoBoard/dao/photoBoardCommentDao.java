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
                SELECT c.*,
                       m.member_id,
                       m.name AS member_name
                FROM board_comment c
                JOIN member m ON c.member_no = m.no
                WHERE c.board_no = ?
                AND c.is_deleted = 'N'
                ORDER BY c.no ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        photoBoardCommentDto.builder()
                                .no(rs.getInt("no"))
                                .boardNo(rs.getInt("board_no"))
                                .memberNo(rs.getInt("member_no"))
                                .content(rs.getString("content"))
                                .regdate(rs.getString("regdate"))
                                .updateDate(rs.getString("update_date"))
                                .isDeleted(rs.getString("is_deleted"))
                                .memberId(rs.getString("member_id"))
                                .memberName(rs.getString("member_name"))
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
                    regdate,
                    is_deleted
                )
                VALUES (
                    board_comment_seq.nextval,
                    ?,
                    ?,
                    ?,
                    SYSDATE,
                    'N'
                )
                """;

        jdbcTemplate.update(sql,
                commentDto.getBoardNo(),
                commentDto.getMemberNo(),
                commentDto.getContent()
        );
    }

    public void delete(int no, int memberNo) {
        String sql = """
                UPDATE board_comment
                SET is_deleted = 'Y',
                    update_date = SYSDATE
                WHERE no = ?
                AND member_no = ?
                AND is_deleted = 'N'
                """;

        jdbcTemplate.update(sql, no, memberNo);
    }
}