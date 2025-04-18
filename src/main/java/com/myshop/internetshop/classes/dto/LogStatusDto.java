package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.enums.LogStatusEnum;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogStatusDto {
    private String id;
    private LogStatusEnum status;
    @JsonIgnore
    private String logPath;
    private String additional;
    private String errorMsg;
}
