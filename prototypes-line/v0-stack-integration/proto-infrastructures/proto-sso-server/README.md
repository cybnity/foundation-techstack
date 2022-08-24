# FROM PROJECT DIRECTORY

## BUILD OF IMAGE
Build a Docker image of container from shell command (including force remove of intermediary container) from shell command line:

  ```shell
  docker-compose build --force-rm
  ```

## CHECK IMAGE
Scan image vulnerabilities from shell command line:

  ```shell
  docker scan agnet/cybnity-keycloak-distribution
  ```

## START IMAGE
Run a docker container without shell console (as a daemon without interaction) or without -d argument to maintain shell console opened from shell command line:

  ```shell
  docker-compose up -d
  ```

## RUN
When start process is executed, a Quarkus Keycloack server in development mode is executed:
  - A Keycloack server instance is running;
  - A admin account is automatically created and ready for use (authentication ready from user=admin and password=admin) as defined in Docker compose file;
  - The Keycloak web application instance is accessible from web browser at http://localhost:8082/ via account:
    - login = admin
    - password = admin

### Integration with CYBNITY Frontend ui
#### Realm registration
Create a Realm similar to a Tenant Id relative to an organization (e.g named cybnity) from the top-left select menu with click on `Create Realm` button.

#### Set the Cybnity realm configuration
Set the Security defenses configuration:
  - Navigate to `Realm Settings > Cybnity > Security Defenses` to configure the Clickjacking security:
    - Default `SAMEORIGIN` value of X-Frame-Options
      - See https://datatracker.ietf.org/doc/html/rfc7034#section-2.2.1 for more details about X-Frame-Options
      - See https://wjw465150.gitbooks.io/keycloak-documentation/content/server_admin/topics/threat/clickjacking.html for help about mitigation of Clickjacking
    - Default `frame-src 'self'; frame-ancestors 'self'; object-src 'none';` value of `Content-Security-Policy`
      - See https://www.w3.org/TR/CSP/#directive-frame-src about frame-src to restrict the URLS which may be loaded into nested browsing contexts
      - See https://www.w3.org/TR/CSP/#directive-frame-ancestors about frame-ancestors to define the URLs which can embed the resource using frame of iframe
      - See https://www.w3.org/TR/CSP/#directive-object-src about object-src to restrict URLS from which plugin context may be loaded

#### Clients registrations from Cybnity realm
Register a new Keycloak client dedicated to proto-frontend-ui-server (allowing user authentication from web browser when access to web UI) from the administration console:
  - Navigate to `Clients` and use `Create` button for add a new client
  - Set client description about web app source to integrate with Keycloak login:
    - Client ID: cybnity-frontend-ui-react
    - Client Protocol: openid-connect
    - Enabled: ON
    - Access Type: public
      Mandatory to be public allowing authentication via JS client executed on client-side (browser)
    - Standard Flow Enabled: ON
    - Direct Access Grants Enabled: ON
    - Root URL: http://localhost:3000/
    - Valid Redirect URL: http://localhost:3000/*
    - Admin URL: http://localhost:3000/
    - Web Origins: http://localhost:3000
    - Backchannel Logout Session Required: ON

Register a new Keycloak client dedicated to proto-backend-ui-server (allowing sso check by server-side during reception of Event bus messages) from the administration console:
  - Navigate to `Clients` and use `Create` button for add a new client
  - Set client description about web app source to integrate with Keycloak sso checking:
    - Client ID: cybnity-backend-api
    - Client Protocol: openid-connect
    - Enabled: ON
    - Access Type: bearer-only
    - Admin URL: http://localhost:3000/
    - OpenID Connect Compatibility Mode:
      - Use Refresh Token: ON
  - Go to `Credentials` section and define:
    - Client Authenticator: Client Id and Secret
    - Generate secret from button
  - Go to `Keys` section and define:
    - Certificate via button

#### Applicative roles definition
- From `Clients > cybnity-frontend-ui-react > Roles` section, add a new standard role named "user"
  - From `Clients > cybnity-backend-api > Roles` section, add a new standard role named "user"
