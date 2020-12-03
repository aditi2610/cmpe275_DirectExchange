import Axios from 'axios';
import React from 'react';
import { Alert, Button, Col, Container, Form, Row } from 'react-bootstrap';
import { rooturl } from '../../config/config';

function PostNewOffer(props) {
  let [responseMsg,setResponseMsg] = React.useState('');

  let handleSubmit = (e) => {
    e.preventDefault();
    let form = e.target;
    let formData = {
      "sourceCountry" :form.sourceCountry.value,
      "sourceCurrency" :form.sourceCurrency.value,
      "amount" :parseInt(form.amount.value),
      "destinationCountry" :form.destinationCountry.value,
      "destinationCurrency" :form.destinationCurrency.value,
      "exchangeRate" :parseFloat(form.exchangeRate.value),
      "expirationDate" :new Date(form.expirationDate.value).toISOString(),
      "isCounterOfferAllowed" :form.isCounterOfferAllowed.checked,
      "isSplitOfferAllowed" :form.isSplitOfferAllowed.checked,
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
        setResponseMsg(<Alert variant='success'>Offer submitted successfully!</Alert>)
      }else{
        setResponseMsg(<Alert variant='danger'>{response.data["Bad Request"] && response.data["Bad Request"]['Error Message'] ? (response.data["Bad Request"]['Error Message']) : ('Something went wrong')}</Alert>)
      }
    })
  }

  return (
    <Container>
      <Row>
        <Col sm='2'></Col>
        <Col>
          <Form onSubmit={e => handleSubmit(e)}>
            <h3>Post a new offer</h3>
            <br/>
            {responseMsg}
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Source Country
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select source country' name="sourceCountry" required as="select" defaultValue=''>
                <option value=''>Select Source Country</option>
                <option value='India'>India</option>
                <option value='Britan'>Britan</option>
                <option value='China'>China</option>
                <option value='USA'>USA</option>
              </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Source Currency
              </Form.Label>
              <Col sm="9">
                <Form.Control label='select source currency' name="sourceCurrency" required as="select" defaultValue=''>
                  <option value=''>Select Source Currency</option>
                  <option value='EUR'>EUR</option>
                  <option value='GBP'>GBP</option>
                  <option value='INR'>INR</option>
                  <option value='RMB'>RMB</option>
                  <option value='USD'>USD</option>
                </Form.Control>
              </Col>
            </Form.Group>
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
                Destination Country
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select Destination country' required name="destinationCountry" required as="select" defaultValue=''>
              <option value=''>Select Destination Country</option>
                <option value='India'>India</option>
                <option value='Britan'>Britan</option>
                <option value='India'>India</option>
                <option value='China'>China</option>
                <option value='USA'>USA</option>
              </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Destination Currency
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select destination currency' required name="destinationCurrency" required as="select" defaultValue=''>
                <option value=''>Select Destination Currency</option>
                <option value='EUR'>EUR</option>
                <option value='GBP'>GBP</option>
                <option value='INR'>INR</option>
                <option value='RMB'>RMB</option>
                <option value='USD'>USD</option>
              </Form.Control>
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
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Expiration Date
              </Form.Label>
              <Col sm="9">
                <Form.Control required name="expirationDate" type="date" placeholder="Expiration Date" />
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Counter Offer
              </Form.Label>
              <Col sm="9">
                <Form.Check style={{"float":"left"}} type="checkbox" name="isCounterOfferAllowed" label='Allow' defaultChecked={true}/>
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Split Offer
              </Form.Label>
              <Col sm="9">
                <Form.Check style={{"float":"left"}}type="checkbox" name="isSplitOfferAllowed" label='Allow' defaultChecked={true}/>
              </Col>
            </Form.Group>
            <Col>
              <Button variant="primary" type="submit">Submit</Button>
            </Col>
          </Form>
        </Col>
        <Col sm='2'></Col>
        
      </Row>
    </Container>
  );
}

export default PostNewOffer;