import Axios from 'axios';
import React from 'react';
import { Alert, Container, Spinner } from 'react-bootstrap';
import { rooturl } from '../../config/config';

function AcceptSplitOffer(props) {
  let matchingOfferId = props.match.params.matchingOfferId;
  let splitOffer1 = props.match.params.splitOffer1;
  let splitOffer2 = props.match.params.splitOffer2;
  let aEqualsBPlusC = props.match.params.aEqualsBPlusC;
  let [loading,setLoading] = React.useState(true);
  let [respMessage, setRespMessage] = React.useState(true);

  React.useEffect(()=>{
    let formData = [matchingOfferId ,splitOffer1 ,splitOffer2];
    let url = `${rooturl}/offer/acceptSplitOfferFromMyOffer/${aEqualsBPlusC}`;
    Axios.post(`${url}`,formData,{validateStatus:false}).then(response => {
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

export default AcceptSplitOffer;