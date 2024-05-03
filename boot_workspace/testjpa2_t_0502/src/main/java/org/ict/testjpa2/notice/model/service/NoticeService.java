package org.ict.testjpa2.notice.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa2.common.SearchDate;
import org.ict.testjpa2.notice.jpa.entity.NoticeEntity;
import org.ict.testjpa2.notice.jpa.repository.NoticeQueryRepository;
import org.ict.testjpa2.notice.jpa.repository.NoticeRepository;
import org.ict.testjpa2.notice.model.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    //JPA 가 제공하는 기본 메서드 사용을 위해서는
    private final NoticeRepository noticeRepository;
    //JPA  제공 메서드로 해결하지 못하는 SQL 문 처리를 위한 별도의 리포지터리 의존성 주입함 (상속, 재구현 없는 형식)
    private final NoticeQueryRepository noticeQueryRepository;

    //메서드 작성부 -----------------------------------------

    public ArrayList<NoticeDto> selectTop3(){
        //jpa 제공
        //등록날짜 기준으로 내림차순정렬한 리스트를 받아서, 0~2ㅓㄴ 인덱스만 추출함
        List<NoticeEntity> entityList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeNo"));
        //List<NoticeEntity> entityList = noticeQueryRepository.findNewTop3();   //추가한 메서드
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            list.add(entityList.get(i).toDto());
        }
        return list;
    }

    //페이징 처리를 위한 총 목록 갯수 조회용
    public long selectListCount(){
        //jpa 제공
        return noticeRepository.count();
    }

    public ArrayList<NoticeDto> selectList(Pageable pageable){
        //jpa 제공
        Page<NoticeEntity> entities = noticeRepository.findAll(pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public NoticeDto selectNotice(int noticeNo){
        //jpa 제공 : findById(Id) : Optional<T>
        //엔티티에 등록된 id 에 해당하는 property 를 사용해서 조회함
        Optional<NoticeEntity> entity = noticeRepository.findById(noticeNo);
        return entity.get().toDto();
    }

    @Transactional
    public void insertNotice(NoticeDto noticeDto) {
        //save(entity) : Entity
        //jpa 제공, insert 문과 update문 처리용 메서드
        noticeRepository.save(noticeDto.toEntity());
    }

    @Transactional
    public void updateNotice(NoticeDto noticeDto) {
        noticeRepository.save(noticeDto.toEntity());
    }

    @Transactional
    public int deleteNotice(int noticeNo) {     //int 리턴 형태를 만든다면 아래와 같이 작성함
        try {
            //jpa 제공 메서드
            noticeRepository.deleteById(noticeNo);
            return 1;
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    //검색 카운트 추가 메소드 사용 -----------------------------------
    public long getSearchTitleCount(String keyword){
        return noticeQueryRepository.countByTitle(keyword);
    }

    public long getSearchContentCount(String keyword){
        return noticeQueryRepository.countByContent(keyword);
    }

    public long getSearchDateCount(SearchDate searchDate){
        return noticeQueryRepository.countBySearchDate(searchDate);
    }

    //검색 관련 추가 메소드 사용 ---------------------------------------
    public ArrayList<NoticeDto> selectSearchTitle(String keyword, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchTitle(keyword, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public ArrayList<NoticeDto> selectSearchContent(String keyword, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchContent(keyword, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public ArrayList<NoticeDto> selectSearchDate(SearchDate searchDate, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchDate(searchDate, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }
}
