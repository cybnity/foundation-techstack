import * as React from 'react';
import logo from './logo.svg';
import './App.css';
import { Button, Navbar, Container, Nav, Spinner } from 'react-bootstrap';

// Integration of keycloak ----------
// See https://github.com/react-keycloak/react-keycloak/blob/master/packages/web/README.md
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Welcome from "./Welcome";
import Secured from "./Secured";
import { ReactKeycloakProvider } from '@react-keycloak/web'
import keycloak from './keycloak';
import SplashScreen from "./SplashScreen";
// ------------`

// A component to be displayed while keycloak is being initialized, if not provided child components will be rendered immediately
const KeycloakLoading = () => (
  <div>
    <Spinner animation="border" role="status" variant="primary">
      <span className="visually-hidden">Loading...</span>
    </Spinner>
  </div>
)


// More on routes & roles on https://cagline.medium.com/authenticate-and-authorize-react-routes-component-with-keycloak-666e85662636

const App = (event) => {
  const [keycloakReady, setKeycloakReady] = React.useState(false);

  const handleBusEvent = (channel, msg)  => {
    event.onEvent(channel, msg);
  };

  const onKeycloakEvent = (event, error) => {
        console.log(`Keycloak Event ${event}`);
        // onReady, onInitError, onAuthSuccess, onAuthError, onAuthRefreshSuccess, onAuthRefreshError, onTokenExpired, onAuthLogout
        if(event && event === 'onReady'){
          setKeycloakReady(true);
        }
  };

  const onKeycloakTokens = (tokens) => {
      console.log('onKeycloakTokens', tokens);
  };

  return (
   <ReactKeycloakProvider authClient={keycloak} initOptions={{
     onLoad: "login-required"
   }} LoadingComponent={<KeycloakLoading />}
    keycloak={keycloak} onEvent={onKeycloakEvent} onTokens={onKeycloakTokens}
    >

    <SplashScreen keycloakReady={keycloakReady}/>

     <BrowserRouter>
       <div className="App">
         <Navbar bg="primary" variant="dark" expand="lg">
          <Container fluid>
            <Navbar.Brand href="/">
              <img
                alt=""
                src={logo}
                width="30"
                height="30"
                className="d-inline-block align-top"
              />{' '}
              CYBNITY React
            </Navbar.Brand>

            <Nav className="me-auto">
              <Nav.Link href="/">Public Screen</Nav.Link>
              <Nav.Link href="/secured">Secured Screen</Nav.Link>
            </Nav>

            <Navbar.Collapse className="justify-content-end">
              <Navbar.Text>
                {!keycloak.authenticated ? 'Not authenticated user' : ''}
              </Navbar.Text>

              <Button as="a" onClick={() => keycloak.logout()}>Logout</Button>
            </Navbar.Collapse>
          </Container>
        </Navbar>

         <Routes>
           <Route exact path="/" element={<Welcome />} />
           <Route path="/secured" element={<Secured onEvent={handleBusEvent}  />} />
         </Routes>

       </div>
     </BrowserRouter>
   </ReactKeycloakProvider>
  );
};

export default App;
