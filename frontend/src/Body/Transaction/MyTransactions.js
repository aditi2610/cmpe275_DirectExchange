import React from 'react';
import Axios from 'axios';
import { offerStatus, rooturl } from '../../config/config';
import {Table, Container, Button} from 'react-bootstrap';

function MyTransactions(props) {
  let [transactions, setTransactions] = React.useState([]);
  let [reload, setReload] = React.useState(false);

  let handlePay = (transaction_id) => {
    Axios.post(`${rooturl}/transactions/apply/${transaction_id}`,{validateStatus: false}).then(response => {
      if(response.status === 200){
        setReload(!reload);
      }
    })
  }

  React.useEffect(() => {
    Axios.get(`${rooturl}/transactions/${localStorage.getItem('userId')}`,{validateStatus: false}).then(response => {
      if(response.status === 200){
        setTransactions(response.data);
      }
    })
  },[reload])
  let pendingTransactions = transactions.filter(t => t.status === 0);
  let completedTransactions = transactions.filter(t => t.status === 1);
  let expiredTransactions = transactions.filter(t => t.status === 2 || new Date().getTime() > new Date(t.expirationDate).getTime());
  return (
    <Container>
      <h4>Pending Transactions</h4>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Amount</th>
            <th>Expiration Date</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
            {!pendingTransactions.length ? <tr><td colSpan="3">{'No Pending Transactions'}</td></tr> : pendingTransactions.map(transaction => {
              return <tr>
                <td>{transaction.sourceCurrency} {transaction.sendingAmount}</td>
                <td>{new Intl.DateTimeFormat('en-US', {year: 'numeric', month: '2-digit',day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'}).format(new Date(transaction.expirationDate))}</td>
                <td><Button onClick={e => handlePay(transaction.id)}>Pay Now</Button></td>
                </tr>
            })}
        </tbody>
      </Table>
      <br/>
      <h4>Completed Transactions</h4>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Amount</th>
            <th>Expiration Date</th>
            <th>Offer Status</th>
          </tr>
        </thead>
        <tbody>
            {!completedTransactions.length ? <tr><td colSpan="3">{'No Pending Transactions'}</td></tr> : completedTransactions.map(transaction => {
              return <tr>
                <td>{transaction.sourceCurrency} {transaction.sendingAmount}</td>
                <td>{new Intl.DateTimeFormat('en-US', {year: 'numeric', month: '2-digit',day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'}).format(new Date(transaction.expirationDate))}</td>
                <td>{offerStatus[transaction.offer.status]}</td>
                </tr>
            })}
        </tbody>
      </Table>
      <h4>Expired Transactions</h4>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Amount</th>
            <th>Expiration Date</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
            {!expiredTransactions.length ? <tr><td colSpan="3">{'No Expired Transactions'}</td></tr> : expiredTransactions.map(transaction => {
              return <tr>
                <td>{transaction.sourceCurrency} {transaction.sendingAmount}</td>
                <td>{new Intl.DateTimeFormat('en-US', {year: 'numeric', month: '2-digit',day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'}).format(new Date(new Date(transaction.expirationDate)))}</td>
                <td>Expired</td>
                </tr>
            })}
        </tbody>
      </Table>
    </Container>
  );
}

export default MyTransactions;