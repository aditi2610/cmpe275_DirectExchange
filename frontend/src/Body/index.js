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
import PrevailingRates from './PrevailingRates'
import OtherSignIn from './OtherSignIn';
import AcceptBrowseOffer from './offer/AcceptBrowseOffer';
import CreateCounterOffer from './offer/CreateCounterOffer';
import AcceptCounterOffer from './offer/AcceptCounterOffer';
import AcceptMatchingOffer from './offer/AcceptMatchingOffer';
import RedirectToLogin from './RedirectToLogin';
import MyTransactions from './Transaction/MyTransactions';
import Message from './Message';
import AcceptSplitOffer from './offer/AcceptSplitOffer';
import ReputationTransaction from './Transaction/ReputationTransaction';
import Report from './Report';

function Body(props) {
  return (
    <Switch>
      <Route exact path="/my-offers" component={MyOffers} />
      <Route exact path="/home" component={Home} />
      <Route exact path="/sign-in" component={SignIn} />
      <Route exact path="/sign-out" component={SignOut} />
      <Route exact path="/post-offer" component={PostNewOffer} />
      <Route exact path="/offer/:id/edit" component={EditOffer} />
      <Route exact path="/addBank" component={AddBankAccount} />
      <Route exact path="/browse-offers" component={BrowseOffers} />
      <Route exact path="/prevailing-rates" component={PrevailingRates} />
      <Route exact path="/other-signIn" component={OtherSignIn} />
      <Route exact path="/browse-offers/:offerId/:email/accept" component={AcceptBrowseOffer} />
      <Route exact path="/create-counter-offer/:offerId" component={CreateCounterOffer} />
      <Route exact path="/create-counter-offer/:offerId/:matchingOfferId" component={CreateCounterOffer} />
      <Route exact path="/create-counter-offer/:offerId/:matchingOfferId/:splitMatchedUserId" component={CreateCounterOffer} />
      <Route exact path="/accept-counter-offer/:offerId/:email/accept" component={AcceptCounterOffer} />
      <Route exact path="/matching-offer/:myOfferId/:acceptedOfferId/accept" component={AcceptMatchingOffer} />
      <Route exact path="/my-transactions" component={MyTransactions} />
      <Route exact path="/message/:receiverEmailId" component={Message} />
      <Route exact path="/accept-split-offer/:matchingOfferId/:splitOffer1/:splitOffer2/:aEqualsBPlusC" component={AcceptSplitOffer} />
      <Route exact path="/reputationTransaction/:userId" component={ReputationTransaction} />
      <Route exact path="/report" component={Report} />
    </Switch>
  );
}

export default Body;