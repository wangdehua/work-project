package cn.com.datou.smile.api.handler;

import cn.com.datou.smile.api.feign.UserFeignService;
import cn.com.datou.smile.common.entity.R;
import cn.com.datou.smile.common.exception.IErrorCode;
import cn.com.datou.smile.common.exception.SmileException;
import cn.com.datou.smile.common.vo.ApiRequest;
import cn.com.datou.smile.common.vo.ApiResponse;
import cn.com.datou.smile.common.vo.CoreUserRequest;
import cn.com.datou.smile.common.vo.CoreUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserHandler
 * @Description api 处理user的handler
 * @Author wangdh
 * @Date 2020/3/26 17:29
 */
@Service
@Slf4j
public class UserHandler {

    @Autowired
    private UserFeignService userFeignService ;

    public R getUserInfo(ApiRequest request){
        //TODO 参数可能要加密!!!
        log.info(" userHandler getUserInfo request param : {} " , request );
        CoreUserRequest coreReq = new CoreUserRequest() ;
        ApiResponse response = new ApiResponse();
        try{
            if(StringUtils.isBlank(request.getCardId())){
                throw new SmileException(IErrorCode.MISSING_REQUIRED_FIELD);
            }
            BeanUtils.copyProperties(request,coreReq);
            //请求核心区
            log.info(" userHandler getUserInfo request core param : {} " , coreReq );
            CoreUserResponse coreRsp = this.userFeignService.getUserInfo(coreReq);
            log.info(" userHandler getUserInfo core response : {} " , coreRsp );
            if (!IErrorCode.SUCCESS.substring(0,4).equals(coreRsp.getRespCode())){
                throw new SmileException(coreRsp.getRespCode() + "|" + coreRsp.getRespMsg());
            }
            BeanUtils.copyProperties(coreRsp,response);
            response.setRetCode(IErrorCode.SUCCESS.substring(0,4));
            response.setMemo(IErrorCode.SUCCESS.substring(5));
        }catch (Exception e){
            log.error("getUserInfo error", e);
            String errorCode;
            if (e instanceof SmileException) {
                errorCode = ((SmileException) e).getErrorCode();
            } else {
                errorCode = IErrorCode.SYS_EXCEPTION;
            }
            response.setRetCode(errorCode.substring(0, 4));
            response.setMemo(errorCode.substring(5));
        }
        log.info(" userHandler getUserInfo result response : {} " , response );
        return R.data(response);
    }

}
