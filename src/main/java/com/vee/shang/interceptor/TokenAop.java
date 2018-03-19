package com.vee.shang.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vee.shang.vo.RedisKeyConstants;
import com.vee.shang.vo.RespCodeConstants;
import com.vee.shang.vo.RespResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//描述切面类
@Aspect
@Configuration
public class TokenAop {

    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /*
     * 定义一个切入点
     */
//    @Pointcut("@annotation(com.vee.shang.interceptor.TokenAnnotation)")
    @Pointcut("execution(* com.vee.shang.controller.*ControllerV.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doBefore(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        try {
            String tokenStr = request.getHeader("token");
            logger.info("tokenStr:{}",tokenStr);
            JSONObject jsonObject = JSON.parseObject(tokenStr);
            String token = jsonObject.getString("token");
            logger.info("token:{}",token);
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String checkToken = (String) valueOperations.get(RedisKeyConstants.USER_TOKEN + jsonObject.getString("userId"));
            if (token != null && token.equals(checkToken)) {
                // result的值就是被拦截方法的返回值
                RespResult result = (RespResult) pjp.proceed();
                return result;
            } else {
                return new RespResult(RespCodeConstants.APP_API_RESULT_CODE_4002, RespCodeConstants.APP_API_RESULT_MSG_4002);
            }
        } catch (Throwable throwable) {
            return new RespResult(RespCodeConstants.FAIL, "token解析异常");
        }
    }

}