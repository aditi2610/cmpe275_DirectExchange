import React, { Component } from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import axios from 'axios';
import { rooturl } from '../config/config';
import emailjs, { init } from 'emailjs-com';
class PrevailingRates extends Component {
  //call the constructor method
  constructor(props) {
    //Call the constructor of Super class i.e The Component
    super(props);
    //maintain the state required for this component
    this.state = {
      rates: []

    }
    //Bind the handlers to this class
    init("user_Ec6uKvIdbgBq1PetK5PwC");
    this.inputHandler = this.inputHandler.bind(this);
  }

  componentDidMount = () => {
    this.getExchangeRates();
  }

  getExchangeRates = async () => {

    let result = await axios.get(rooturl + '/exchangerate');
    let rates = result.data;
    console.log(rates);
    await this.setState({ rates });

  };
  inputHandler(event) {
    const target = event.target;
    const value = target.value;

    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  sendEmail(e) {
    e.preventDefault();
    console.log(" Send Emails: " + e.target);
    emailjs.sendForm("service_f8jmo8y", "template_tzy5aro", e.target, "user_Ec6uKvIdbgBq1PetK5PwC")
      .then((result) => {
        console.log(result.text);
      }, (error) => {
        console.log(error.text);
      });
  }
  render() {
    return (
      <div><h4>Exchange Rates Table</h4>
        <br></br>
        <h6>With respect to USD</h6>

        <Container>
          <table class="table table-bordered table-dark">
            <thead>
              <tr>

                <th scope="col">Country</th>
                <th scope="col">Currency Code</th>
                <th scope="col">Rate</th>
              </tr>
            </thead>
            <tbody>

              {this.state.rates.map(rate =>
                <tr key={rate.id}>

                  <td>{rate.country}</td>
                  <td>{rate.currencyCode}</td>
                  <td>{rate.rate}</td>
                </tr>
              )}
            </tbody>


          </table>

          <form className="contact-form" onSubmit={this.sendEmail}>
            <input type="hidden" name="contact_number" onChange={this.inputHandler} />
            <label>Name</label>
            <input type="text" name="user_name" onChange={this.inputHandler} />
            <label>Email</label>
            <input type="email" name="user_email" onChange={this.inputHandler} />
            <label>Message</label>
            <textarea name="message" />
            <input type="submit" value="Send" />
          </form>
        </Container>
      </div>
    )
  }
}
export default PrevailingRates;