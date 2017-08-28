package com.zhongqi.filter;

import com.zhongqi.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component
public class SecurityInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);
	
	@Value("${sdltServer}")
	private String sdltServer;
	/**
	 *
	 * 请求处理之前处理
	 * session校验
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		LOGGER.info("***********************************************************************");
		LOGGER.info("-----------------------<<拦截器拦截 start>>--------------------------------");
		LOGGER.info("***********************************************************************");
		LOGGER.info("执行请求为：{}",request.getRequestURI());


//		Object ssoUser = request.getSession().getAttribute("AuthSpId");

//		LOGGER.info("获取session的值为：{}",ssoUser);

//		if (ssoUser == null) {
//			LOGGER.info("鉴权未通过,跳转首页{}",sdltServer);
//            response.sendRedirect(sdltServer);
//            return false;
//        }
		User user =null;
		if (user==null){
			//			LOGGER.info("鉴权未通过,跳转首页{}",sdltServer);
            response.sendRedirect("/login");
            return false;
		}

		LOGGER.info("恭喜，鉴权通过。走走走");
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
			
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
			
		
	}

}
