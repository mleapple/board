package com.jm.board.filter;

import com.jm.board.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginFilter  implements Filter {

   // private static final String[] whitelist={"/","/member/join","/member/login","/member/logout","/css/*","/favicon.ico"};
    private static final String[] whitelist={"/","/member/join","/member/login","/member/logout","/css/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest   = (HttpServletRequest) request;
        String requestURI                       = httpServletRequest.getRequestURI(); // 진입 URL 가져오기
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try{
            log.info("로그인 인층체크하기 ");
            if(isLoginCheckPath(requestURI)){
                log.info("인증체크 로직 실행{}",requestURI);
                //세션가져오기
                HttpSession session = httpServletRequest.getSession(false); // false 설정 하여 기존 세션 여부를 판단한다
                if(session == null|| session.getAttribute(SessionConst.LOGIN_MEMBER)== null){ // 세션이 존재 하지 않는다면
                    log.info("===>>>>> 미인증 사용자 요청{}", requestURI);
                    httpServletResponse.sendRedirect("/member/login?requestURL="+requestURI); // 로그인 페이지로 진입URL과 같이 보내 준다
                    return;
                }
            }
            // 세션이 존재한다면
            chain.doFilter(httpServletRequest , httpServletResponse);

        }catch(Exception e){
            throw e ;// 톰캣까지 에러 보내주어야함
        }finally{
            log.info("인증체크필터 종료",requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크를 하지 않는다
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
