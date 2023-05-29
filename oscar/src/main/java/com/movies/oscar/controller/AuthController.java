package com.movies.oscar.controller;

import com.movies.oscar.dto.LoginDto;
import com.movies.oscar.entity.User;
import com.movies.oscar.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired TokenService tokenService;

	@PostMapping("/login")
	public String login(@RequestBody LoginDto login) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(login.login(), login.senha());
		var authentication = this.authenticationManager.authenticate(authenticationToken);
		var usuario = (User) authentication.getPrincipal();

		return this.tokenService.gerarToken(usuario);
	}

}
