package com.infy.qrcode.filter;

import com.fasterxml.uuid.Generators;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@Order(1)
public class RequestResponseFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ThreadContext.clearAll();
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		final String transacionId = Generators.timeBasedGenerator().generate().toString();
		log.info(" do Filter Method : {} Transaction Id : {}", RequestResponseFilter.class.getName(), transacionId);
		ThreadContext.put("Transaction_Id", transacionId);
		httpServletResponse.setHeader("Transaction_Id", transacionId);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		httpServletRequest.setAttribute("Transaction_Id",transacionId);

		chain.doFilter(request, response);
	}

}
