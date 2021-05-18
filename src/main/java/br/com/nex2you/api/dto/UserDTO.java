package br.com.nex2you.api.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.web.error.ErrorAttributeOptions.Include;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String firstName;

	private String lastName;

	private String email;

	private String address;

	private String number;

	private String complement;

	private String neighborhood;

	private String city;

	private String state;

	private String cep;

	private String password;

	private String username;

}