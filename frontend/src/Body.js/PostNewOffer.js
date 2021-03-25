import React from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';

function PostNewOffer(props) {
  return (
    <Container>
      <Row>
        <Col sm='2'></Col>
        <Col>
          <Form>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Source Country
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select source country' name="SourceCountry" required as="select">
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
                Source Currency
              </Form.Label>
              <Col sm="9">
                <Form.Control label='select source currency' name="SourceCurrency" required as="select">
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
                <Form.Control name="Amount" type="number" step="0.1"  placeholder="Amount" />
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Destination Country
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select Destination country' name="DestinationCountry" required as="select">
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
              <Form.Control label='select destination currency' name="DestinationCurrency" required as="select">
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
                <Form.Control name="ExchangeRate" type="number" step="0.01" placeholder="Exchange Rate" />
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                ExpirationDate
              </Form.Label>
              <Col sm="9">
                <Form.Control name="ExpirationDate" type="date" placeholder="Expiration Date" />
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Counter Offer
              </Form.Label>
              <Col sm="9">
                <Form.Check style={{"float":"left"}} type="checkbox" name="isCounterOfferAllowed" label='Allow'/>
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Split Offer
              </Form.Label>
              <Col sm="9">
                <Form.Check style={{"float":"left"}}type="checkbox" name="isSplitOfferAllowed" label='Allow'/>
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