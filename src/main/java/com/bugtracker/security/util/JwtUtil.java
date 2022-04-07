package com.bugtracker.security.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bugtracker.security.UserDet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	
	 private String secret = "BugtrackerByLakhdar";

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	    	//String secretKey = TextCodec.BASE64URL.encode(secret);
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    public String generateToken(Authentication auth) {
	        Map<String, Object> claims = new HashMap<>();
	        ArrayList<String> roles = new ArrayList<String>();
	        auth.getAuthorities().forEach(role->roles.add(role.getAuthority()));
	        claims.put("roles", roles);
	        return createToken(claims, auth);
	    }

	    private String createToken(Map<String, Object> claims, Authentication auth) {

	        return Jwts.builder().setClaims(claims).setSubject(auth.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(SignatureAlgorithm.HS256, secret).compact();
	    }

	    public Boolean validateToken(String token, UserDet userDetails) {
	    
		        final String username = extractUsername(token);
		        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    	
	    }

}
