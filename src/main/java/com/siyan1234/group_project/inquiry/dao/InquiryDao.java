package com.siyan1234.group_project.inquiry.dao;

import com.siyan1234.group_project.inquiry.dto.InquiryAnswerDto;
import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiryDao {
    int insertInquiry(InquiryDto inquiryDto);

    List<InquiryDto> findMyInquiryList(Integer memberNo);

    InquiryDto findByNo(Integer no);

    InquiryDto findMyInquiry(
            @Param("no") Integer no,
            @Param("memberNo") Integer memberNo
    );

    List<InquiryDto> findAllInquiry();

    // 관리자 문의 검색
    List<InquiryDto> searchInquiryList(InquiryDto inquiryDto);

    int insertAnswer(InquiryAnswerDto answerDto);

    InquiryAnswerDto findAnswerByInquiryNo(Integer inquiryNo);

    int updateInquiryStatus(Integer inquiryNo);
}
