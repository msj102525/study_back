package org.ict.testjpa2.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SearchDate {
	private Date begin;
	private Date end;
}
