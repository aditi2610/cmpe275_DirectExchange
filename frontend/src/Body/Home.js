import React from 'react';
import RedirectToLogin from './RedirectToLogin';

function Home(props) {
  return (
    <div>
      <RedirectToLogin/>
      <h4>Welcome to DirectExchange, Browse offers or Post one!</h4>
    </div>
  );
}

export default Home;