import React from 'react';
import {Modal, Container, Row, Col, Button, Tabs, Tab, Form, Alert} from 'react-bootstrap';
import RedirectToHome from './RedirectToHome';
import { Redirect } from 'react-router-dom';
import { useDataContext } from './../App';
import axios from 'axios';
import { rooturl } from '../config/config';

function SignIn(props) {

  const [show, setShow] = React.useState(true);
  const [userLoginError, showUserLoginError] = React.useState('');
  const [userRegisterError, showUserRegisterError] = React.useState('');
  const [closeModal,setCloseModal] = React.useState(null);
  axios.defaults.withCredentials = true;

  const handleClose = (e) => {
    setCloseModal(<Redirect to={`/home`} />);
  }

  const handleRegisterSubmit =(e) => {
    e.preventDefault();
    const form = e.currentTarget;
    // set the with credentials to true
    // make a post request with the user data
    const params = {
      nickName:form.nickName.value,
      email:form.email.value,
      password:form.password.value,
    };
    console.log(JSON.stringify(params))
    axios.defaults.withCredentials = true;
    axios.post(rooturl+'/user/register',null,{params})
    .then((response) => {
      console.log(response)
      if (response.status === 201) {
        showUserRegisterError(<Alert variant="success">Registration Successful. Please login once your account is verified</Alert>);
      }else{
        console.log(response.status)
        let errors = Object.values(response.data || {'error' : ['Something went wrong']});
        showUserRegisterError(errors.map(error => {
          return <Alert variant="danger">{error}</Alert>
        }));
      }
    })
    .catch(err=>{
      console.log(err)
      let errors = Object.values(err.data || {'error' : ['Email or NickName already registered']});
        showUserRegisterError(errors.map(error => {
          return <Alert variant="danger">{error}</Alert>
  }))
})
}
  

  const handleUserSigninSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;
    // set the with credentials to true
    // make a post request with the user data
    const formData = {
      email_id: form.email.value,
      password: form.password.value,
    };
    // axios.post(`${rooturl}/core/user/login`, formData,{ validateStatus: false })
    // .then((response) => {
    //   console.log('Status Code : ', response.status);
    //   if (response.status === 200) {
    //       localStorage.setItem('token', response.data.token);
    //       localStorage.setItem("userType", response.data.user_details.user_type);
    //       localStorage.setItem("email", formData.email);
    //       setShow(false);
    //   }else{
    //     let errors = Object.values(response.data || {'error' : ['Something went wrong']});
    //     showUserLoginError(errors.map(error => {
    //       return <Alert variant="danger">{error}</Alert>
    //     }));
    //   }
    //});
  }

  return (
    <Modal show={show} onHide={handleClose} aria-labelledby="contained-modal-title-vcenter">
      <RedirectToHome />
      {closeModal}
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Welcome to Direct Exchange
        </Modal.Title>
      </Modal.Header>
      <Modal.Body className="show-grid">
      <Tabs defaultActiveKey="signin">
        <Tab eventKey="signin" title="User Sign In">
          <Container>
            <br />
            <Form onSubmit={handleUserSigninSubmit}>
              {userLoginError}
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" name='email' placeholder="Enter email" required/>
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" name='password' placeholder="Password" required/>
              </Form.Group>
              <Button variant="primary" type="submit" block>
                Sign In
              </Button>
            </Form>
          </Container>
        </Tab>
        <Tab eventKey="register" title="User Register">
          <Container>
            <br />
            <Form onSubmit={handleRegisterSubmit}>
              {userRegisterError}            
              <Form.Group controlId="formBasicFirstName">
                <Form.Label>NickName</Form.Label>
                <Form.Control type="text" name='nickName' placeholder="Nick Name" required/>
              </Form.Group>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" name='email' placeholder="Enter email" required/>
              </Form.Group>
              <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" name='password' placeholder="Password" required/>
                <Form.Text className="text-muted">
                  At least 8 characters
                </Form.Text>
                <Form.Text className="text-muted">
                  Mix of letters and numbers
                </Form.Text>
              </Form.Group>
              <Button variant="primary" type="submit" block>
                Submit
              </Button>
            </Form>
          </Container>
        </Tab>
      </Tabs>
      </Modal.Body>
    </Modal>
  );
}

export default SignIn;