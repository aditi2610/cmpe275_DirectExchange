import React, {Component} from 'react';
import { Button, Col, Container, Form, Row } from 'react-bootstrap';

class  PrevailingRates extends Component{
    //call the constructor method
    constructor(props){
        //Call the constructor of Super class i.e The Component
        super(props);
        //maintain the state required for this component
        this.state = {
          
           
        }
        //Bind the handlers to this class
       

    }
    componentDidMount=()=>
    {
       
    }
    render(){
        return(
<div><h4>prevailing rates table</h4></div>
        )
    }
}
export default PrevailingRates;