package br.com.nex2you.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.nex2you.api.dto.UserDTO;
import br.com.nex2you.api.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Value("${keycloak.resource}")
	private String CLIENTID;

	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;

	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${password}")
	private String PASSWORD;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UsersResource userRessource;

	@Bean
	private void loadKeycloakBean() {
		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm("master").username("admin").password(PASSWORD)
				.clientId("admin-cli").resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
				.build();

		RealmResource realmResource = kc.realm(REALM);
		userRessource = realmResource.users();
	}

	@Override
	public UserDTO create(UserDTO userDTO) {
		logger.debug("[UserService][create][userDTO: {}]", userDTO);

		Map<String, List<String>> attributes = new HashMap<String, List<String>>();
		attributes.put("address", Arrays.asList(userDTO.getAddress()));
		attributes.put("number", Arrays.asList(userDTO.getNumber()));
		attributes.put("cep", Arrays.asList(userDTO.getCep()));
		attributes.put("complement", Arrays.asList(userDTO.getComplement()));
		attributes.put("neighborhood", Arrays.asList(userDTO.getNeighborhood()));
		attributes.put("city", Arrays.asList(userDTO.getCity()));
		attributes.put("state", Arrays.asList(userDTO.getState()));

		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
		credentialRepresentation.setTemporary(false);
		credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
		credentialRepresentation.setValue(userDTO.getPassword());

		UserRepresentation user = new UserRepresentation();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEnabled(true);
		user.setAttributes(attributes);
		user.setId(userDTO.getId());
		user.setCredentials(new ArrayList<>());
		user.getCredentials().add(credentialRepresentation);

		Response response = userRessource.create(user);

		logger.debug("[UserService][createUserInKeyCloak][response: {}]", response.getStatusInfo());

		return findByUsername(userDTO.getUsername());
	}

	@Override
	public UserDTO findByUsername(String username) {
		try {
			logger.info("[UserService][findByUsername][username: ".concat(username).concat("]"));

			List<UserRepresentation> itens = userRessource.search(username);

			for (UserRepresentation userRepresentation : itens) {
				logger.info("[UserService][findByUsername][user: ".concat(userRepresentation.toString()).concat("]"));

				return UserDTO.builder().address(getAttribute(userRepresentation.getAttributes(), "address"))
						.cep(getAttribute(userRepresentation.getAttributes(), "cep"))
						.city(getAttribute(userRepresentation.getAttributes(), "city"))
						.complement(getAttribute(userRepresentation.getAttributes(), "complement"))
						.email(userRepresentation.getEmail()).firstName(userRepresentation.getFirstName())
						.id(userRepresentation.getId()).lastName(userRepresentation.getLastName())
						.neighborhood(getAttribute(userRepresentation.getAttributes(), "neighborhood"))
						.number(getAttribute(userRepresentation.getAttributes(), "number"))
						.state(getAttribute(userRepresentation.getAttributes(), "state"))
						.username(userRepresentation.getUsername()).build();
			}
			logger.info("[UserService][findByUsername][not found]");

			return null;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		logger.debug("[UserService][update][userDTO: {}]", userDTO);

		Map<String, List<String>> attributes = new HashMap<String, List<String>>();
		attributes.put("address", Arrays.asList(userDTO.getAddress()));
		attributes.put("number", Arrays.asList(userDTO.getNumber()));
		attributes.put("cep", Arrays.asList(userDTO.getCep()));
		attributes.put("complement", Arrays.asList(userDTO.getComplement()));
		attributes.put("neighborhood", Arrays.asList(userDTO.getNeighborhood()));
		attributes.put("city", Arrays.asList(userDTO.getCity()));
		attributes.put("state", Arrays.asList(userDTO.getState()));

		UserRepresentation user = new UserRepresentation();
		user.setAttributes(attributes);

		UserResource userResource = userRessource.get(userDTO.getId());

		try {
			userResource.update(user);
		} catch (BadRequestException e) {
			System.out.println(e);
		}
		return findByUsername(userDTO.getUsername());
	}

	private String getAttribute(Map<String, List<String>> attributes, String propertyName) {
		return (attributes != null && attributes.get(propertyName) != null && attributes.get(propertyName).size() > 0)
				? attributes.get(propertyName).get(0)
				: null;
	}
}