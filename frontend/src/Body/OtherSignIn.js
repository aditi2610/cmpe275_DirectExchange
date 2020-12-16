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

class OtherSignIn extends Component {
    state = { isSignedIn: false }
    uiConfig = {
        signInFlow: "popup",
        signInOptions: [
            firebase.auth.GoogleAuthProvider.PROVIDER_ID,
            firebase.auth.FacebookAuthProvider.PROVIDER_ID
        ],
        callbacks: {
            signInSuccess: () => false
        }
    }


    componentDidMount = () => {

        firebase.auth().onAuthStateChanged(user => {

            console.log("user", user)
            console.log(firebase.auth().currentUser.displayName, " displayName");
            const params = {
                email: firebase.auth().currentUser.email,
                username: firebase.auth().currentUser.displayName

            }
            // console.log("data going to add bank account" + JSON.stringify(params));
            //set the with credentials to true
            axios.defaults.withCredentials = true;
            axios.post(rooturl + '/user/login/oauth', null, { params })
                .then(response => {
                    if (response.status === 200) {
                        localStorage.setItem("nickName", response.data.nickName);
                        localStorage.setItem("userId", response.data.id);
                        localStorage.setItem("email", params.email);
                        const { data, setData } = useDataContext();
                        setData({ ...data, logggedIn: true });
                        // this.setState({ isSignedIn: true });
                        this.setState({ isSignedIn: !!user })
                    }
                })
                .catch(err => {
                    this.setState({ errorMessage: "error" });
                })

        })
    }

    render() {
        return (
            <div className="App">

                {this.state.isSignedIn ? (
                    <RedirectToHome />
                    // <span>
                    //     <div>Signed In!</div>
                    //     <button onClick={() => firebase.auth().signOut()}>Sign out!</button>
                    //     <h1>Welcome {firebase.auth().currentUser.displayName}</h1>
                    //     <h1> {firebase.auth().currentUser.email}</h1>
                    //     <img
                    //         alt="profile picture"
                    //         src={firebase.auth().currentUser.photoURL}
                    //     />
                    // </span>
                ) : (
                        <StyledFirebaseAuth
                            uiConfig={this.uiConfig}
                            firebaseAuth={firebase.auth()}
                        />
                    )}

            </div>
        )
    }
}

export default OtherSignIn;