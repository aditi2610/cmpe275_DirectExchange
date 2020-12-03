import React from 'react';
import { Redirect } from 'react-router-dom';
import { useDataContext } from '../App';

const SignOut = () => {
  localStorage.removeItem('email');
  localStorage.removeItem('nickName');
  localStorage.removeItem('userId');
  const {data,setData} = useDataContext();
  setData({...data,logggedIn: true});
  return <Redirect to={`/home`} />;
};

export default SignOut;