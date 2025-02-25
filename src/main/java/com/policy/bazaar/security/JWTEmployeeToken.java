package com.policy.bazaar.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.policy.bazaar.employee.model.Employees;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTEmployeeToken {
	
	public static String SECRET_KEY = "Thisismysecretkey11122!@#$%%^";
	public static Logger logger = LoggerFactory.getLogger(JwtCustomerToken.class);

	public static String createJWT(long ttlMillis, Employees employees) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		//Date now = new Date(nowMillis);

		// sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder()
				.claim("id", employees.getEmpid())
				.signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {

			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();

	}

}
