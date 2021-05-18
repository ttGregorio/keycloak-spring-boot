package br.com.nex2you.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.nex2you.api.dto.UserDTO;
import br.com.nex2you.api.response.Response;
import br.com.nex2you.api.service.AuthService;
import br.com.nex2you.api.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		try {
			logger.info("[UserController][createUser][userDTO: ".concat(userDTO.toString()).concat("]"));
			return ResponseEntity.ok(new Response(userService.create(userDTO)));
		}

		catch (Exception ex) {
			logger.error("[UserController][createUser][".concat(ex.getMessage()).concat("]"));
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> auth(@RequestBody UserDTO userDTO) {
		try {
			logger.info("[UserController][createUser][userDTO: ".concat(userDTO.toString()).concat("]"));
			return ResponseEntity.ok(authService.getToken(userDTO));
		}

		catch (Exception ex) {
			logger.error("[UserController][createUser][".concat(ex.getMessage()).concat("]"));
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{token}/refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> refreshToken(@PathVariable String token) {
		try {
			logger.info("[UserController][createUser][userDTO: ".concat(token).concat("]"));
			return ResponseEntity.ok(authService.getByRefreshToken(token));
		}

		catch (Exception ex) {
			logger.error("[UserController][createUser][".concat(ex.getMessage()).concat("]"));
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
