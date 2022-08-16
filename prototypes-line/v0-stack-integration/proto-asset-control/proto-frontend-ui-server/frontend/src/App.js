import * as React from 'react';
import logo from './logo.svg';
import './App.css';

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
       	setIsEmpty(': ');
      } else {
       	setIsEmpty('including description : ');
      }
    };

    const InputWithLabel = ({
      id, label, value, type = 'text', onInputChange,
      }) => (
        <React.Fragment>
          <label htmlFor={id}>{label}: </label>
          &nbsp;
          <input id={id} type={type} vamlue={value} onChange={onInputChange} />
        </React.Fragment>
    );

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
          return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
      });
    }

    function handleCreateAsset() {
      var msgBody = "{occurredOn: '', correlationId: " + generateUUIDUsingMathRandom()
      + ", id: " + generateUUIDUsingMathRandom() + ", type: 'CommandEvent', name: 'createAsset', inParameters: {domain: 'asset_control', name: '"
      + assetNameTerm + "', type: 'asset', description: '"
      + assetDescTerm + "'}}";
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
        <label htmlFor="asset_name">Named: </label>
        <input id="asset_name" type="text" onChange={handleNameChange} />
        <label htmlFor="asset_description"> with description: </label>
        <input id="asset_description" type="text" onChange={handleDescriptionChange} />
        <p>Ready to commit the creation of a new asset named {assetNameTerm} {existingAssetDescription}</p>
        <button onClick={handleCreateAsset}>
          COMMIT
        </button>

      </React.Fragment>
    );
}

const App = (event) => {
  const initialAssets = React.useState([]);
  const findAsyncAssets = () =>
    Promise.resolve({data: {assets: initialAssets}});

  const [assets, setAssets] = React.useState([]);

  React.useEffect(() => {
    // Read data asynchronouslt from backend server
    findAsyncAssets().then(result => {
      setAssets(result.data.assets);
    });
  },[]);

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
        <h2>Create new asset</h2>
        <Create onCommit={handleCreate} />
        <p>
        <img valign="middle" src={logo} className="App-logo" alt="logo" width="100px"/>Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
