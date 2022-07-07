# CYBNITY SYSTEMS DEPLOYMENT & START
## Backend UI server run
From shell command line:
* Package the server service:

  ```shell
  mvn package
  ```
  
* Start the server Vert.x server-side backend system: mvn exec:java

# INTEGRATION TEST PROTOCOL

1. Test that ui backend system give answer about sent parameter to the requester
     - From shell command line: curl -v http://localhost:8888\?name\="Olivier"