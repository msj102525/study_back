package org.ict.testjpa2.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCondition {
    private String key;  //search key
    private String value;  //search value
}
