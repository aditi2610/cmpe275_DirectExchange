import React from 'react';
import { Card, Col, Container, ListGroup, Nav, Row, Tab } from 'react-bootstrap';

function MyOffers(props) {
  let [offers,setOffers] = React.useState([]);
  React.useEffect(() => {
    setOffers([{id: 1},{id: 2},{id: 3}])
  },[])

  return (
    <Container>
      My Offers
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
                    return <Tab.Pane eventKey={offer.id}>Matching offers for {offer.id}</Tab.Pane>
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