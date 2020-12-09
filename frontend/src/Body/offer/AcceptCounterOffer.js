import Axios from 'axios';
import React from 'react';
import { Alert, Container, Spinner } from 'react-bootstrap';
import { rooturl } from '../../config/config';
import emailjs, { init } from 'emailjs-com';
//template_w6vin4u
function AcceptCounterOffer(props) {
  let offerId = props.match.params.offerId;
  let emailId = props.match.params.email;
  let [loading, setLoading] = React.useState(true);
  let [respMessage, setRespMessage] = React.useState(true);

  React.useEffect(() => {
    let formData = {
      id: parseInt(localStorage.getItem('userId')),
    };
    Axios.post(`${rooturl}/offer/acceptCounterOfferFromBrosePage?offerId=${offerId}`, formData, { validateStatus: false }).then(response => {
      if (response.status === 200 || response.status === 201) {
        setRespMessage(<Alert variant='success'>Offer accepted successfully!</Alert>);
        setLoading(false);
        //send Offer Acceptance Emails
        let templateParams = {
          from_name: "Direct Exchange Team",
          to_name: localStorage.getItem("email"),
          message: "Congratulations!! The Counter offer has been accepted successfully"
        }
        emailjs.send("service_f8jmo8y", "template_w6vin4u", templateParams, "user_Ec6uKvIdbgBq1PetK5PwC")
          .then((result) => {
            console.log(result.text);
          }, (error) => {
            console.log(error.text);
          });

        let templateParams1 = {
          from_name: "Direct Exchange Team",
          to_name: emailId,
          message: "Congratulations!! The counter offer has been accepted successfully"
        }
        emailjs.send("service_f8jmo8y", "template_w6vin4u", templateParams1, "user_Ec6uKvIdbgBq1PetK5PwC")
          .then((result) => {
            console.log(result.text);
          }, (error) => {
            console.log(error.text);
          });



      } else {
        setRespMessage(<Alert variant='danger'>{response.data['Bad Request'] && response.data['Bad Request']['Error Message'] ? response.data['Bad Request']['Error Message'] : 'Something went wrong, Please try again!'}</Alert>);
        setLoading(false);
      }
    })
  }, []);

  return (
    loading ? <Spinner border='animation'></Spinner> : <Container>{respMessage}</Container>
  );
}

export default AcceptCounterOffer;