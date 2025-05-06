import React, { useState, useEffect } from 'react'
import Header from './Header'
import { Container, Row, Col } from 'react-bootstrap';
import { getAllBookings } from '../apis/bookingApi';
import BookingCard from './BookingCard';

function Bookings() {
    const [bookingData, setBookingData] = useState([]);
    const [errorState, setErrorState] = useState('');
    const token = JSON.parse(localStorage.getItem("user")).token;
    useEffect(() => {
        getAllBookings(token).then((response) => {
            const customerData=JSON.parse(localStorage.getItem("customer"));
            if(customerData===null){
                return;
            }
            const filteredBookings = response.filter((booking) => booking.customerId === customerData[0].customerId)
            setBookingData(filteredBookings)
        }).catch((error) => {
            const err=error?.response?.data?.responseMessage|| 'No records found';
            setErrorState(err);
        })
    },[])
    return (
        <div>
            <Header />
            {bookingData.length===0 && <h1 className='text-center'>{errorState}</h1>}
            <Container>
                <Row>
                    {bookingData.map((booking, index) => (
                        <Col key={index} md={6} className="mb-4">
                            <BookingCard booking={booking} />
                        </Col>
                    ))}
                </Row>
            </Container>
        </div>
    )
}

export default Bookings