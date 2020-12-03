import {BrowserRouter as Router} from 'react-router-dom'
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import Header from './Header/index.js';
import Body from './Body';
import React from 'react';
export const DataContext = React.createContext({});
export const useDataContext = () => React.useContext(DataContext);

const initialState = {logggedIn : false,
}

function App() {
  const [data, setData] = React.useState(initialState);
  return (
    <DataContext.Provider value={{ data, setData }}>
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
    </DataContext.Provider>
  );
}

export default App;
