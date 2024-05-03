package org.ict.testjpa2.notice.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.ict.testjpa2.common.SearchDate;
import org.ict.testjpa2.notice.jpa.entity.NoticeEntity;
import org.ict.testjpa2.notice.jpa.entity.QNoticeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepository {
    //방법 3 : 상속, 재구현(implement) 없는 querydsl 만으로 구성하는 리포지터리 클래스 작성 방법

    private  final JPAQueryFactory queryFactory;   //이것만 선언하면 됨
    private final EntityManager entityManager;   //JPQL 사용을 위해 의존성 주입
    private QNoticeEntity notice = QNoticeEntity.noticeEntity;
    
    //조회수 1 증가 처리 => jpa 제공 메서드로 save() 사용해도 됨
    public void updateReadCount(int noticeNo){
        queryFactory
                .update(notice)     //update notice
                .set(notice.readCount, notice.readCount.add(1))     //set readcount = readcount + 1
                .where(notice.noticeNo.eq(noticeNo))   //where noticeno = 전달된글번호
                .execute();
        entityManager.flush();
        entityManager.clear();
    }
    
    //가장 최근 공지글 1개 조회
    public NoticeEntity findLast(){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)  // select * from notice
                .orderBy(notice.noticeNo.desc())  //order by noticeno desc
                .fetch().get(0);   //내림차순정렬 공지글의 첫번째 == 가장 최근 등록글 1개
    }

    //최근 등록글 3개 조회 : querydsl 에서 jpql 사용하는 경우
    public List <NoticeEntity> findNewTop3(){
        //주의 : querydsl 에서는 select 절과 where 절에서의 서브쿼리는 지원하지만,
        //           from 절에서의 서브쿼리는 지원하지 않는다. => 쿼리를 나눠서 실행 또는 조인 등으로 해결할 필요가 있음
        //JPQL 도 where 절과 group by 절에서만 서브쿼리 사용 가능함 => from 절에서 서브쿼리 사용 못함
        return entityManager
                .createQuery("SELECT NOTICENO, NOTICETITLE, NOTICEDATE FROM NOTICE ORDER BY NOTICEDATE DESC")
                .getResultList();
    }

    //검색 페이징 처리를 위한 검색 카운트 관련 메소드 ------------------------------
    public long countByTitle(String keyword){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeTitle.like("%" + keyword + "%"))  //where noticetitle like %검색단어%
                .fetchCount();
    }

    public long countByContent(String keyword){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeContent.like("%" + keyword + "%"))  //where noticecontent like %검색단어%
                .fetchCount();
    }

    public long countBySearchDate(SearchDate searchDate) {
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeDate.between(searchDate.getBegin(), searchDate.getEnd())) //where noticedate between begin and end
                .fetchCount();
    }

    //검색 관련 메소드
    public List<NoticeEntity> findBySearchTitle(String keyword, Pageable pageable){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeTitle.like("%" + keyword + "%"))  //where noticetitle like %검색단어%
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<NoticeEntity> findBySearchContent(String keyword, Pageable pageable){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeContent.like("%" + keyword + "%"))  //where noticecontent like %검색단어%
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<NoticeEntity> findBySearchDate(SearchDate searchDate, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)   //select * from notice
                .where(notice.noticeDate.between(searchDate.getBegin(), searchDate.getEnd())) //where noticedate between begin and end
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}









