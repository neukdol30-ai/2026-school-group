package com.siyan1234.group_project.photoBoard.service;

import com.siyan1234.group_project.photoBoard.dao.PhotoBoardDao;
import com.siyan1234.group_project.photoBoard.dto.PhotoBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoBoardService {

    private final PhotoBoardDao photoBoardDao;

    public List<PhotoBoardDto> getList() {
        return photoBoardDao.findAll();
    }

    public void write(PhotoBoardDto photoBoardDto) {
        photoBoardDao.write(photoBoardDto);
    }

    public PhotoBoardDto getBoard(int no) {
        return photoBoardDao.findByNo(no);
    }

    public void delete(int no) {
        photoBoardDao.delete(no);
    }

    public void like(int no) {
        photoBoardDao.like(no);
    }
}