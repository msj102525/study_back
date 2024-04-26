package org.ict.first.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // @ToString, @EqualsAndHashCode, @Getter/@Setter, @RequiredArgsConstructor 모두 사용한 것과 같음
@NoArgsConstructor
@AllArgsConstructor
public class Board /* implements java.io.Serializable */ {
	//private static final long serialVersionUID = -5219066150385199445L;

	private int boardNum;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
	private String boardOriginalFileName;
	private String boardRenameFileName;
	private int boardRef;
	private int boardReplyRef;
	private int boardLev;
	private int boardReplySeq;
	private int boardReadCount;
	private java.sql.Date boardDate;	
	
}