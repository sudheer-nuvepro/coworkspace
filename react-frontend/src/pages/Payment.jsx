import React, { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom';
import Header from './Header';
import { Container, Card, Alert } from 'react-bootstrap';
import { checkPayment } from '../apis/paymentApis';
import { useNavigate } from 'react-router-dom';
import { clear } from '@testing-library/user-event/dist/clear';

function Payment() {
    const navigate=useNavigate();
    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const [searchParams, setSearchParams] = useSearchParams();
    const [countdown, setCountdown] = useState(5);

    useEffect(() => {
        const paymentIntentId = searchParams.get("payment_intent")
        const bookingId = searchParams.get("bookingId")
        const token = JSON.parse(localStorage.getItem("user")).token;
        
        checkPayment(paymentIntentId, bookingId, token).then((response) => {
            setPaymentSuccess(true)
          
              // Redirect to the home page after 5 seconds
              const redirectTimeout = setTimeout(() => {
                navigate('/');
              }, 5000);
          
              return () => {
                clearTimeout(redirectTimeout)
              };
        }).catch((error) => {
            setPaymentSuccess(false)
        })
    }, [navigate])
    return (
        <div>
            <Header />
            {paymentSuccess && <Container className="d-flex align-items-center justify-content-center vh-100">
                <Card style={{ width: '400px' }}>
                    <Card.Body>
                        <Alert variant="success">
                            <Alert.Heading>Payment Successful!</Alert.Heading>
                            <p>
                                Thank you for your payment. Your transaction was successful.
                            </p> 
                            <p>
                                Redirecting to the home page in {countdown} seconds...
                            </p>
                        </Alert>
                    </Card.Body>
                </Card>
            </Container>}
            {!paymentSuccess && <Container className="d-flex align-items-center justify-content-center vh-100">
                <h2>Payment not successful</h2>
                </Container>}
        </div>
    )
}

export default Payment