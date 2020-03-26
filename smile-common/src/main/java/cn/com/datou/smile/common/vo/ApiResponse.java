package cn.com.datou.smile.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName ApiResponse
 * @Description api 响应的实体
 * @Author wangdh
 * @Date 2020/3/26 17:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    /**
     * 响应码
     */
    private String retCode ;
    /**
     * 响应描述
     */
    private String memo ;
    /**
     * 自增id
     */
    private Integer uesrId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 账户
     */
    private String account;
    /**
     * 性别1:男 0:女
     */
    private Integer sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机号
     */
    private String calling;
    /**
     * 校园卡编号/身份证编号
     */
    private String cardId;
    /**
     * 是否认证 1:已认证 0:未认证
     */
    private Integer isReal;
    /**
     * 是否为vip 1:是 0:否
     */
    private Integer isVip;
    /**
     * 微信的openId
     */
    private String openId;
    /**
     * 微信开放平台unionId
     */
    private String unionId;
    /**
     * 账户是否正常 1:是 0:否
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;


}
