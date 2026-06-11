package com.siyan1234.group_project.board.dao;

import com.siyan1234.group_project.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> listBoard();
}
