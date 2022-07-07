# CYBNITY SYSTEMS DEPLOYMENT & START
## Backend UI server run
From shell command line:
* Package the server service:

  ```shell
  mvn package
  ```
  
* Start the server Vert.x server-side backend system:

  ```shell
  mvn exec:java
  ```

# INTEGRATION TEST PROTOCOL
From shell command line:
1. Test that ui backend system give answer about sent parameter to the requester:

  ```shell
  curl -v http://localhost:8888\?name\="Olivier"
  ```
  