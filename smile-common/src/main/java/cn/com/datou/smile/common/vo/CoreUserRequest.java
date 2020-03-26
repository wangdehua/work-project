package cn.com.datou.smile.common.vo;

import lombok.Data;

/**
 * @ClassName CoreUserRequest
 * @Description 请求user - core的request 实体
 * @Author wangdh
 * @Date 2020/3/26 15:31
 */
@Data
public class CoreUserRequest {

    private Integer id;

    private String nickName;

    private String account;

    private String password;

    private String calling;

    private String cardId;

    private Integer isReal;

    private Integer isVip;

    private String openId;

    private String unionId;

    private Integer status;

}
