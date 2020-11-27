import {BrowserRouter as Router} from 'react-router-dom'
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from './Header.js';
import Body from './Body.js';

function App() {
  return (
    <div className="App">
      <div className="App">
        <Router>
          <Header/>
          <br/>
          <Body/>
        </Router>
      </div>
    </div>
  );
}

export default App;
