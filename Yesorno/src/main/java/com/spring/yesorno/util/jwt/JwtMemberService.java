package com.spring.yesorno.util.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.validation.Errors;

import com.spring.yesorno.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtMemberService {

	public static final long tokenExpTimeDelta = 1000 * 60 * 5; // 5분
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
	public String authMemberToken(String jwt, Errors errors) {
		String authMemberEmail = null;
		Jws<Claims> claims = null;

		try {
			// 토큰 검사 - 파싱이 가능하면 아래 5가지 예외에서 벗어난 상태
			claims = Jwts.parser().setSigningKey(keyHS256.getBytes("UTF-8")).parseClaimsJws(jwt);
			authMemberEmail = claims.getBody().get("memberEmail", String.class);
		} catch (Exception e) {
			/* [Throws]
			1) ExpiredJwtException : 유효기간 초과
			2) UnsupportedJwtException : 일치하지 않는 특정 형식이나 구성
			3) MalformedJwtException : 올바르게 구성되지 않음
			4) SignatureException : 서명 확인 불가
			5) IllegalArgumentException : null이거나 빈칸 */
			e.printStackTrace();
			errors.reject("error.invailedToken");
			authMemberEmail = null;
		}

		// 검증 완료 시 토큰 이메일 반환
		return authMemberEmail;
	}
}

/*
 * @SuppressWarnings("unchecked")
try {
claims = Jwts.parser()
.setSigningKey(keyHS256.getBytes("UTF-8"))
.parseClaimsJws(jwt);
resultMap = (Map<String, Object>)claims.getBody().get("memberId");
		} catch (Exception e) {
			
			
			errors.reject("error.loginTokenError");
			resultMap = null;
		}
		*/