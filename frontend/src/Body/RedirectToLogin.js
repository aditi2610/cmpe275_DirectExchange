import React from 'react';
import { Redirect } from 'react-router-dom';

const RedirectToLogin = () => {
  const email = localStorage.getItem('email');
  console.log('@@@@@@@@@@',email);
  if (!email) {
    return <Redirect to={`/sign-in`} />;
  }
  return null;
};

export default RedirectToLogin;