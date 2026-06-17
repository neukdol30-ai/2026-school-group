package com.siyan1234.group_project.notice.dao;

import com.siyan1234.group_project.notice.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {

    List<NoticeDto> findAll();

    NoticeDto findByNo(int no);

    void increaseHit(int no);

    void write(NoticeDto noticeDto);

    void update(NoticeDto noticeDto);

    void delete(int no);
}