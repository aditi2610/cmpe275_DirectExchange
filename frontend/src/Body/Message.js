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

    // to_name should be the one who is sending the email
    let handleSubmit = (e) => {
        console.log("Event:  " + e.target.message.value);

        e.preventDefault();
        let templateParams = {
            from_name: localStorage.getItem("nickName"),
            to_name: receiverEmailId,
            subject: 'Hello Somya',
            message: e.target.message.value,
        }
        emailjs.send("service_f8jmo8y", "template_tzy5aro", templateParams, "user_Ec6uKvIdbgBq1PetK5PwC")
            .then((result) => {
                console.log(result.text);
            }, (error) => {
                console.log(error.text);
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
                        <Form.Control name="message" type="text" placeholder="Type Your message here"
                        />
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