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
  - Create a Realm similar to a Tenant Id relative to an organization (e.g named cybnity) from the top-left select menu with click on `Create Realm` button;
  - By default, new created Realm has none user; so create a test user account from the `Users` menu as:
    - Username: tester
    - User enabled: true
  - From `Users > tester` account management UI:
    - From `Details` panel:
      - Toggle the `Email Verified` to ON and save
    - Go to `Credentials` panel:
      - set a password for the tester account and toggle `Temporary` to OFF
      - validate password creation via `Set Password` button

### Integration with CYBNITY Frontend ui
Register the React frontend app as a new Keycloak's client from the administration console:
  - Navigate to `Clients` and use `Create` button for add a new client
  - Set client description about web app source to integrate with Keycloak login:
    - Client ID: cybnity-frontend-ui-auth
    - Client Protocol: openid-connect
    - Root URL: http://localhost:3000/
    - Enabled: ON
    - Implicit Flow enabled: ON
    - Direct Access Grants Enabled: ON
    - Access Type: public
    - Web Origins: http://localhost:3000
  - Navigate to `Realm Settings > Cybnity > Security Defenses` to configure the Clickjacking security:
    - Change default `SAMEORIGIN` value of X-Frame-Options with `ALLOW-FROM http://localhost:3000` value
      - See https://datatracker.ietf.org/doc/html/rfc7034#section-2.2.1 for more details about X-Frame-Options
      - See https://wjw465150.gitbooks.io/keycloak-documentation/content/server_admin/topics/threat/clickjacking.html for help about mitigation of Clickjacking
    - Change default value of `Content-Security-Policy`
      - See https://www.w3.org/TR/CSP/#directive-frame-src about frame-src to restrict the URLS which may be loaded into nested browsing contexts
      - See https://www.w3.org/TR/CSP/#directive-frame-ancestors about frame-ancestors to define the URLs which can embed the resource using frame of iframe
      - See https://www.w3.org/TR/CSP/#directive-object-src about object-src to restrict URLS from which plugin context may be loaded

### Tests
#### Test user account authentication
When disconnected of any user account:
  - Open http://localhost:8082/realms/cybnity/account/ from a web browser:
    - The account management UI is shown including panels of Keycloak features;
    - Click on `Personal info` for try to manage personal basic informations;
      - The standard Sign page is shown allowing to authenticate the test user account;
      - Re-use the Tester account credential for try authentication;
      - When authenticated, the personal information screen is shown with success.

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
