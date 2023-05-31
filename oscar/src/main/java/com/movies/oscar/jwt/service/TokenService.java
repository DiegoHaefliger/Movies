package com.movies.oscar.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.movies.oscar.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

	private static final String SECRECT = "u8x/A?D(G+KaPdSgVkYp3s6v9y$B&E)H";
	private static final String ISSUER = "Movie";
	private static final Integer TEMPO_TOKEN = 10;

	public String gerarToken(User user) {
		return JWT.create()
				.withIssuer(ISSUER)
				.withSubject(user.getLogin())
				.withClaim("id", user.getId())
				.withExpiresAt(LocalDateTime.now().plusHours(TEMPO_TOKEN).toInstant(ZoneOffset.of("-03:00")))
				.sign(Algorithm.HMAC256(SECRECT));
	}

	public String getSubject(String token) {
		return JWT.require(Algorithm.HMAC256(SECRECT))
				.withIssuer(ISSUER)
				.build()
				.verify(token)
				.getSubject();
	}

}
