package com.boot.test.dto;

import lombok.Data;

@Data
public class Board {
	private int boardNum;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
}
