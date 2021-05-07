package com.jeebase.system.controlSys.log.entitly;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author DELL
 */
@Getter
@Setter
public class ErrLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("log_id")
    /**
     * log_id
     */
    private String logId;

    /**
     * 异常类型
     */
    private String epType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 主计划id
     */
    private String planId;

    /**
     * 主计划编码
     */
    private String planCode;

    /**
     * 异常来源
     */
    private String epSource;

    /**
     * 异常详情
     */
    private String epDetails;

    /**
     * 处理标识
     */
    private String epStatus;
}
