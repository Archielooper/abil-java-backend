package com.policy.bazaar.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class CustomerAuthorizationFilter extends GenericFilterBean {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
			filterchain.doFilter(req, res);
			return;
		}

		try {
			if (isTokenValid(req)) {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				setError(res);
				return;
			}

		} catch (Exception ex) {

			setError(res);
			return;

		}

		filterchain.doFilter(req, res);
	}

	private void setError(HttpServletResponse res) throws IOException {
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.getOutputStream().write("Invalid Token".getBytes());
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader("Authorization");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

		if (token != null) {

			Claims claim = (Claims) Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(JwtCustomerToken.SECRET_KEY))
					.parseClaimsJws(token.replace("Bearer", "")).getBody();

			Map<String, Object> user = getMapFromIoJsonwebtokenClaims(claim);

			req.setAttribute("user", user.get("id"));

			if (claim != null) {
				usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claim, null,
						new ArrayList<>());
			}

		}
		return usernamePasswordAuthenticationToken;
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(Claims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	private boolean isTokenValid(HttpServletRequest req) {
		boolean result = false;
		String header = req.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer")) {
			result = false;

		} else {
			result = true;
		}

		return result;
	}

}
