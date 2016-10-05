package com.tiy;

/**
 * Created by bearden-tellez on 10/5/16.
 */



        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.ModelAndView;
        import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.util.Enumeration;

@Configuration
public class TIYRequestInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        logger.debug("preHandle() processing ...");
        logger.debug("Request = " + request);
        logger.debug("Response = " + response);
        logger.debug("Handler = " + handler);

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            logger.debug("Header name =" + headerName);
            logger.debug("Header value = " + request.getHeader(headerName));
        }

        return true;
    }

    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {

        logger.debug("postHandle() processing ...");
        logger.debug("Request = " + request);
        logger.debug("Response = " + response);
        logger.debug("Handler = " + handler);
        logger.debug("ModelAndView = " + modelAndView);

        for (String headerName : response.getHeaderNames()){
            logger.debug("Header name =" + headerName);
            logger.debug("Header value = " + response.getHeader(headerName));
        }

    }
}