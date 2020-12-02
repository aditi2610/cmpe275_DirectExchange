import React, {Component} from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';
import axios from 'axios';
import { rooturl } from '../config/config';

class  PrevailingRates extends Component{
    //call the constructor method
    constructor(props){
        //Call the constructor of Super class i.e The Component
        super(props);
        //maintain the state required for this component
        this.state = {
          rates:[]
           
        }
        //Bind the handlers to this class
       

    }
    componentDidMount=()=>
    {
       this.getExchangeRates();
    }

    getExchangeRates = async () => {
       
        let result = await axios.get(rooturl + '/exchangerate');
        let rates = result.data;
        console.log(rates);
        await this.setState({ rates});
  
    };
    render(){
        return(
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
</Container>
</div>
        )
    }
}
export default PrevailingRates;