package com.siyan1234.group_project.notice.service;

import com.siyan1234.group_project.notice.dao.NoticeDao;
import com.siyan1234.group_project.notice.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;

    public List<NoticeDto> getList() {
        return noticeDao.findAll();
    }

    // 조회수 증가 O
    public NoticeDto getNotice(int no) {
        noticeDao.increaseHit(no);
        return noticeDao.findByNo(no);
    }

    // 조회수 증가 X
    public NoticeDto findByNo(int no) {
        return noticeDao.findByNo(no);
    }

    public void write(NoticeDto noticeDto) {
        noticeDao.write(noticeDto);
    }

    public void update(NoticeDto noticeDto) {
        noticeDao.update(noticeDto);
    }

    public void delete(int no) {
        noticeDao.delete(no);
    }
}