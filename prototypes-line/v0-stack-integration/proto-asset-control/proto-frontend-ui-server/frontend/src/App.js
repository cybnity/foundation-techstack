import * as React from 'react';
import logo from './logo.svg';
import './App.css';
import Button from 'react-bootstrap/Button';
import Stack from 'react-bootstrap/Stack';
import Accordion from 'react-bootstrap/Accordion';
import Form from 'react-bootstrap/Form';

const Create = (props) => {
    const [assetNameTerm, setAssetName] = React.useState('');
    const [assetDescTerm, setAssetDesc] = React.useState('');
    const [existingAssetDescription, setIsEmpty] = React.useState('');

    const handleNameChange = (event) => {
      setAssetName(event.target.value);
    };

    const handleDescriptionChange = (event) => {
      setAssetDesc(event.target.value);
      if (event.target.value === '') {
       	setIsEmpty('');
      } else {
       	setIsEmpty('including description');
      }
    };

    function generateUUIDUsingMathRandom() {
      var d = new Date().getTime();// Timestamp
      var d2 = (performance && performance.now && (performance.now()*1000)) || 0;//Time in microseconds since page-load or 0 if unsupported
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
          var r = Math.random() * 16;//random number between 0 and 16
          if(d > 0){//Use timestamp until depleted
              r = (d + r)%16 | 0;
              d = Math.floor(d/16);
          } else {//Use microseconds since page-load if supported
              r = (d2 + r)%16 | 0;
              d2 = Math.floor(d2/16);
          }
          return (c === 'x' ? r : (r & (0x3 | 0x8))).toString(16);
      });
    }

    function handleCreateAsset() {
      var jsonBody = {
        occurredOn: '',
        correlationId: generateUUIDUsingMathRandom(),
        id: generateUUIDUsingMathRandom(),
        type: 'CommandEvent',
        name: 'createAsset',
        inParameters: {
          domain: 'asset_control',
          name: assetNameTerm,
          type: 'asset',
          description: assetDescTerm
        }
      };
      // Send to event busbroker
      props.onCommit('aap.in', jsonBody);
    }

    return (
      <React.Fragment>
        <Form>
          <Form.Group className="mb-3">
            <Form.Label>Asset name</Form.Label>
            <Form.Control type="text" placeholder="Enter an asset label" id="asset_name" onChange={handleNameChange}/>
            <Form.Text className="text-muted">
              Unique label to logically simplify the future search of the asset ;)
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="asset_description">
            <Form.Label>Asset description</Form.Label>
            <Form.Control type="text" placeholder="Give a short presentation" onChange={handleDescriptionChange}/>
          </Form.Group>
          <Button as="a" variant="primary" onClick={handleCreateAsset}>
            Validate Creation
          </Button>
        </Form>
        <Form.Text className="text-muted">Ready to commit the creation of a new asset named {assetNameTerm} {existingAssetDescription}</Form.Text>
      </React.Fragment>
    );
}

const App = (event) => {
  //const initialAssets = React.useState([]);
  //const findAsyncAssets = () => Promise.resolve({data: {assets: initialAssets}});

  //const [assets, setAssets] = React.useState([]);

  //React.useEffect(() => {
    // Read data asynchronouslt from backend server
    //findAsyncAssets().then(result => {
      //setAssets(result.data.assets);
    //});
  //},[]);

  function getTitle(title) {
      return title;
  }

  const handleCreate = (channel, msg)  => {
    event.onEvent(channel, msg);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Hello, {getTitle('React')} world!</h1>
        <br/>
        <Accordion defaultActiveKey="0">
          <Accordion.Item eventKey="0">
            <Accordion.Header>How to create a new asset?</Accordion.Header>
            <Accordion.Body><Create onCommit={handleCreate} /></Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="1">
            <Accordion.Header>ReactJS, what is it?</Accordion.Header>
            <Accordion.Body><p>
            <img valign="middle" src={logo} className="App-logo" alt="logo" width="100px"/>Edit <code>src/App.js</code> and save to reload.
            </p>
            <a
              className="App-link"
              href="https://reactjs.org"
              target="_blank"
              rel="noopener noreferrer"
            >
              Learn React
            </a></Accordion.Body>
          </Accordion.Item>
        </Accordion>
      </header>
    </div>
  );
}

export default App;
