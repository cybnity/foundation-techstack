import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// --------- VERTX EVENT BUS INTEGRATION -------
//import SockJS from 'https://unpkg.io/sockjs-client@1.6.1/dist/sockjs.min.js';
//import EventBus from 'https://unpkg.io/@vertx/eventbus-bridge-client.js@1.0.0-2/vertx-eventbus.js';
import EventBus from 'vertx3-eventbus-client';

var busOptions = {
   vertxbus_reconnect_attempts_max: Infinity, // Max reconnect attempts
   vertxbus_reconnect_delay_min: 1000, // Initial delay (in ms) before first reconnect attempt
   vertxbus_reconnect_delay_max: 5000, // Max delay (in ms) between reconnect attempts
   vertxbus_reconnect_exponent: 2, // Exponential backoff factor
   vertxbus_randomization_factor: 0.5 // Randomization factor between 0 and 1
};

var eb = new EventBus('http://localhost:8080/eventbus', busOptions);

// Set up event bus handlers...
eb.onopen = function() {
  console.log("Vert.x event bus opened");
  eb.enableReconnect(true);
  // Set a handler to receive UI capabilities answers over the event bus
  eb.registerHandler('aap.out', function(error, message) {
    console.log('received a message from assetcontrol.out: ' + JSON.stringify(message));
  });
}

eb.onreconnect = function() {
  console.log("Vert.x event bus reconnected");
}; // Optional, will only be called on re-connections

// --------- VERTX EVENT BUS INTEGRATION END -------

const sendToBus = (channel, jsonMessage) => {
  console.log("message=" + JSON.stringify(jsonMessage));
  console.log("channel=" + channel.toString());
  // CONTINUER ICI CAR RIEN NE SE PASSE (mettre en async !)
  
  eb.send(channel.toString(), jsonMessage);
  console.log("Message sent to backend api");
};

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App onEvent={sendToBus} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
