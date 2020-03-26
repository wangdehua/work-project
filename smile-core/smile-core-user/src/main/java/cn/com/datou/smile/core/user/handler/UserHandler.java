package cn.com.datou.smile.core.user.handler;

import cn.com.datou.smile.common.entity.R;
import cn.com.datou.smile.common.exception.IErrorCode;
import cn.com.datou.smile.common.exception.SmileException;
import cn.com.datou.smile.common.vo.CoreUserRequest;
import cn.com.datou.smile.common.vo.CoreUserResponse;
import cn.com.datou.smile.core.user.domain.User;
import cn.com.datou.smile.core.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserHandler
 * @Description 用户的hadler 处理中心
 * @Author wangdh
 * @Date 2020/3/26 14:47
 */
@Service
@Slf4j
public class UserHandler {

    @Autowired
    private UserService userService ;

    public CoreUserResponse getUserInfo(CoreUserRequest request){
        log.info(" core user getUserInfo request param : {} " , request );
        CoreUserResponse response = new CoreUserResponse();
        try{
            User user = new User();
            user.setCardId(request.getCardId());
            user = this.userService.getUserInfo(user);
            log.info(" core user getUserInfo select user result : {} " , user );
            if(user == null){
                //记录不存在直接抛出异常,在api模块统一处理
                throw new SmileException(IErrorCode.RECORD_NOT_EXIST);
            }
            //转换对象
            BeanUtils.copyProperties(user,response);
            response.setRespCode(IErrorCode.SUCCESS.substring(0,4));
            response.setRespMsg(IErrorCode.SUCCESS.substring(5));
        }catch (Exception e){
            log.error("getUserInfo error", e);
            String errorCode;
            if (e instanceof SmileException) {
                errorCode = ((SmileException) e).getErrorCode();
            } else {
                errorCode = IErrorCode.SYS_EXCEPTION;
            }
            response.setRespCode(errorCode.substring(0, 4));
            response.setRespMsg(errorCode.substring(5));
        }
        log.info(" core user getUserInfo response result : {} " , response );
        return response ;
    }

}
