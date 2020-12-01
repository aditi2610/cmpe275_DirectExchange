import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Row, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { rooturl } from '../../config/config';

function MatchingOffers(props) {
  let [matchingOffers, setMatchingOffers] = React.useState({exactMath: [],splitMatch: [],counterOffer:[]});
  let [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    Axios.get(`${rooturl}/offer/matchingOffer?id=${props.id}`).then(response => {
      if(response.status === 200){
        setMatchingOffers(response.data);
        setLoading(false);
      }
    });
  },[])

  return (
    <div>
      <h4>Single Offers</h4>
      {loading ? <Spinner animation='border'></Spinner> : matchingOffers.exactMath.map(offer => {
        return <Card>
        <Card.Body>
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
        <br/>
        <Row>
          <Col><Link to={`/offer/${offer.id}/edit`}><Button variant='primary'>Accept</Button></Link>{' '}<Button variant='danger'>Counter Offer</Button>
          </Col>
        </Row>
        </Card.Body>
      </Card>
      })}
      <br/>
      <h4>Counter Offers</h4>
      
      {loading ? <Spinner animation='border'></Spinner> : matchingOffers.counterOffer.map(offer => {
        return <Card>
        <Card.Body>
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
        <br/>
        <Row>
          <Col><Link to={`/offer/${offer.id}/edit`}><Button variant='primary'>Accept</Button></Link></Col>
        </Row>
        </Card.Body>
      </Card>
      })}
    </div>
  );
}

export default MatchingOffers;