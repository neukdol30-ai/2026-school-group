package com.siyan1234.group_project.member.interceptor;

import com.siyan1234.group_project.member.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception{
        HttpSession session =
                request.getSession(false);
        String contextPath =
                request.getContextPath();

        if (session == null) {
            response.sendRedirect(contextPath+"/member/login");
            return false;
        }

        MemberDto loginUser =
                (MemberDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(contextPath+"/member/login");
            return false;
        }
        if (!"ADMIN".equals(loginUser.getRole())){
            response.sendRedirect(contextPath+"/");
            return false;
        }
        return true;
    }
}
