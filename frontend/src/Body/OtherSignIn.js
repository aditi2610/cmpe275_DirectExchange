import React, { Component } from "react"

import { Modal, Container, Row, Col, Button, Tabs, Tab, Form, Alert } from 'react-bootstrap';
import RedirectToHome from './RedirectToHome';
import { Redirect } from 'react-router-dom';
import { useDataContext } from './../App';
import axios from 'axios';
import { rooturl } from '../config/config';
import { Link } from 'react-router-dom';

import firebase from "firebase"
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"

firebase.initializeApp({
    apiKey: "AIzaSyAXodreKh2F7xEYCV-gJhlCFsQjdS_H15E",
    authDomain: "directexchange-5db09.firebaseapp.com"
})

function OtherSignIn({showUserLoginError,setShow}) {
    const { data, setData } = useDataContext();
    const uiConfig = {
        signInFlow: "popup",
        signInOptions: [
            firebase.auth.GoogleAuthProvider.PROVIDER_ID,
            firebase.auth.FacebookAuthProvider.PROVIDER_ID
        ],
        callbacks: {
            signInSuccess: () => false
        }
    }
    React.useEffect(() => {
        firebase.auth().onAuthStateChanged(user => {
            console.log("user", user)
            console.log(firebase.auth().currentUser.displayName, " displayName");
            const params = {
                email: firebase.auth().currentUser.email,
                username: firebase.auth().currentUser.displayName

            }
            axios.defaults.withCredentials = true;
            axios.post(rooturl + '/user/login/oauth',null,{validateStatus: false, params: params })
                .then(response => {
                    if (response.status === 200) {
                        localStorage.setItem("nickName", response.data.nickName);
                        localStorage.setItem("userId", response.data.id);
                        localStorage.setItem("email", params.email);
                        setShow(false);
                        setData({ ...data, logggedIn: true });
                    }
                    if(response.status === 401){
                        showUserLoginError(<Alert variant="danger">{response.data['Bad Request']['Error Message']}</Alert>);
                    }
                })
                .catch(err => {
                    
                })

        })
    },[]);
    return (
        <div className="App">
            <StyledFirebaseAuth uiConfig={uiConfig} firebaseAuth={firebase.auth()}/>
        </div>
    )
}

export default OtherSignIn;