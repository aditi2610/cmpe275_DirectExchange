import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Container, Nav, Row, Spinner, Tab, ToggleButton } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { offerStatus, rooturl } from '../../config/config';
import MatchingOffers from './MatchingOffers';

function MyOffers(props) {
  let [offers,setOffers] = React.useState([]);
  let [loading,setLoading] = React.useState(true);
  let [reload,setReload] = React.useState(false);
  let [showSplit, setShowSplit] = React.useState(true);
  React.useEffect(() => {
    Axios.get(`${rooturl}/offer/userId/${localStorage.getItem('userId')}`,{validateStatus: false})
    .then(response => {
      if(response.status === 200){
        setOffers(response.data);
        setLoading(false);
      }
    })
  },[reload])

  let handleDelete = () => {
    Axios.get(`${rooturl}/offer/userId/1`,{validateStatus: false})
    .then(response => {
      if(response.status === 200){
        setLoading(true);
        setReload(!reload);
      }
    })
  }


  return loading ? <Spinner animation='border'></Spinner> : (
    <Container>
      <Container>
      <h3>My Offers</h3>
      <Row>
        <Col>
          <ToggleButton inline
            style={{'float': 'right'}}
            type="checkbox"
            variant="secondary"
            checked={showSplit}
            value="1"
            onChange={(e) => setShowSplit(e.currentTarget.checked)}
          >
            {'Show split offers'}
          </ToggleButton>
        </Col>
      </Row>
      </Container>
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row>
          <Col sm={3}>
            <Card>
              <Card.Body>
                <Nav variant="pills" className="flex-column">
                {offers.map(offer => {
                  return <Nav.Item>
                  <Nav.Link eventKey={offer.id}>Offer {offer.id}</Nav.Link>
                </Nav.Item>
                })}
              </Nav>
              </Card.Body>
            </Card>
            
          </Col>
          <Col sm={9}>
            <Card>
              <Card.Body>
                <Tab.Content>
                {offers.map(offer => {
                    return <Tab.Pane eventKey={offer.id}>
                      <h4>Offer Details</h4>
                      <Card>
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
                          {offer.status === 0 ? <Col><Link to={`/offer/${offer.id}/edit`}><Button variant='primary'>Edit</Button></Link>{' '}<Button variant='danger' onClick={handleDelete}>Delete</Button>
                          </Col> : <Col><b>Status : </b>{offerStatus[offer.status]}</Col>}
                        </Row>
                        </Card.Body>
                      </Card>
                      <br/>
                      {offer.status === 0 && <MatchingOffers showSplit={showSplit} id={offer.id}/>}
                    </Tab.Pane>
                  })}
                </Tab.Content>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Tab.Container>
    </Container>
  );
}

export default MyOffers;