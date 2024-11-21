package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {//session이 있는지 확인
            Object authInfo = session.getAttribute("authInfo");
            if (authInfo != null) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/login");//로그인 정보가 없다면 로그인 창으로 보내겠다
        return false;// 여기서 컨텍스트 경로는 /begin-spring2가 된다
    }
}
