import { axiosRequest } from "./api";

export const addWorkspace = async (data,token) => {
    return await axiosRequest.post('/addCoworkspace', data,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data;
    })
};
export const updateCoworkspace = async (wokspaceId,data,token) => {
    console.log('wokspaceId',wokspaceId)

    return axiosRequest.put(`${process.env.REACT_APP_API_URL}/updateCoworkspaceById/${wokspaceId}`, data, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data;
    })
}
export const getAllWorkspaces = async (token) => {
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/getAllCoworkspaces`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }
    ).then((response) => {
        return response.data
    });
}
export const getCoworkspaceById= async (id,token) => {
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/getCoworkspaceById/${id}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data
    })
}
export const getCoworkspaceByCity = async (city,token) => {
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/getCoworkspaceByCity?city=${city}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data
    });
}
export const getCoworkspaceByName = async (name,token) => {
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/getCoworkSpaceByName?coworkspaceName=${name}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data
    });
}
export const getCoworkspaceByPincode = async (pincode,token) => {
    return await axiosRequest.get(`${process.env.REACT_APP_API_URL}/getCoworkspaceByPincode?pincode=${pincode}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data
    });
}

export const deleteCoworkspaceById = async (id,token) => {
    return await axiosRequest.delete(`${process.env.REACT_APP_API_URL}/deleteCoworkspaceById/${id}`,{
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    }).then((response) => {
        return response.data
    })
}