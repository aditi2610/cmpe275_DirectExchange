import React from 'react';
import { Button, Card, Col, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Rating from '../Rating';

function SplitOffers({offers,matchingOfferId, aEqualsBPlusC}) {
  return <Card>
    {offers.map(offer => {
      return <Card.Body>
        <Row>
          <Col><b>User :</b> {offer.user && offer.user.nickName}</Col>
          <Col><b>Reputation :</b> <Rating rating={offer.user.rating} /></Col>
          <Col></Col>
        </Row>
        <Row>
          <Col><b>Amount :</b> {offer.amount}</Col>
          <Col><b>Source Country :</b> {offer.sourceCountry}</Col>
          <Col><b>Source Currency :</b> {offer.sourceCurrency}</Col>
        </Row>
        <Row>
          <Col><b>Destination Country :</b> {offer.destinationCountry}</Col>
          <Col><b>Destination Currency :</b> {offer.destinationCurrency}</Col>
          <Col><b>Exchange Rate :</b> {offer.exchangeRate}</Col>
        </Row>
      </Card.Body>
    })}
    <Card.Body>
    <Row>
      <Col><Link to={`/accept-split-offer/${matchingOfferId}/${offers[0].id}/${offers[1].id}/${aEqualsBPlusC}`}><Button variant='primary'>Accept</Button></Link>{' '}<Link to={`/create-counter-offer/${(aEqualsBPlusC ? (offers[0].amount > offers[1].amount) : (offers[0].amount > offers[1].destinationAmount)) ? offers[0].id : offers[1].id}/${matchingOfferId}/${(aEqualsBPlusC ? (offers[0].amount > offers[1].amount) : (offers[0].amount > offers[1].destinationAmount)) ? offers[1].user.id : offers[0].user.id}`}><Button variant='primary'>Counter Offer</Button></Link>
      </Col>
    </Row>
    </Card.Body>
  </Card>
}

export default SplitOffers;