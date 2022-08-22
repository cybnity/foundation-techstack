import * as React from 'react';
import logo from './logo.svg';
import './App.css';
import Button from 'react-bootstrap/Button';
import Stack from 'react-bootstrap/Stack';
import Accordion from 'react-bootstrap/Accordion';
import Form from 'react-bootstrap/Form';

// Integration of keycloak
// See https://scalac.io/blog/user-authentication-keycloak-1/
import { BrowserRouter, Route, Routes, Link } from "react-router-dom";
import Welcome from "./components/Welcome";
import Secured from "./components/Secured";
// ------------`

function App() {
 return (
   <BrowserRouter>
     <div className="App">
       <ul>
        <li><Link to="/">Public component</Link></li>
        <li><Link to="/secured">Secured component</Link></li>
       </ul>
       <Routes>
         <Route exact path="/" element={<Welcome />} />
         <Route path="/secured" element={<Secured />} />
       </Routes>
     </div>
   </BrowserRouter>
 );
}

export default App;
