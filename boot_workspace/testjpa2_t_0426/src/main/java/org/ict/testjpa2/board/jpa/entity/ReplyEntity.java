package org.ict.testjpa2.board.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.testjpa2.board.model.dto.ReplyDto;

import java.util.GregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="REPLY")
@Entity    //jpa 가 관리함, repository 와 연결됨
public class ReplyEntity {
    @Id //JPA가 객체를 관리할 때 식별할 기본키 지정
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //primary key 지정에 사용함, dml 실행시 default 를 사용하므로 지정하지 말 것
    @Column(name="REPLY_NUM")
    private int replyNum;  //테이블의 컬럼과 매칭되는 dto의 프로퍼티를 지정함

    @Column(name="REPLY_WRITER")
    private String replyWriter;

    @Column(name="REPLY_TITLE")
    private String replyTitle;

    @Column(name="REPLY_CONTENT")
    private String replyContent;

    @Column(name="BOARD_REF")
    private int boardRef;

    @Column(name="REPLY_REPLY_REF")
    private int replyReplyRef;

    @Column(name="REPLY_LEV")
    private int replyLev;

    @Column(name="REPLY_SEQ")
    private int replySeq;

    @Column(name="REPLY_READCOUNT")
    private int replyReadCount;

    @Column(name="REPLY_DATE")
    private java.util.Date replyDate;

    @PrePersist  //jpa 로 넘어가지 전에 작동
    public void prePersist() {
        replyDate = new GregorianCalendar().getGregorianChange();    //boardDate 에 현재 시간 적용
    }

    //entity -> dto 로 변환하는 메서드 추가함
    public ReplyDto toDto(){
        return ReplyDto.builder()
                .replyNum(replyNum)
                .replyTitle(replyTitle)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .boardRef(boardRef)
                .replyLev(replyLev)
                .replyReplyRef(replyReplyRef)
                .replySeq(replySeq)
                .replyReadCount(replyReadCount)
                .replyDate(replyDate)
                .build();
    }
}
