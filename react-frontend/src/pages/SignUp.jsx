import { useState } from "react";
import React from 'react'
import {
    Container,
    Toast,
    Button,
    Col,
    Form,
    Row,
    ToastContainer,
    Card,
  } from "react-bootstrap";
import { Link, useNavigate} from "react-router-dom";
import axios from 'axios'

function SignUp() {
    const navigate = useNavigate();
    const [isAdmin, setIsAdmin] = useState(false);
  const [formValues, setFormValues] = useState({
    customerFirstName: "",
    customerLastName: "",
    companyName: "",
    companyAddress: "",
    state: "",
    city: "",
    country:"",
    phoneNumber: "",
    pincode: "",
    customerEmail: "",
    title:"",
    password:"",
    confirmPassword:""
  });
  const [formErrors, setFormErrors] = useState({
    customerFirstName: "",
    customerLastName: "",
    companyAddress: "",
    companyName: "",
    state: "",
    city: "",
    country:"",
    phoneNumber: "",
    customerEmail: "",
    pincode: "",
    title:"",
    password:"",
    confirmPassword:""
  });
  const [showErrorToast, setShowErrorToast] = useState(false);
  const [errorToastMessage, setErrorToastMessage] = useState("");
  const validateForm = () => {
    let valid = true;
    const newErrors = {};

    if (formValues.customerFirstName.trim()==="") {
      newErrors.customerFirstName = "First name is required.";
      valid = false;
    }
    if (formValues.customerLastName.trim() === "") {
      newErrors.customerLastName = "Please enter customer last name.";
      valid = false;
    }
    if(formValues.companyName.trim() === "") {
        newErrors.companyName = "Please enter company name.";
        valid = false;
    }
    if(formValues.companyAddress.trim() === "") {
        newErrors.companyAddress = "Please enter company address.";
        valid = false;
    }
    if(formValues.city.trim()===""){
        newErrors.city="Please enter city."
        valid=false;
    }
    if(formValues.state.trim()===""){
        newErrors.state="Please enter state."
        valid=false;
    }
    if(formValues.country.trim()===""){
        newErrors.country="Please enter country."
        valid=false;
    }
    if(formValues.pincode.trim()===""){
        newErrors.pincode="Please enter pincode."
        valid=false;
    }
    if(formValues.phoneNumber.trim()===""){
        newErrors.phoneNumber="Please enter phone number."
        valid=false;
    }
    if(formValues.title.trim()===""){
        newErrors.title="Please enter title."
        valid=false;
    }
    if(formValues.password.trim()===""){
        newErrors.password="Please enter password."
        valid=false;
    }
    if(formValues.confirmPassword.trim()===""){
        newErrors.confirmPassword="Please enter confirm password."
        valid=false;
    }
    if(formValues.customerEmail.trim()===""){
        newErrors.customerEmail="Please enter email."
        valid=false;
    }
    // if(formValues.customerEmail.trim()!=="admin@gmail.com"){
    //     newErrors.customerEmail="Do not use admin gmail to login"
    //     valid=false;
    // }
    // const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // if (!emailPattern.test(formValues.customerEmail)) {
    //   newErrors.email = "Please enter a valid email address.";
    //   valid = false;
    // }

    if (formValues.password !== formValues.confirmPassword) {
        newErrors.confirmPassword = "Passwords do not match.";
        valid = false;
    }

    setFormErrors(newErrors);
    return valid;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
        const userObj={
            "username":formValues.customerEmail,
            "password":formValues.password
        }
        const customerObj={
            "customerFirstName":formValues.customerFirstName,
            "customerLastName":formValues.customerLastName,
            "companyName":formValues.companyName,
            "city":formValues.city,
            "state":formValues.state,
            "country":formValues.country,
            "pincode":Number(formValues.pincode),
            "companyAddress":formValues.companyAddress,
            "customerEmail":formValues.customerEmail,
            "phoneNumber":formValues.phoneNumber,
            "title":formValues.title,
            "password":formValues.password
        }
        axios
        .post(`${process.env.REACT_APP_API_URL}/user/register`, userObj)
        .then((response) => {
          axios.post(`${process.env.REACT_APP_API_URL}/authenticate`,userObj).then(async (response) => {
            setIsAdmin(false)
            const email=userObj.username
            await localStorage.setItem("user", JSON.stringify({email, isAdmin, token: Object(response.data).token}));
            const token = Object(response.data).token;
            axios.post(`${process.env.REACT_APP_API_URL}/addNewCustomer`, customerObj, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },}
                ).then((response) => {
                    axios.get(`${process.env.REACT_APP_API_URL}/getAllCustomers`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },}
                        ).then((response) => {
                            const customer=response.data.filter((customer)=>customer.customerEmail===email)
                            localStorage.setItem("customer", JSON.stringify(customer));
                            navigate('/')
                        }).catch((error) => {
                            console.log(error)
                        })
                }).catch((error) => {
                console.log(error)
            })
          })
        })
        .catch((error) => {
          console.log(error)
          const message =
            error.code === "ERR_NETWORK"
              ? "SERVER_ISSUE: cannot connect to server."
              : "Please check if User Email already registered, or verify other fields.";
          
          setErrorToastMessage(message);

          setShowErrorToast(true);
        });
    }
  };
   
    return (
    <Container fluid className="signin" >
            
      <ToastContainer className="p-3" position="top-center">
        <Toast
          bg={"danger"}
          show={showErrorToast}
          onClose={() => setShowErrorToast(false)}
          delay={5000}
          autohide={true}
          animation={true}
        >
          <Toast.Header>
            <strong className="me-auto">Form Submission Error</strong>
          </Toast.Header>
          <Toast.Body>
            <b>{errorToastMessage}</b>
          </Toast.Body>
        </Toast>
      </ToastContainer>
      <Container className="form d-flex justify-content-center align-items-center">
        <Card className="mt-4 form-card-signup" >
        <Form  onSubmit={handleSubmit}>
        {/* <button onClick={sampleSubmit}>sample submit</button> */}
          <h2 className="text-center">Sign up</h2>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                name="customerFirstName"
                id="customer_first_name"
                placeholder="Enter first name"
                value={formValues.customerFirstName}
                onChange={handleChange}
              />
              {formErrors.customerFirstName && (
                <div className="text-danger">{formErrors.customerFirstName}</div>
              )}
            </Form.Group>
            <Form.Group as={Col}>
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                name="customerLastName"
                id="customer_last_name"
                placeholder="Enter last name"
                value={formValues.customerLastName}
                onChange={handleChange}
              />
              {formErrors.customerLastName && (
                <div className="text-danger">{formErrors.customerLastName}</div>
              )}
            </Form.Group>
            
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>State</Form.Label>
              <Form.Control
                type="text"
                name="state"
                id="customer_first_name"
                placeholder="Enter state"
                value={formValues.state}
                onChange={handleChange}
              />
              {formErrors.state && (
                <div className="text-danger">{formErrors.state}</div>
              )}
            </Form.Group>
            <Form.Group as={Col}>
              <Form.Label>Job title</Form.Label>
              <Form.Control
                type="text"
                name="title"
                id="customer_last_name"
                placeholder="Enter job title"
                value={formValues.title}
                onChange={handleChange}
              />
              {formErrors.title && (
                <div className="text-danger">{formErrors.title}</div>
              )}
            </Form.Group>
            
          </Row>
          <Row className="mb-3">
          <Form.Group as={Col}>
              <Form.Label>Company Name</Form.Label>
              <Form.Control
                type="text"
                name="companyName"
                id="company_name"
                placeholder="Enter company name"
                value={formValues.companyName}
                onChange={handleChange}
              />
              {formErrors.companyName && (
                <div className="text-danger">{formErrors.companyName}</div>
              )}
            </Form.Group>
          <Form.Group as={Col}>
              <Form.Label>Country</Form.Label>
              <Form.Control
                type="text"
                name="country"
                id="country"
                placeholder="Enter country"
                value={formValues.country}
                onChange={handleChange}
              />
              {formErrors.companyName && (
                <div className="text-danger">{formErrors.companyName}</div>
              )}
            </Form.Group>
          <Form.Group as={Col}>
              <Form.Label>Company Address</Form.Label>
              <Form.Control
                type="text"
                name="companyAddress"
                id="company_address"
                placeholder="Enter company address"
                value={formValues.companyAddress}
                onChange={handleChange}
              />
              {formErrors.companyAddress && (
                <div className="text-danger">{formErrors.companyAddress}</div>
              )}
            </Form.Group>

            
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>City</Form.Label>
              <Form.Control
                type="text"
                name="city"
                id="employee_city"
                placeholder="Enter city"
                value={formValues.city}
                onChange={handleChange}
              >
              </Form.Control>
              {formErrors.city && (
                <div className="text-danger">{formErrors.city}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Phone</Form.Label>
              <Form.Control
                type="text"
                name="phoneNumber"
                id="employee_phone"
                placeholder="Enter phone"
                value={formValues.phoneNumber}
                onChange={handleChange}
              />
              {formErrors.phoneNumber && (
                <div className="text-danger">{formErrors.phoneNumber}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="email"
                name="customerEmail"
                id="user_email"
                value={formValues.customerEmail}
                placeholder="Enter username (email)"
                onChange={handleChange}
              />
              {formErrors.customerEmail && (
                <div className="text-danger">{formErrors.customerEmail}</div>
              )}
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>Pincode</Form.Label>
              <Form.Control
                type="text"
                name="pincode"
                id="employee_city"
                placeholder="Enter pincode"
                value={formValues.pincode}
                onChange={handleChange}
              >
              </Form.Control>
              {formErrors.pincode && (
                <div className="text-danger">{formErrors.pincode}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                id="employee_phone"
                placeholder="Enter password"
                value={formValues.password}
                onChange={handleChange}
              />
              {formErrors.password && (
                <div className="text-danger">{formErrors.password}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Confirm password</Form.Label>
              <Form.Control
                type="password"
                name="confirmPassword"
                id="user_email"
                value={formValues.confirmPassword}
                placeholder="Repeat password"
                onChange={handleChange}
              />
              {formErrors.confirmPassword && (
                <div className="text-danger">{formErrors.confirmPassword}</div>
              )}
            </Form.Group>
          </Row>
          <Button className="align-center" variant="primary" type="submit">
            Sign up
          </Button>{" "}
          <Link
            to="/signin"
            className="btn btn-secondary"
            id="AddEmployeeCancelButton"
          >
            Sign in
          </Link>
        </Form>
        </Card>
        </Container>
    </Container>
    )
}

export default SignUp