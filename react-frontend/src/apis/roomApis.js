import { axiosRequest } from "./api";

export const getAvailableRooms=async (id,checkInTime,checkOutTime,token)=>{
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/availableRoomsByWorkspaceId/${id}?startTime=${checkInTime}&endTime=${checkOutTime}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response)=>{
       return response.data
    })
}

export const addRoomInCoworkspace=async (id,room,token)=>{
    return await axiosRequest.post(`${process.env.REACT_APP_API_URL}/addRoomsInCoworkspaceRoom/${id}`,room,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response)=>{
        return response.data
    })
}