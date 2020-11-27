import React from 'react';
import { Redirect } from 'react-router-dom';

const SignOut = () => {
  localStorage.removeItem('email');
  localStorage.removeItem('userType');
  return <Redirect to={`/home`} />;
};

export default SignOut;