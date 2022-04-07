package com.bugtracker.security.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.bugtracker.security.UserDet;
import com.bugtracker.security.UserDetailServ;


@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailServ userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	try {
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer")) {
			token = authorizationHeader.substring(7);
			userName = jwtUtil.extractUsername(token);
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	     	UserDet userDet = (UserDet) userDetailsService.loadUserByUsername(userName);
	     
	     	if (jwtUtil.validateToken(token, userDet)) {
	     		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
	     				new UsernamePasswordAuthenticationToken(userDet, null, userDet.getAuthorities());
	     		usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	     	}
		} 
		filterChain.doFilter(request, response);
	}catch(Exception e) {		
		//response.sendError(HttpStatus.UNAUTHORIZED.value());
		/*StringBuilder sb = new StringBuilder();
	    sb.append("{ ");
	    sb.append("\"message\": \"Token might be invalid\"");
	    sb.append("} ");
	    response.setContentType("application/json");*/
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
	   // response.getWriter().write(sb.toString());
		return;
	}
	
	}

}
