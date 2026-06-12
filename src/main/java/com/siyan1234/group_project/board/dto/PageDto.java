package com.siyan1234.group_project.board.dto;

import lombok.Data;

@Data
public class PageDto {
    // 1. 브라우저 요청

    private int page = 1;
    private int amount = 10; // 페이지당 글 수
    private String keyword; // 검색어

    // 2. 계산 값
    private int total; // 전체 글 수(검색이라면 검색 결과 수)
    private int offset; // SQL에서 건너뛸 글 수
    private int totalPage; // 전체 페이지 수(하단)

    // 3. 계산 메서드
    public void calculate(int total) {
        this.total = total;
        this.offset = (page - 1) * amount;
        this.totalPage = (int) Math.ceil((double) total / amount);

        if (totalPage == 0) {
            totalPage = 1;
        }

    }

}
