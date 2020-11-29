import React, {Component} from 'react';
import { Button, Col, Container, Form, Row ,Card} from 'react-bootstrap';
import axios from 'axios';
import { rooturl } from '../config/config';
class  AddBankAccount extends Component{
    //call the constructor method
    constructor(props){
        //Call the constructor of Super class i.e The Component
        super(props);
        //maintain the state required for this component
        this.state = {
          country:'India',
          primaryCurrency:'INR',
          supportMethod:'Send',
          userId:1,
          bankaccounts:[]
           
        }
        //Bind the handlers to this class
        this.inputHandler = this.inputHandler.bind(this);
       

    }
    componentDidMount=()=>
    {
       this.getBankAccounts();
    }

    //Adding new bank account
    onAddBank = async (e) => {
        e.preventDefault();
        const params = {
          bankName:this.state.bankName,
          country:this.state.country,
          accountNumber:this.state.accountNumber,
          primaryCurrency:this.state.primaryCurrency,
          ownerName:this.state.ownerName,
          ownerAddress:this.state.ownerAddress,
          supportMethod:this.state.supportMethod,
          userId:this.state.userId
            //userId:localStorage.getItem("user_id")
            //userId is hardcoded for now need to cheange once login is done

        }
        console.log("data going to add bank account" + JSON.stringify(params));
        //set the with credentials to true
        axios.defaults.withCredentials = true;
        axios.post(rooturl+'/bankaccount',null,{params})
            .then(response => {
                if (response.status === 200) {
                    this.setState({
                        showBankList: true
                    }); 
                   // this.getBankAccounts();
                }
            })
            .catch(err => {
                this.setState({ errorMessage: "error" });
            })
    }
    getBankAccounts = async () => {
      //let user = localStorage.getItem("user_id");
      //let user = "5f8350d39bd6a608aae08c11";
      //const params = { user: user }
      let result = await axios.get(rooturl + '/bankaccount/'+this.state.userId)
      let bankaccounts = result.data;
      console.log(bankaccounts);
      await this.setState({ bankaccounts });

  };

    inputHandler(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
       
        const name = target.name;
    
        this.setState({
          [name]: value
        });
      }
    render(){
      let emptyList=null;
if(this.state.bankaccounts.length===0)
{
emptyList=(<h4>No Accounts to display</h4>);
}
    
        return(

             <Container>
      <Row>
        <Col sm='2'></Col>
        <Col>
          <Form onSubmit={this.onAddBank}>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Bank Name
              </Form.Label>
              <Col sm="9">
                <Form.Control name="bankName" type="text" placeholder="Bank Name" onChange={this.inputHandler} />
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Country
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select Bank country' name="country" required as="select" onChange={this.inputHandler}>
                <option value='India'>India</option>
                <option value='Britan'>Britan</option>
                <option value='India'>Germany</option>
                <option value='China'>China</option>
                <option value='USA'>USA</option>
              </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Account Number
              </Form.Label>
              <Col sm="9">
                <Form.Control name="accountNumber" type="number" step="0.1"  placeholder="Account Number" onChange={this.inputHandler} />
              </Col>
            </Form.Group>
            
            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Primary Currency
              </Form.Label>
              <Col sm="9">
              <Form.Control label='select destination currency' name="primaryCurrency" required as="select" onChange={this.inputHandler}>
                <option value='INR'>INR</option>
                <option value='GBP'>GBP</option>
                <option value='EUR'>EUR</option>
                <option value='RMB'>RMB</option>
                <option value='USD'>USD</option>
              </Form.Control>
              </Col>
            </Form.Group>

            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Owner Name
              </Form.Label>
              <Col sm="9">
                <Form.Control name="ownerName" type="text"  placeholder="Owner Name" onChange={this.inputHandler} />
              </Col>
            </Form.Group>

            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Owner Address
              </Form.Label>
              <Col sm="9">
                <Form.Control name="ownerAddress" type="text"  placeholder="Owner Address" onChange={this.inputHandler} />
              </Col>
            </Form.Group>

            <Form.Group as={Row}>
              <Form.Label column sm="3">
                Support Method
              </Form.Label>
              <Col sm="9">
              <Form.Control label='support method' name="supportMethod" required as="select" onChange={this.inputHandler}>
                <option value='Send'>Send</option>
                <option value='Receive'>Receive</option>
                <option value='Both'>Both</option>
              </Form.Control>
              </Col>
            </Form.Group>
          
            <Col>
              <Button variant="primary" type="submit">Submit</Button>
            </Col>
          </Form>
        </Col>
        <Col sm='2'></Col>
        
      </Row>
<Row>
  <h4>Added accounts:{" "} </h4>
  {emptyList}
  </Row>
  
      {this.state.bankaccounts.map(bankaccount =>
            <div key={bankaccount.bankId}>
      <Row>
      <Card style={{ width: '30rem' }}>
      <Card.Body>
      <Card.Title>Bank Name:{bankaccount.bankName}</Card.Title>
      <Card.Text>Account Number: {bankaccount.accountNumber} </Card.Text>
      <Card.Text>Owner Name: {bankaccount.ownerName} </Card.Text>
      <Card.Text>Owner Address: {bankaccount.ownerAddress} </Card.Text>
      <Card.Text>Primary Currency: {bankaccount.primaryCurrency} </Card.Text>
      <Card.Text>Support: {bankaccount.supportMethod} </Card.Text>
  </Card.Body>
</Card>
        </Row>    
        </div>   
                            )
                            }
                          
    </Container>
        )
    }
}

export default AddBankAccount;