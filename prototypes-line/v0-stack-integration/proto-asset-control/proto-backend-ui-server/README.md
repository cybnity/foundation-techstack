# CYBNITY PROTOTYPE SYSTEMS DEPLOYMENT & START
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

## UI BACKEND SUPPORTED ENDPOINTS (HTTP, API, EVENTBUS)
### From shell command line
1. Test that ui backend give answer about sent parameter (over HTTP/GET protocol) without specific route to defined UI capability:

    ```shell
    curl -v http://localhost:8080\?name\="Olivier"
    ```
  
2. Test that UI backend give answer about sent parameter (over HTTP/GET protocol) from specific route defined about UI capability handled:
 
    ```shell
    curl -v http://localhost:8080/assetcontrol/\?name\="olivier" 
    ```
    
###From web browser
1. Test that UI backend give static resources with default index.html reactive page display

    ```shell
    http://localhost:8080/static/
    ```
    
2. Test that UI backend give answer about sent parameter (over HTTP/GET protocol)


    ```shell
    http://localhost:8080/assetcontrol/?name=olivier
    ```
