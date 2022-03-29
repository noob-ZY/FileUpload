package com.noobzy.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        return request;
    }

    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = null;
        if (requestAttributes != null) {
            response = requestAttributes.getResponse();
        }
        return response;
    }

    public static HttpSession getCurrentSession() {
        HttpServletRequest currentRequest = getCurrentRequest();
        HttpSession session = null;
        if (currentRequest != null) {
            session = currentRequest.getSession();
        }
        return session;
    }

}
