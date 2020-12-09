import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Container, Row, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { rooturl } from '../../config/config';

function BrowseOffers(props) {
  let [offers, setOffers] = React.useState([]);
  let [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    Axios.get(`${rooturl}/offer/${localStorage.getItem('userId')}`).then(response => {
      if (response.status === 200) {
        console.log(response.data);
        setLoading(false);
        setOffers(response.data);
      }
    })
  }, []);

  return (
    loading ? <Spinner animation='border'></Spinner> :
      <Container>
        {offers.map(offer => {
          return <><Card>
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
                <Col><Link to={`/browse-offers/${offer.id}/${offer.user && offer.user.email}/accept`}><Button variant='primary'>Accept</Button></Link>{' '}{offer.isCounterOfferAllowed && <Link to={`/create-counter-offer/${offer.id}`}><Button variant='primary'>Counter Offer</Button></Link>}
                </Col>
              </Row>
            </Card.Body>
          </Card><br /></>
        })}
      </Container>
  );
}

export default BrowseOffers;