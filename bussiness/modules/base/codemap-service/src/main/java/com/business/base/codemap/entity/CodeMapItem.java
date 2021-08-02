package com.business.base.codemap.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 代码集项
 *
 * @author luyi
 */
@Data
@TableName("t_base_code_map_item")
public class CodeMapItem {
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * @see CodeMap#code
     */
    private String convertCode;
    /**
     * 代码级项编码
     */
    private String itemCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 代码级项中文名称
     */
    private String itemCaption;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 添加人
     */
    private String createUser;
    /**
     * 添加时间
     */
    private Date createTime;
}
