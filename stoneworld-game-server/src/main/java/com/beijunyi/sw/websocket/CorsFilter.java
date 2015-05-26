package com.beijunyi.sw.websocket;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import com.beijunyi.sw.GameServerConstants;

@WebFilter(urlPatterns = GameServerConstants.GAME_SOCKET_URL_PATH)
public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setHeader("Access-Control-Allow-Origin", "*");
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }

}
