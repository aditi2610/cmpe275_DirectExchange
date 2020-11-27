import React from 'react';
import Switch from 'react-bootstrap/esm/Switch';
import { Route } from 'react-router-dom';
import Home from './Home';
import SignIn from './SignIn';
import SignOut from './SignOut';

function Body(props) {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/sign-in" component={SignIn} />
      <Route exact path="/sign-out" component={SignOut} />
    </Switch>
  );
}

export default Body;