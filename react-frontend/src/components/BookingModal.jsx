import React,{ useState } from 'react';
import { Modal, Button,Form } from "react-bootstrap";
import { Link } from "react-router-dom";

function BookingModal(props) {
    const [showModal, setShowModal] = useState(false);
    const [checkinTime, setCheckinTime] = useState("");
    const [checkoutTime, setCheckoutTime] = useState("");
    const isAdmin=JSON.parse(localStorage.getItem("user")).isAdmin;
    const handleShowModal = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };
    return (
        <>
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Select time</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="checkinTime">
                            <Form.Label>Check-in Time</Form.Label>
                            <Form.Control
                                type="datetime-local"
                                value={checkinTime}
                                onChange={(e) => setCheckinTime(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="checkoutTime">
                            <Form.Label>Check-out Time</Form.Label>
                            <Form.Control
                                required
                                type="datetime-local"
                                value={checkoutTime}
                                onChange={(e) => setCheckoutTime(e.target.value)}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Close
                    </Button>
                    <Link to={`/rooms/${props.workspaceid}/${checkinTime}/${checkoutTime}`} className="btn btn-primary">
                        View rooms
                    </Link>
                </Modal.Footer>
            </Modal>{!isAdmin && (
            <button onClick={handleShowModal} className="btn btn-primary book_now">Book Now</button>)}
            {isAdmin && (
                <button onClick={handleShowModal} className="btn btn-primary book_now">View Rooms</button>
            )}
        </>
    );
}

export default BookingModal;
