package com.bilibili.api.support;

import com.bilibili.domain.exception.ConditionException;
import com.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {
    public Long getCurrentUserID() {
        //
        ServletRequestAttributes requestAttributesAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributesAttributes.getRequest().getHeader("header");
        Long userID = TokenUtil.verifyToken(token);
        if (userID < 0) {
            throw new ConditionException("The user ID is illegal.");
        }
        return userID;


    }
}
