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
@Table(name="Reply")
@Entity
public class ReplyEntity {
    @Id
    @Column(name = "REPLY_NUM", nullable = false)
    private int replyNum;

    @Column(name = "REPLY_WRITER", nullable = false)
    private String replyWriter;

    @Column(name = "REPLY_TITLE", nullable = false)
    private String replyTitle;

    @Column(name = "REPLY_CONTENT", nullable = false)
    private String replyContent;

    @Column(name = "BOARD_REF")
    private int boardRef;

    @Column(name = "REPLY_REPLY_REF")
    private int replyReplyRef;

    @Column(name = "REPLY_LEV")
    private int replyLev;

    @Column(name = "REPLY_SEQ")
    private int replySeq;

    @Column(name = "REPLY_READCOUNT")
    private int replyReadCount;

    @Column(name = "REPLY_DATE")
    private java.util.Date replyDate;

    @PrePersist
    public void prePersist() {
        replyDate = new GregorianCalendar().getGregorianChange();
    }

    public ReplyDto toDto() {
        return ReplyDto.builder()
                .replyNum(this.replyNum)
                .replyWriter(this.replyWriter)
                .replyTitle(this.replyTitle)
                .replyContent(this.replyContent)
                .boardRef(this.boardRef)
                .replyReplyRef(this.replyReplyRef)
                .replyLev(this.replyLev)
                .replySeq(this.replySeq)
                .replyReadCount(this.replyReadCount)
                .replyDate(this.replyDate)
                .build();
    }

}
