import React from 'react';
import { Redirect } from 'react-router-dom';

const SignOut = () => {
  localStorage.removeItem('email');
  localStorage.removeItem('nickName');
  localStorage.removeItem('userId');
  return <Redirect to={`/home`} />;
};

export default SignOut;