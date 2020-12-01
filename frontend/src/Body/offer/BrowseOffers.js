import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Container, Row, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { rooturl } from '../../config/config';

function BrowseOffers(props) {
  let [offers,setOffers] = React.useState([]);
  let [loading,setLoading] = React.useState(true);

  React.useEffect(() =>{
    Axios.get(`${rooturl}/offer`).then(response =>{
      if(response.status === 200){
        setLoading(false);
        setOffers(response.data);
      }
    })
  },[]);

  return (
    loading ? <Spinner animation='border'></Spinner> : 
    <Container>
      {offers.map(offer => {
        return <><Card>
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
              <Col><Link to={`/offer/${offer.id}/edit`}><Button variant='primary'>Accept</Button></Link>{' '}<Button variant='primary'>Counter Offer</Button>
              </Col>
            </Row>
          </Card.Body>
        </Card><br/></>
      })}
    </Container>
  );
}

export default BrowseOffers;