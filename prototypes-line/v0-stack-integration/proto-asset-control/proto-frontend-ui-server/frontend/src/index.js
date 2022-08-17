import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';

// --------- VERTX EVENT BUS INTEGRATION -------
// See https://www.demo2s.com/node.js/node-js-vertx3-eventbus-client-eventbus-send-string-function-call.html
import EventBus from 'vertx3-eventbus-client';

var busOptions = {
   vertxbus_reconnect_attempts_max: Infinity, // Max reconnect attempts
   vertxbus_reconnect_delay_min: 1000, // Initial delay (in ms) before first reconnect attempt
   vertxbus_reconnect_delay_max: 5000, // Max delay (in ms) between reconnect attempts
   vertxbus_reconnect_exponent: 2, // Exponential backoff factor
   vertxbus_randomization_factor: 0.5 // Randomization factor between 0 and 1
};

var eb = new EventBus('http://localhost:8080/eventbus/', busOptions);

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

eb.onerror = function(error) {
  console.log("Event bus error: " + JSON.stringify(error));
}

// --------- VERTX EVENT BUS INTEGRATION END -------

const sendToBus = (channel, jsonMessage) => {
  try {
    eb.send(channel.toString(), jsonMessage, function() {
        console.log("Message successfully sent to backend api (channel: " + channel.toString() + "): " + JSON.stringify(jsonMessage));
    });
  } catch (err) {
    console.log(err);
  }
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
