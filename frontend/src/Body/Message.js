import React, { Component } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import axios from 'axios';
import { rooturl } from '../config/config';
import emailjs, { init } from 'emailjs-com';

function Message(props) {
    let receiverEmailId = props.match.params.receiverEmailId;
    console.log("Email : " + receiverEmailId);
    init("user_Ec6uKvIdbgBq1PetK5PwC");
    // this.inputHandler = this.inputHandler.bind(this);
    let [responseMsg, setResponseMsg] = React.useState('');


    let handleSubmit = (e) => {
        e.preventDefault();
        console.log(" Send Emails: " + e.target);
        emailjs.sendForm("service_f8jmo8y", "template_tzy5aro", e.target, "user_Ec6uKvIdbgBq1PetK5PwC")
            .then((result) => {
                console.log(result.text);
            }, (error) => {
                console.log(error.text);
            });

    }

    let inputHandler = (event) => {
        const target = event.target;
        const value = target.value;

        const name = target.name;

        this.setState({
            [name]: value
        });
    }


    return (
        <Container>
            <Form onSubmit={e => handleSubmit(e)}>
                <h3>Send Message</h3>
                <br />
                {responseMsg}
                <Form.Group as={Row}>
                    <Form.Label column sm="3">
                        Message:
          </Form.Label>
                    <Col sm="9">
                        <Form.Control name="message" type="text" placeholder="Type Your message here" />
                    </Col>
                </Form.Group>
                <Col>
                    <Button variant="primary" type="submit">Submit</Button>
                </Col>
            </Form>
        </Container>
    );
}


export default Message;