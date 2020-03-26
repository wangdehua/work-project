package cn.com.datou.smile.core.user.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User  {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
	private int id;
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
     * 密码
     */
	private String password;
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
     * 二维码链接
     */
	private String qrUrl;
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
    /**
     * 修改时间
     */
	private Date updateTime;
    /**
     * 备用字段1
     */
	private Integer res1;
    /**
     * 备用字段2
     */
	private String res2;
    /**
     * 备用字段3
     */
	private String res3;
}