- From `Roles > Realm Roles` section, add a new realm role named "app-user"
  - Set `Composite Roles` value to ON
  - In `Composite Roles` sub-panel:
    - Associate each client's role with the "app-user" realm role:
      - Select and associate "user" role to cybnity-backend-api
      - Select and associate "user" role to cybnity-frontend-ui-react client

#### Test user account creation
By default, new created Realm has none user.
Create a test account declared for a test user allowing to use the proto-frontend-ui-server web UI:
  - Add a test user account from the `Users` menu as:
    - Username: tester
    - User enabled: true
  - From `Users > tester` account management UI:
    - From `Details` panel:
      - Toggle the `Email Verified` to ON and save
    - Go to `Credentials` panel:
      - set a password for the tester account and toggle `Temporary` to OFF
      - validate password creation via `Set Password` button

#### Test user account creation
By default, new created Realm has none user.
Create a test account declared for a test user allowing to use the proto-frontend-ui-server web UI:
  - Add a test user account from the `Users` menu as:
    - Username: tester
    - User enabled: true
  - From `Users > tester` account management UI:
    - From `Details` panel:
      - Toggle the `Email Verified` to ON and save
    - Go to `Credentials` panel:
      - Set a password for the tester account and toggle `Temporary` to OFF for avoid password change required at first login
      - Validate password creation via `Set Password` button
    - Go to `Role Mappings` panel:
      - Assign the "app-user" realm role to the user allowing him to have automatically assigned mapped role defined for each client

#### Client scope creation
If you have many applications you need to secure and register within your organization, it can become tedious to configure role scope mappings for each of these clients. Keycloak allows you to define a shared client configuration in an entity called a client scope.
If we want to get client roles in a custom key in the JWT token, we have to add client scope to put client roles in access token.

Go to `Client Scopes` and create a new scope:
  - Name: clienbt_roles_react_app
Go to `Client Scopes > client_roles_react_app > Mappers` panel:
  - Create a new mapper:
    - Name: roles
    - Mapper Type: User Client Role
    - Client ID: cybnity-frontend-ui
    - Multivalued: ON
    - Token Claim Name: roles
    - Claim JSON Type: String

Go to `Clients > cybnity-frontend-ui > Client Scopes` panel for add the custom scope previously created:
  - Select client_roles_react_app from the "Default Client Scopes" list, and assign it via the "Add selected" button

From now, we can get the client roles from the JWT token with __roles__ key, allowing to enable/disable proto-frontend-ui-server's view to particular roles received from the token.

### Tests
#### Test user account authentication
When disconnected of any user account:
  - Open http://localhost:8082/realms/cybnity/account/ from a web browser:
    - The account management UI is shown including panels of Keycloak features;
    - Click on `Personal info` for try to manage personal basic informations;
      - The standard Sign page is shown allowing to authenticate the test user account;
      - Re-use the Tester account credential for try authentication;
      - When authenticated, the personal information screen is shown with success.

#### Test of secured access to frontend
From [proto-front-end-ui web access](http://localhost:3000), try access to __Secured Screen__ with use of Keycloak test account.

## MONITOR
Check that startup log contains (ensuring that desired feature is enabled) the following line:

  ```shell
  INFO  [org.key.com.Profile] (main) Preview feature enabled: token_exchange
  ```

Read logs from shell command line:

  ```shell
  docker-compose logs -f
  ```

## STOP IMAGE
Kill container from shell command line:

  ```shell
  docker-compose kill
  ```

or

  ```shell
  docker kill agnet/cybnity-keycloak-distribution
  ```

## PUBLISH TO DOCKER REPOSITORY
Create tag for built container distribution specific to CYBNITY technology from shell command line:

  ```shell
  docker tag cybnity-keycloak-distribution agnet/cybnity-keycloak-distribution:latest
  ```

Push docker image to online CYBNITY private Docker repository from shell command line:

  ```shell
  docker push agnet/cybnity-keycloak-distribution:latest
  ```
