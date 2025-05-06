import React,{useEffect, useState} from 'react'
import { Card } from 'react-bootstrap';
import { getCoworkspaceById } from '../apis/coworkspaceApis';

function BookingCard({ booking }) {
    const [coworkspaceName, setCoworkspaceName] = useState('');
    const { bookingId, checkInDate, checkOutDate, workspaceId } = booking;
    
    const token = JSON.parse(localStorage.getItem("user")).token;
    useEffect(() => {
        getCoworkspaceById(workspaceId, token).then((response) => {
            setCoworkspaceName(response.coworkingSpaceName)
        })
        
    })
    return (
        <Card className='my-3'>
            <Card.Body>
                <Card.Title>Booking Summary</Card.Title>
                <hr />
                <Card.Text>
                    <strong><h6>Workspace Name: {coworkspaceName} </h6></strong>
                    <strong>Booking ID:</strong> {bookingId}
                    <br />
                    <strong>Check-In Date:</strong> {checkInDate}
                    <br />
                    <strong>Check-Out Date:</strong> {checkOutDate}
                    <br />
                    
                </Card.Text>
            </Card.Body>
        </Card>
    )
}

export default BookingCard