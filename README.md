Este projeto tem como objetivo exemplificar a integração com a ferramenta de gestão de login Keycloak, da Red Hat

Para isso você deverá realizar o download do keycloak (https://www.keycloak.org/downloads) ou de sua imagem docker 
(https://hub.docker.com/r/jboss/keycloak/), e configura-la conforme sua necessidade. Para este exemplo criamos um realm 
chamado spring-boot-keycloak, com um client login-app, e especificamos que somente usuários com a role "user" podem acessar a aplicação.

Atenção: Este projeto foi desenvolvido somente como modelo de integração com a senha colocada não criptografada no arquivo de properties 
apenas para facilitar a manutenção. É altamente recomendável que você não coloque dados tão sensíveis em local de tão fácil acesso.





This project aims to exemplify the integration with Red Hat's Keycloak login management tool

To do this, you must download the keycloak (https://www.keycloak.org/downloads) or docker image
(https://hub.docker.com/r/jboss/keycloak/), and configure it as needed. For this example we created a realm
called spring-boot-keycloak, with a client login-app, and we specify that only users with the role "user" can access the application.

Attention: This project was developed only as an integration model with the password placed unencrypted in the properties file
just for easy maintenance. It is highly recommended that you do not place such sensitive data in such an easily accessible location.