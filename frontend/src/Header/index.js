import React from 'react';
import {Navbar,Nav,NavDropdown,Dropdown, Form,FormControl,Button,Container,Row,Col} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import logo from '../Logo.jpg'
import './Header.css';
import {FaUser, FaSignInAlt, FaSignOutAlt} from 'react-icons/fa';

function Header(props) {
  return (
  <Container className='menu-container'>
    <Navbar>
      <Navbar.Brand href='/home'><img className = 'logo' src = {logo} alt ="Home Finder" ></img></Navbar.Brand>
      <Nav className="ml-auto">
        <Nav.Item>
          <Nav.Link><Link to='/post-offer'>Post an Offer</Link></Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link><Link to='/browse-offers'>Browse Offers</Link></Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link><Link to='/my-offers'>My Offers</Link></Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link><Link to='/addBank'>Add Bank Account</Link></Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link><Link to='/prevailing-rates'>Prevailing Rates</Link></Nav.Link>
        </Nav.Item>
      </Nav>
      {localStorage.getItem('email') ? (
          <NavDropdown title={<FaUser/>} id="basic-nav-dropdown">
            <NavDropdown.Divider />
            <NavDropdown.Item href='/sign-out'>Sign out &nbsp;<FaSignOutAlt/></NavDropdown.Item>
          </NavDropdown>
      ) : (
        <Nav.Item>
          <Nav.Link><Link to='/sign-in'>Sign in &nbsp;</Link></Nav.Link>
        </Nav.Item>
      )}
    </Navbar>
  </Container>  
  );
}

export default Header;