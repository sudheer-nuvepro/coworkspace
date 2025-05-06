import axios from 'axios';

const baseUrl=process.env.REACT_APP_API_URL || 'http://localhost:8080/';
export const axiosRequest=axios.create({
  baseURL: baseUrl, 
})
