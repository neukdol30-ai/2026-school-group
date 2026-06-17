package com.siyan1234.group_project.notice.dao;

import com.siyan1234.group_project.notice.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeDao {

    private final JdbcTemplate jdbcTemplate;

    public List<NoticeDto> findAll() {
        String sql = """
                SELECT *
                FROM board
                WHERE board_type = 'NOTICE'
                  AND is_deleted = 'N'
                ORDER BY no DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                NoticeDto.builder()
                        .no(rs.getInt("no"))
                        .memberNo(rs.getInt("member_no"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .hit(rs.getInt("hit"))
                        .regdate(rs.getString("regdate"))
                        .updateDate(rs.getString("update_date"))
                        .build()
        );
    }

    public NoticeDto findByNo(int no) {
        String sql = """
                SELECT *
                FROM board
                WHERE no = ?
                  AND board_type = 'NOTICE'
                  AND is_deleted = 'N'
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        NoticeDto.builder()
                                .no(rs.getInt("no"))
                                .memberNo(rs.getInt("member_no"))
                                .title(rs.getString("title"))
                                .content(rs.getString("content"))
                                .hit(rs.getInt("hit"))
                                .regdate(rs.getString("regdate"))
                                .updateDate(rs.getString("update_date"))
                                .build()
                , no);
    }

    public void write(NoticeDto noticeDto) {
        String sql = """
                INSERT INTO board
                (
                    no,
                    member_no,
                    board_type,
                    title,
                    content,
                    hit,
                    like_count,
                    regdate,
                    is_deleted
                )
                VALUES
                (
                    board_seq.nextval,
                    ?,
                    'NOTICE',
                    ?,
                    ?,
                    0,
                    0,
                    SYSDATE,
                    'N'
                )
                """;

        jdbcTemplate.update(sql,
                noticeDto.getMemberNo(),
                noticeDto.getTitle(),
                noticeDto.getContent()
        );
    }

    public void update(NoticeDto noticeDto) {
        String sql = """
                UPDATE board
                SET title = ?,
                    content = ?,
                    update_date = SYSDATE
                WHERE no = ?
                  AND board_type = 'NOTICE'
                """;

        jdbcTemplate.update(sql,
                noticeDto.getTitle(),
                noticeDto.getContent(),
                noticeDto.getNo()
        );
    }

    public void delete(int no) {
        String sql = """
                UPDATE board
                SET is_deleted = 'Y'
                WHERE no = ?
                  AND board_type = 'NOTICE'
                """;

        jdbcTemplate.update(sql, no);
    }

    public void increaseHit(int no) {
        String sql = """
                UPDATE board
                SET hit = hit + 1
                WHERE no = ?
                  AND board_type = 'NOTICE'
                """;

        jdbcTemplate.update(sql, no);
    }
}