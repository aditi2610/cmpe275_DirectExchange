import {BrowserRouter as Router} from 'react-router-dom'
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from './Header/index.js';
import Body from './Body';

function App() {
  return (
    <div className="App">
      <div className="App">
        <Router>
          <Header/>
          <br/>
          <br/>
          <Body/>
        </Router>
      </div>
    </div>
  );
}

export default App;
