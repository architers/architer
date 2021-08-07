package com.business.base.codemap.constants;

/**
 * 代码集校验常量
 *
 * @author luyi
 */
public class CodeMapItemValidConstant {
    /**
     * 主键ID
     */
    public final static String ID_NOT_NULL = "ID不能为空";
    /**
     * code
     */
    public final static String CODE_NOT_BLANK = "代码集编码不能为空";

    /**
     * itemCode
     */
    public final static String CODE_ITEM_NOT_BLANK = "代码集项编码不能为空";
    public final static String CODE_ITEM_LENGTH_LIMIT = "代码集项编码长度不能超过30";
    /**
     * caption
     */
    public final static String ITEM_CAPTION_NOT_BLANK = "中文描述不能为空";
    public final static String ITEM_CAPTION_LENGTH_LIMIT = "中文描述长度不能超过50";
    /**
     * remark
     */
    public final static String REMARK_LENGTH_LIMIT = "备注长度不能超过100";

}
