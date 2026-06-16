package com.siyan1234.group_project.inquiry.service;

import com.siyan1234.group_project.inquiry.dao.InquiryDao;
import com.siyan1234.group_project.inquiry.dto.InquiryAnswerDto;
import com.siyan1234.group_project.inquiry.dto.InquiryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryDao inquiryDao;

    /* =========================
       문의 등록
    ========================= */
    public int insertInquiry(InquiryDto inquiryDto){
        return inquiryDao.insertInquiry(inquiryDto);
    }

    /* =========================
       사용자 문의 목록
    ========================= */
    public List<InquiryDto> findMyInquiryList(Integer memberNo){
        return inquiryDao.findMyInquiryList(memberNo);
    }

    /* =========================
       문의 상세
    ========================= */
    public InquiryDto findByNo(Integer no){
        return inquiryDao.findByNo(no);
    }

    /* =========================
       관리자 전체 문의 목록
    ========================= */
    public List<InquiryDto> findAllInquiry(){
        return inquiryDao.findAllInquiry();
    }

    /* =========================
       답변 등록 + 상태 변경 (핵심)
    ========================= */
    @Transactional
    public int insertAnswer(InquiryAnswerDto answerDto){

        InquiryAnswerDto answer =
                inquiryDao.findAnswerByInquiryNo(
                        answerDto.getInquiryNo()
                );

        // 이미 답변 존재
        if(answer != null){
            return 0;
        }

        int updateResult =
                inquiryDao.updateInquiryStatus(
                        answerDto.getInquiryNo()
                );

        // 문의 상태 변경 실패
        if(updateResult <= 0){
            throw new RuntimeException(
                    "문의 상태 변경 실패"
            );
        }

        int insertResult =
                inquiryDao.insertAnswer(
                        answerDto
                );

        // 답변 등록 실패
        if(insertResult <= 0){
            throw new RuntimeException(
                    "답변 등록 실패"
            );
        }

        return insertResult;
    }

    /* =========================
       답변 조회
    ========================= */
    public InquiryAnswerDto findAnswerByInquiryNo(Integer inquiryNo){
        return inquiryDao.findAnswerByInquiryNo(inquiryNo);
    }
    public InquiryDto findMyInquiry(
            Integer no,
            Integer memberNo){

        return inquiryDao.findMyInquiry(
                no,
                memberNo
        );
    }
}