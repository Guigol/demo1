package com.internaltools.demo1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolResponseWrapper {
    private List<ToolDto> data;
    private long total;
    private long filtered;
    private Map<String, Object> filtersApplied;
}
