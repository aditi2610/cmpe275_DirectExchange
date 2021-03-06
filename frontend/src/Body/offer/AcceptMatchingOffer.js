import Axios from 'axios';
import React from 'react';
import { Alert, Container, Spinner } from 'react-bootstrap';
import { rooturl } from '../../config/config';

function AcceptMatchingOffer(props) {
  let myOfferId = props.match.params.myOfferId;
  let acceptedOfferId = props.match.params.acceptedOfferId;
  let [loading,setLoading] = React.useState(true);
  let [respMessage, setRespMessage] = React.useState(true);

  React.useEffect(()=>{
    let formData = {
      id: parseInt(localStorage.getItem('userId')),
    };
    Axios.post(`${rooturl}/offer/acceptOfferFromMyOffer?myOfferId=${myOfferId}&acceptedOfferId=${acceptedOfferId}`,formData,{validateStatus:false}).then(response => {
      if(response.status === 200 || response.status === 201){
      setRespMessage(<Alert variant='success'>Offer accepted successfully!</Alert>);
      setLoading(false);
      }else{
        setRespMessage(<Alert variant='danger'>{response.data['Bad Request'] && response.data['Bad Request']['Error Message'] ? response.data['Bad Request']['Error Message'] : 'Something went wrong, Please try again!'}</Alert>);
        setLoading(false);
      }
    })
  },[]);

  return (
    loading ? <Spinner border='animation'></Spinner> : <Container>{respMessage}</Container>
  );
}

export default AcceptMatchingOffer;