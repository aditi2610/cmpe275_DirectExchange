import React from 'react';
import Switch from 'react-bootstrap/esm/Switch';
import { Route } from 'react-router-dom';
import Home from './Home';
import SignIn from './SignIn';
import SignOut from './SignOut';
import AddBankAccount from './AddBankAccount';
import MyOffers from './offer/MyOffers';
import PostNewOffer from './offer/PostNewOffer';
import EditOffer from './offer/EditOffer';
import BrowseOffers from './offer/BrowseOffers';

function Body(props) {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/my-offers" component={MyOffers} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/sign-in" component={SignIn} />
      <Route exact path="/sign-out" component={SignOut} />
      <Route exact path="/post-offer" component={PostNewOffer} />
      <Route exact path="/offer/:id/edit" component={EditOffer} />
      <Route exact path="/addBank" component={AddBankAccount} />
      <Route exact path="/browse-offers" component={BrowseOffers} />
    </Switch>
  );
}

export default Body;