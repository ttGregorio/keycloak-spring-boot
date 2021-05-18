package br.com.nex2you.api.service;

import br.com.nex2you.api.dto.UserDTO;

public interface AuthService {

	String getByRefreshToken(String refreshToken);

	String getToken(UserDTO userCredentials);

}