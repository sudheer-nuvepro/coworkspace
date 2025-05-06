import { axiosRequest } from "./api";

export const bookRoom=async (customerId,roomId,checkInTime,checkOutTime,token)=>{
    const checkInArr=checkInTime.split("T")
    const checkOutArr=checkOutTime.split("T")
    console.log(checkInArr.join(' '),checkOutArr.join(' '))
    const cDate=checkInArr.join(' ')
    const oDate=checkOutArr.join(' ')
    return await axiosRequest.post(`${process.env.REACT_APP_API_URL}/booking/bookRoom/${customerId}/${roomId}`,{
        checkInDate: cDate,
        checkOutDate: oDate,
        travelOpted:true,
        paymentType: "Stripe",
        paymentDate: checkInArr[0],
        bookingDate: checkInArr[0],
        bookingStatus:"Pending",
        paymentStatus:"Pending"
    },{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response)=>{
        return response
    })
}

export const getAllBookings=async (token)=>{
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/booking/getAllBookings`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response)=>{
        return response.data
    })
}