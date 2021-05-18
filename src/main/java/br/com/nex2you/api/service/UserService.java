package br.com.nex2you.api.service;

import br.com.nex2you.api.dto.UserDTO;

public interface UserService {

	UserDTO create(UserDTO userDTO);

	UserDTO findByUsername(String username);

	UserDTO update(UserDTO userDTO);
}