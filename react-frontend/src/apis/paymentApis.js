import {axiosRequest} from './api'

export const checkPayment=async (paymentIntentId,bookingId,token)=>{
    return await axiosRequest.post(`${process.env.REACT_APP_API_URL}/payment-intent-succeeded?paymentIntentId=${paymentIntentId}&bookingId=${bookingId}`,{},{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
        }).then((response)=>{
            return response.data
        })
}