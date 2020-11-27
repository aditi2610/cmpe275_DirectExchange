import React from 'react';
import Switch from 'react-bootstrap/esm/Switch';
import { Route } from 'react-router-dom';
import Home from './Home';
import MyOffers from './MyOffers';
import PostNewOffer from './PostNewOffer';
import SignIn from './SignIn';
import SignOut from './SignOut';

function Body(props) {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/my-offers" component={MyOffers} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/sign-in" component={SignIn} />
      <Route exact path="/sign-out" component={SignOut} />
      <Route exact path="/post-offer" component={PostNewOffer} />
    </Switch>
  );
}

export default Body;