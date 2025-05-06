import React,{useState,useEffect} from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';

function Header() {
    const [showBooking,setShowBooking]=useState(true);
    const navigate=useNavigate();
    const signOutHandler = () => {
        localStorage.removeItem('user');
        navigate('/signin');
    };
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem("user"))
        if (!user) {
            navigate('/signin');
        }
        if(user.isAdmin){
            setShowBooking(false)
        }
        if(window.location.href.includes('bookings')){
            setShowBooking(false)
        }
    })
    return (
        <Navbar bg="dark" data-bs-theme="dark">
            <Container className="d-flex justify-content-between">
            <Navbar.Brand href="/" className="ml-auto">
              <span className="text-bold">Work-From-Here</span>
            </Navbar.Brand>
            <div>

              { showBooking  && <Button id="bookingsButton" className="btn btn-success mx-3" onClick={()=>{
                navigate('/bookings')
              }} >
                Bookings
              </Button>
              }
              <Button id="logoutButton" className="btn btn-danger ml-2" onClick={signOutHandler}>
                Sign out
              </Button>
            </div>
          </Container>
        </Navbar>
    );
}

export default Header;
