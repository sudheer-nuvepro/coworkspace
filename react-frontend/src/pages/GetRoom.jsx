import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';
import Header from './Header';
import AddRoomModal from '../components/AddRoomModal';
import { useNavigate } from 'react-router-dom';
import { addRoomInCoworkspace, getAvailableRooms } from '../apis/roomApis';
import { getCoworkspaceById } from '../apis/coworkspaceApis';
import { bookRoom } from '../apis/bookingApi';
import axios from 'axios';

function GetRoom() {
    const navigate = useNavigate();
    const { id, checkInTime, checkOutTime } = useParams()
    const [rooms, setRooms] = useState([])
    const [workspace, setWorkspace] = useState({})
    const token = JSON.parse(localStorage.getItem("user")).token;
    const isAdmin = JSON.parse(localStorage.getItem("user")).isAdmin;
    useEffect(() => {
        getAvailableRooms(id, checkInTime, checkOutTime, token).then((response) => {
            console.log(response)
            setRooms(response)
        }).catch((error) => {
            if (error.response?.status === 404 || error.response?.status === 403) {
                navigate('/signin')
            }
        })
        getCoworkspaceById(id, token).then((response) => {
            setWorkspace(response)
        }).catch((error) => {
            if (error.response?.status === 404 || error.response?.status === 403) {
                navigate('/signin')
            }
        })
    }, [])

    const handleAddRoom = async (newRoom, workspaceId) => {
        addRoomInCoworkspace(workspaceId, newRoom, token).then((response) => {
            getAvailableRooms(id, checkInTime, checkOutTime, token).then((response) => {

                setRooms(response)
            }).catch((error) => {
                if (error.response?.status === 404 || error.response?.status === 403) {
                    navigate('/signin')
                }
            })
        })
    }

    const handleBookRoom = async (roomId) => {
        const customer = JSON.parse(localStorage.getItem("customer"));
        bookRoom(customer[0].customerId, roomId, checkInTime, checkOutTime, token).then((response) => {
            console.log(response.request.responseURL)
            const axiosInstance = axios.create({
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            
            axiosInstance.get(response.request.responseURL)
                .then(response => {
                    // Handle the response if needed
                    if (response.status === 200) {
                        console.log(response)
                        // Redirect to the desired URL when the response is successful
                        window.location.href = response.request.responseURL;
                    } else {
                        // Handle errors or response data as necessary
                    }
                })
                .catch(error => {
                    // Handle any errors that may occur during the request
                });


        }).catch((error) => {
            if (error.response?.status === 404 || error.response?.status === 403) {
                navigate('/signin')
            }
        })
    }
    return (
        <>
            <Header />
            <Container>
                <h3 className='my-3'>Available rooms in {workspace.coworkingSpaceName}</h3>
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Room Id</th>
                            <th>Room number</th>
                            <th>Room rent per hour</th>
                            <th>Room status</th>
                            <th>Room type</th>
                            <th>Book now</th>
                        </tr>
                    </thead>
                    <tbody>
                        {rooms.length === 0 && <tr><td colSpan="6" className='text-center'>No rooms available</td></tr>}
                        {
                            rooms.map((room) => (
                                <tr key={room.roomId}>
                                    <td>{room.roomId}</td>
                                    <td>{room.roomNumber}</td>
                                    <td>{room.roomRentPerHour}</td>
                                    <td>{room.available ? 'Available' : 'Not available'}</td>
                                    <td>{room.roomType}</td>
                                    <td>
                                        {!isAdmin && (<Button
                                            type='primary'
                                            onClick={() => handleBookRoom(room.roomId)}
                                        >Book now</Button>)}
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                {isAdmin && (<AddRoomModal workspaceId={id} handleAddRoom={handleAddRoom} />)}

            </Container>
        </>
    );
}

export default GetRoom;
