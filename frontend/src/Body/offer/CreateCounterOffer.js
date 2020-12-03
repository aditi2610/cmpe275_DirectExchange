import Axios from 'axios';
import React from 'react';
import { Alert, Container, Form, Row, Col,Button } from 'react-bootstrap';
import { rooturl } from '../../config/config';

function CreateCounterOffer(props) {
  let parentOfferId = props.match.params.offerId;
  let [responseMsg,setResponseMsg] = React.useState('');

  let handleSubmit = (e) => {
    e.preventDefault();
    let form = e.target;
    let formData = {
      "isCounterOffer" : true,
      "parentOffer" : {
        "id" : parseInt(parentOfferId),
      },
      "amount" :parseInt(form.amount.value),
      "exchangeRate" :parseInt(form.exchangeRate.value),
      "user": {
          "id" :parseInt(localStorage.getItem('userId')),
          "isVerified" :true,
      }
    }
    console.log(formData);
    Axios.post(`${rooturl}/offer`,formData,{validateStatus: false})
    .then(response => {
      if(response.status === 201){
        form.reset();
        setResponseMsg(<Alert variant='success'>Counter Offer submitted successfully!</Alert>)
      }else{
        setResponseMsg(<Alert variant='danger'>{response.data["Bad Request"] && response.data["Bad Request"]['Error Message'] ? (response.data["Bad Request"]['Error Message']) : ('Something went wrong')}</Alert>)
      }
    })
  }
  
  return (
    <Container>
      <Form onSubmit={e => handleSubmit(e)}>
        <h3>Create counter offer</h3>
        <br/>
        {responseMsg}
        <Form.Group as={Row}>
          <Form.Label column sm="3">
            Amount
          </Form.Label>
          <Col sm="9">
            <Form.Control name="amount" required type="number" step="0.1"  placeholder="Amount" />
          </Col>
        </Form.Group>
        <Form.Group as={Row}>
          <Form.Label column sm="3">
            Exchange Rate
          </Form.Label>
          <Col sm="9">
            <Form.Control required name="exchangeRate" type="number" step="0.001" placeholder="Exchange Rate" min="0.001" />
          </Col>
        </Form.Group>
        <Col>
          <Button variant="primary" type="submit">Submit</Button>
        </Col>
      </Form>
    </Container>
  );
}


export default CreateCounterOffer;