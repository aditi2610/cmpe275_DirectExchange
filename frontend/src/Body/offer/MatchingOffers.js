import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Row, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { rooturl } from '../../config/config';
import SplitOffers from './SplitOffers';
import Rating from './../Rating';

function MatchingOffers(props) {
  let [matchingOffers, setMatchingOffers] = React.useState({ exactMath: [], aEqualsBPlusC: [],cMinusBEqulsA: [], counterOffer: [], rangeMath: [] });
  let [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    Axios.get(`${rooturl}/offer/matchingOffer?id=${props.id}&userId=${localStorage.getItem('userId')}`).then(response => {
      if (response.status === 200) {
        setMatchingOffers(response.data);
        setLoading(false);
      }
    });
  }, [])

  let singleMatch = [...matchingOffers.exactMath, ...matchingOffers.rangeMath];
  return (
    <div>
      <h4>Single Offers</h4>
      {loading ? <Spinner animation='border'></Spinner> : !singleMatch.length ? <div>No matching single offers</div> : singleMatch.map(offer => {
        return <Card>
          <Card.Body>
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
            <br />
            <Row>
              <Col><Link to={`/matching-offer/${props.id}/${offer.id}/accept`}><Button variant='primary'>Accept</Button></Link>{' '}<Link to={`/create-counter-offer/${offer.id}/${props.id}`}><Button variant='primary'>Counter Offer</Button></Link>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      })}
      <br />
      {
        props.showSplit && <><h4>Split Matches</h4>
          {loading ? <Spinner animation='border'></Spinner> : !(matchingOffers.aEqualsBPlusC.length  || matchingOffers.cMinusBEqulsA.length) ? <div>No matching split offers</div> : <>{matchingOffers.aEqualsBPlusC.map(offers => {
            return <SplitOffers offers={offers} matchingOfferId={props.id} aEqualsBPlusC={true} />
          })}
          {matchingOffers.cMinusBEqulsA.map(offers => {
            return <SplitOffers offers={offers} matchingOfferId={props.id} aEqualsBPlusC={false} />
          })}
          </>}
          <br /></>
      }
      <h4>Counter Offers</h4>

      {loading ? <Spinner animation='border'></Spinner> : !(matchingOffers.counterOffer && matchingOffers.counterOffer.length) ? <div>No counter offers</div> : matchingOffers.counterOffer.map(offer => {
        return <Card>
          <Card.Body>
            <Row>
              <Col><b>User :</b> {offer.user && offer.user.nickName}</Col>
              <Col><b>Reputation :</b> {Math.round((Math.random() * 6 + Number.EPSILON) * 100) / 100}</Col>
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
            <br />
            <Row>
              <Col><Link to={`/accept-counter-offer/${offer.id}/${offer.user && offer.user.email}/accept`}><Button variant='primary'>Accept</Button></Link></Col>
            </Row>
          </Card.Body>
        </Card>
      })}
    </div>
  );
}

export default MatchingOffers;