package org.ict.testjpa2.notice.jpa.repository;

import org.ict.testjpa2.notice.jpa.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함
}
