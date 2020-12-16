import Axios from 'axios';
import React from 'react';
import { Button, Card, Col, Container, Form, Row, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { rooturl } from '../../config/config';
import Rating from '../Rating';
import Pagination from "react-js-pagination";

function BrowseOffers(props) {
  let [offers, setOffers] = React.useState([]);
  let [loading, setLoading] = React.useState(true);
  let [currentPage,setCurrentPage] = React.useState(1);
  const handleOnChange = (e) => {
    const { form } = e.currentTarget;
    const queryData = {
      page: 1,
    };
    form.sourceCurrency.value && (queryData['sourceCurrency'] = form.sourceCurrency.value);
    form.amount.value && (queryData['amount'] = form.amount.value);
    form.destinationCurrency.value && (queryData['destinationCurrency'] = form.destinationCurrency.value);
    form.destinationAmount.value && (queryData['destinationAmount'] = form.destinationAmount.value);
    browseOfferCall(queryData);
  };
  const resetForm = (e) => {
    e.currentTarget.form.reset();
    handleOnChange(e);
  };
  React.useEffect(() => {
    browseOfferCall({page: 1});
  }, []);

  let browseOfferCall = (queryData) => {
    setLoading(true);
    Axios.get(`${rooturl}/offer/${localStorage.getItem('userId')}`,{params: queryData }).then(response => {
      if (response.status === 200) {
        console.log(response.data);
        setLoading(false);
        setOffers(response.data);
      }
    })
  }
  let handlePageChange = (pageNumber) => {
    browseOfferCall({page: pageNumber});
  }

  return (
      <Container>
        <Card>
          <Form id='search-student-form'>
            <Card.Body>
              <Card.Title>
                Filters
                <Button style={{ float: 'right' }} onClick={resetForm} variant="secondary">Reset</Button>
              </Card.Title>
              <Row>
                <Col><b>Source Currency :</b> <Form.Control onChange={handleOnChange} label='select source currency' name="sourceCurrency" required as="select" defaultValue='' style={{width:'auto',display:'inline'}}>
                  <option value=''>Select Source Currency</option>
                  <option value='EUR'>EUR</option>
                  <option value='GBP'>GBP</option>
                  <option value='INR'>INR</option>
                  <option value='RMB'>RMB</option>
                  <option value='USD'>USD</option>
                </Form.Control></Col>
                <Col><b>Source Amount :</b> <Form.Control onChange={handleOnChange} name="amount" required type="number" step="0.1"  placeholder="Amount" style={{width:'auto',display:'inline'}}/></Col>
              </Row>
              <br/>
              <Row>
                <Col><b>Destination Currency :</b> <Form.Control onChange={handleOnChange} label='select source currency' name="destinationCurrency" required as="select" defaultValue='' style={{width:'auto',display:'inline'}}>
                  <option value=''>Select Destination Currency</option>
                  <option value='EUR'>EUR</option>
                  <option value='GBP'>GBP</option>
                  <option value='INR'>INR</option>
                  <option value='RMB'>RMB</option>
                  <option value='USD'>USD</option>
                </Form.Control></Col>
                <Col><b>Destination Amount :</b> <Form.Control onChange={handleOnChange} name="destinationAmount" required type="number" step="0.1"  placeholder="Amount" style={{width:'auto',display:'inline'}}/></Col>
              </Row>
            </Card.Body>
          </Form>
        </Card>
        <br/>
        {loading ? <Spinner animation='border'></Spinner> :
        offers.map(offer => {
          return <><Card>
            <Card.Body>
              <Row>
                <Col><b>User :</b> {offer.user && offer.user.nickName}</Col>
                <Col><b>Reputation :</b>
                <Link to={`/reputationTransaction/${offer.user && offer.user.id}`}>
                <Rating rating={offer.user.rating} /> 
                </Link>
                </Col>
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
        <Row>
          <Col>
            <Pagination
              activePage={currentPage}
              itemsCountPerPage={10}
              totalItemsCount={30}
              itemClass="page-item"
              linkClass="page-link"
              onChange={(e) => handlePageChange(e)}
            />
          </Col>
        </Row>
        
      </Container>
      
  );
}

export default BrowseOffers;