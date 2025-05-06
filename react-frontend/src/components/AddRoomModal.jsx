import { useState } from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

const AddRoomModal = ({handleAddRoom,workspaceId}) => {
    const [showModal, setShowModal] = useState(false);
    const [roomNumber, setRoomNumber] = useState('');
    const [available, setAvailable] = useState(true);
    const [roomType, setRoomType] = useState('');
    const [floor, setFloor] = useState('');
    const [roomSeatingCapacity, setRoomSeatingCapacity] = useState('');
    const [roomRentPerHour, setRoomRentPerHour] = useState('');
    const [roomRentByDay, setRoomRentByDay] = useState('')

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        switch (name) {
            case 'roomNumber':
                setRoomNumber(value);
                break;
            case 'available':
                setAvailable(value === 'true');
                break;
            case 'roomType':
                setRoomType(value);
                break;
            case 'floor':
                setFloor(value);
                break;
            case 'roomSeatingCapacity':
                setRoomSeatingCapacity(value);
                break;
            case 'roomRentPerHour':
                setRoomRentPerHour(value);
                break;
            case 'roomRentByDay':
                setRoomRentByDay(value);
                break;
            default:
                break;
        }
    };

    const handleFormSubmit = (event) => {
        event.preventDefault();
        const newRoom = {
            roomNumber,
            available,
            roomType,
            floor,
            roomSeatingCapacity,
            roomRentPerHour,
            roomRentByDay,
        };
        console.log(newRoom);
        handleAddRoom(newRoom,workspaceId);
        setRoomNumber('');
        setAvailable(true);
        setRoomType('');
        setFloor('');
        setRoomSeatingCapacity('');
        setRoomRentPerHour('');
        setRoomRentByDay('');
        setShowModal(false);
    };

    return (
        <>
            <Button variant="primary" onClick={() => setShowModal(true)}>
                Add Room
            </Button>
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Room</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleFormSubmit}>
                        <Form.Group controlId="roomNumber">
                            <Form.Label>Room Number:</Form.Label>
                            <Form.Control
                                type="text"
                                name="roomNumber"
                                value={roomNumber}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="available">
                            <Form.Label>Available:</Form.Label>
                            <Form.Control
                                as="select"
                                name="available"
                                value={available}
                                onChange={handleInputChange}
                                required
                            >
                                <option value={true}>Yes</option>
                                <option value={false}>No</option>
                            </Form.Control>
                        </Form.Group>
                        <Form.Group controlId="roomType">
                            <Form.Label>Room Type:</Form.Label>
                            <Form.Control
                                type="text"
                                name="roomType"
                                value={roomType}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="floor">
                            <Form.Label>Floor:</Form.Label>
                            <Form.Control
                                type="text"
                                name="floor"
                                value={floor}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="roomSeatingCapacity">
                            <Form.Label>Room Seating Capacity:</Form.Label>
                            <Form.Control
                                type="text"
                                name="roomSeatingCapacity"
                                value={roomSeatingCapacity}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="roomRentPerHour">
                            <Form.Label>Room Rent Per Hour:</Form.Label>
                            <Form.Control
                                type="text"
                                name="roomRentPerHour"
                                value={roomRentPerHour}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="roomRentByDay">
                            <Form.Label>Room Rent By Day:</Form.Label>
                            <Form.Control
                                type="text"
                                name="roomRentByDay"
                                value={roomRentByDay}
                                onChange={handleInputChange}
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Add Room
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
};

export default AddRoomModal;
