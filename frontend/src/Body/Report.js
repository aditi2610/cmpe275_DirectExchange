import React from 'react';
import Axios from 'axios';
import { offerStatus, rooturl } from '../config/config';
import { Table, Container, Button } from 'react-bootstrap';

import { Link } from 'react-router-dom';
function Report(props) {
  let [transactions, setTransactions] = React.useState([]);
  let [total, setTotal] = React.useState([]);
  let [adminTotal, setAdminTotal] = React.useState([]);
  let [reload, setReload] = React.useState(false);
  let userId = props.match.params.userId;


  React.useEffect(() => {
    Axios.get(`${rooturl}/transactions/history/${localStorage.getItem('userId')}`, { validateStatus: false }).then(response => {
      if (response.status === 200) {
        setTransactions(response.data);
        console.log(response.data);
      }
    })
    Axios.get(`${rooturl}/transactions/historyStats/${localStorage.getItem('userId')}`, { validateStatus: false }).then(response => {
        if (response.status === 200) {
          setTotal(response.data);
          console.log("Totalllll",response.data);
        }
      })
      Axios.get(`${rooturl}/transactions/report`, { validateStatus: false }).then(response => {
        if (response.status === 200) {
          setAdminTotal(response.data);
          console.log("AdminTotalllll",response.data);
        }
      })
  }, [reload])

  
//    let pendingTransactions = transactions.filter(t => t.status === 0 && new Date(t.expirationDate).getTime() > new Date().getTime());
   let completedTransactions = transactions;
  // let expiredTransactions = transactions.filter(t => t.status !== 1 && new Date().getTime() > new Date(t.expirationDate).getTime());
  return (
    <Container>
    
      <h4>Transaction History Report: User</h4>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Sending Amount</th>
            <th>Source Currency</th>
            <th>Receiving Amount</th>
            <th>Destination Currency</th>
            <th>Service Fee</th>
            <th>Exchange Rate</th>
            <th>Creation Date</th>
          </tr>
        </thead>
        <tbody>
          {!completedTransactions.length ? <tr><td colSpan="3">{'No Transactions'}</td></tr> : completedTransactions.map(transaction => {
            return <tr>
              <td>{transaction.sendingAmount}</td>
              <td>{transaction.sourceCurrency}</td>
              <td>{transaction.recevingAmount}</td>
              <td>{transaction.destinationCurrency}</td>
              <td>{transaction.servieFee}</td>
              <td>{transaction.exchangeRate}</td>
              <td>{new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' }).format(new Date(transaction.creationDate))}</td>
              
            </tr>
          })}
          <tr style={{color:"royalblue"}}>
        <td>{total && total[0] && total[0].totalSendingAmount}</td>
        <td>USD</td>
        <td>{total && total[0] && total[0].totalReceivingAmount}</td>
        <td>USD</td>
        <td>{total && total[0] && total[0].totalServiceFee}</td>
        <td>N/A</td>
        <td>N/A</td>
          </tr>

        </tbody>
      </Table>

      <h4>Transaction History Report: Admin</h4>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Total Remitted Amount</th>
            <th>Currency</th>
            <th>Total Service Fee</th>
            <th>Total Pending Transaction </th>
           
          </tr>
        </thead>
        <tbody>
          {/* {!completedTransactions.length ? <tr><td colSpan="3">{'No Transactions'}</td></tr> : completedTransactions.map(transaction => {
            return <tr>
              <td>{transaction.sendingAmount}</td>
              <td>{transaction.sourceCurrency}</td>
              <td>{transaction.recevingAmount}</td>
              <td>{transaction.destinationCurrency}</td>
              <td>{transaction.servieFee}</td>
              <td>{transaction.exchangeRate}</td>
              <td>{new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' }).format(new Date(transaction.creationDate))}</td>
              
            </tr>
          })} */}
          <tr style={{color:"royalblue"}}>
        <td>{adminTotal && adminTotal[0] && adminTotal[0].convertedUSD}</td>
        <td>USD</td>
        <td>{adminTotal && adminTotal[0] && adminTotal[0].totalFee}</td>
        <td>{adminTotal && adminTotal[0] && adminTotal[0].totalOffers}</td>
       
          </tr>

        </tbody>
        <thead>
          <tr>
            <th>Total Remitted Amount</th>
            <th>Currency</th>
            <th>Total Service Fee</th>
            <th>Total Completed Transaction </th>
           
          </tr>
        </thead>
        <tbody>
          {/* {!completedTransactions.length ? <tr><td colSpan="3">{'No Transactions'}</td></tr> : completedTransactions.map(transaction => {
            return <tr>
              <td>{transaction.sendingAmount}</td>
              <td>{transaction.sourceCurrency}</td>
              <td>{transaction.recevingAmount}</td>
              <td>{transaction.destinationCurrency}</td>
              <td>{transaction.servieFee}</td>
              <td>{transaction.exchangeRate}</td>
              <td>{new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' }).format(new Date(transaction.creationDate))}</td>
              
            </tr>
          })} */}
          <tr style={{color:"royalblue"}}>
        <td>{adminTotal && adminTotal[1] && adminTotal[1].convertedUSD}</td>
        <td>USD</td>
        <td>{adminTotal && adminTotal[1] && adminTotal[1].totalFee}</td>
        <td>{adminTotal && adminTotal[1] && adminTotal[1].totalOffers}</td>
       
          </tr>

        </tbody>
      </Table>


      
     
    </Container>
  );
}

export default Report;