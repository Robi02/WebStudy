package com.spring.yesorno.util.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtMemberService {

	private static final long tokenExpTimeDelta = 1000 * 60 * 15; // 15분
	private final String keyHS256;
	
	public JwtMemberService(String keyHS256) {
		this.keyHS256 = keyHS256;
	}

	// 토큰 생성
	public String createMemberToken(MemberDto memberDto, Errors errors) {
		final Date isaDate = new Date();
		final Date expDate = new Date(isaDate.getTime() + tokenExpTimeDelta);
		String jwt = null;

		try {
			jwt = Jwts.builder().setHeaderParam("typ", "JWT")
								.setHeaderParam("alg", "HS256")
							    .setIssuedAt(isaDate)
							    .setExpiration(expDate)
							    .setSubject("memberToken")
							    .claim("memberId", memberDto.getMemberId())
							    .claim("memberEmail", memberDto.getMemberEmail())
							    .signWith(SignatureAlgorithm.HS256, keyHS256.getBytes("UTF-8"))
							    .compact();
		} catch (UnsupportedEncodingException e) {
			errors.reject("error.loginTokenError");
		}
		
		return jwt;
	}
	
	// 토큰 검증
	@SuppressWarnings("unchecked")
	public Map<String, Object> authMemberToken(String jwt, MemberDto memberDto, Errors errors) {
		Map<String, Object> resultMap = null;
		
		Jws<Claims> claims = null;
		
		try {
			claims = Jwts.parser()
						 .setSigningKey(keyHS256.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
			resultMap = (Map<String, Object>)claims.getBody().get("memberId");
		} catch (Exception e) {
			/*	[Throws]
				1) ExpiredJwtException : 유효기간 초과
				2) UnsupportedJwtException : 일치하지 않는 특정 형식이나 구성
				3) MalformedJwtException : 올바르게 구성되지 않음
				4) SignatureException : 서명 확인 불가
				5) IllegalArgumentException : null이거나 빈칸
			*/
			errors.reject("error.loginTokenError");
			resultMap = null;
		}

		return resultMap;
	}
}
