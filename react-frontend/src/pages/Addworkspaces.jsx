import React,{useState} from 'react';
import {
    Container,
    Toast,
    Button,
    Col,
    Form,
    Row,
    ToastContainer,
} from "react-bootstrap";
import { Link, useNavigate} from "react-router-dom";
import Header from './Header';
import { addWorkspace } from '../apis/coworkspaceApis';

function Addworkspaces() {
    const navigate = useNavigate();
    
  const [formValues, setFormValues] = useState({
    name: "",
    capacity: "",
    state: "",
    pincode: "",
    city: "",
    phone: "",
    email: "",
    address: "",
    cabFacility: "",
    rating: "",
  });
  const [formErrors, setFormErrors] = useState({
    name: "",
    capacity: "",
    state: "",
    pincode: "",
    city: "",
    phone: "",
    email: "",
    address: "",
    cabFacility: "",
    rating: "",
  });
  const [showErrorToast, setShowErrorToast] = useState(false);
  const [errorToastMessage, setErrorToastMessage] = useState("");

  const validateForm = () => {
    let valid = true;
    const newErrors = {};

    if (!formValues.name.trim()) {
      newErrors.name = "Name is required.";
      valid = false;
    }

    if (formValues.capacity.trim() === "") {
      newErrors.capacity = "Please enter the capacity.";
      valid = false;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(formValues.email)) {
      newErrors.email = "Please enter a valid email address.";
      valid = false;
    }
    const phonePattern = /^[0-9()+-]+$/;
    if (!phonePattern.test(formValues.phone)) {
      newErrors.phone = "Please enter a valid phone number (numbers only).";
    }

    if (/[a-zA-Z]/.test(formValues.phone)) {
      newErrors.phone = "Phone number should not contain alphabets.";
    }


    if (formValues.city.trim() === "") {
      newErrors.city = "Please select a city.";
      valid = false;
    }

    if (formValues.pincode.trim() === "") {
      newErrors.pincode = "Please enter pincode";
      valid = false;
    }
    if(formValues.pincode.trim().length !== 6){
        newErrors.pincode = "Pincode should be 6 digits";
        valid = false;
    }
    if(formValues.address.trim() === ""){
        newErrors.address = "Please enter address.";
        valid = false;
    }
    if(formValues.state.trim() === ""){
        newErrors.state = "Please enter state.";
        valid = false;
    }
    if(formValues.rating.trim() === ""){
        newErrors.rating = "Please enter rating.";
        valid = false;
    }
    if(Number(formValues.rating.trim()) > 5){
        newErrors.rating = "Rating should be less than 5.";
        valid = false;
    }
    if(formValues.cabFacility.trim() === ""){
        newErrors.cabFacility = "Please enter cabFacility.";
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

  const handleSubmit =  (e) => {
    e.preventDefault();
    if (validateForm()) {
        const token=JSON.parse(localStorage.getItem("user")).token;
        console.log(Boolean(formValues.cabFacility))
        const sendData={
            coworkingSpaceName: formValues.name,
            totalCapacity: Number(formValues.capacity),
            phoneNumber: formValues.phone,
            companyEmail: formValues.email,
            address: formValues.address,
            city: formValues.city,
            state: formValues.state,
            pincode: formValues.pincode,
            rating: Number(formValues.rating),
            cabFacilityAvailable: formValues.cabFacility === "false" ? false : Boolean(formValues.cabFacility)
        }
        console.log(sendData)
        addWorkspace(sendData,token).then((res)=>{
            navigate('/');
        }).catch((error)=>{
          if(error?.response?.status===403){
            setShowErrorToast(true);
            setErrorToastMessage('Session Expired. Please login again.')
            setTimeout(() => {
              localStorage.clear();
              navigate('/signin')
            },4000)
          }
          const err=error?.response?.data?.responseMessage|| 'SERVER_ISSUE'
          setShowErrorToast(true);
          setErrorToastMessage(`Error in adding workspace: ${err}`);
        })
    }
  };
  return (
    <>
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
        <Header />
      <Container>
        <Form className="mt-4 p-4 border" onSubmit={handleSubmit}>
          <h2 className="text-center">Add Workspace</h2>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>Coworkspace Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                id="coworkspace_name"
                placeholder="Enter coworkspace name"
                value={formValues.name}
                onChange={handleChange}
              />
              {formErrors.name && (
                <div className="text-danger">{formErrors.name}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Capacity</Form.Label>
              <Form.Control
                type="number"
                name="capacity"
                id="capacity"
                placeholder='Enter the capacity'
                value={formValues.capacity}
                onChange={handleChange}
              >
                
              </Form.Control>
              {formErrors.capacity && (
                <div className="text-danger">{formErrors.capacity}</div>
              )}
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>Rating</Form.Label>
              <Form.Control
                type="text"
                name="rating"
                id="rating"
                placeholder="Enter rating"
                value={formValues.rating}
                onChange={handleChange}
              />
              {formErrors.rating && (
                <div className="text-danger">{formErrors.rating}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Cab Facility</Form.Label>
              <Form.Control
                as="select"
                name="cabFacility"
                id="cabFacility"
                value={formValues.cabFacility}
                onChange={handleChange}
              >
                <option value="">Select yes if cab facility available</option>
                <option value="true">Yes</option>
                <option value="false">No</option>
              </Form.Control>
              {formErrors.cabFacility && (
                <div className="text-danger">{formErrors.cabFacility}</div>
              )}
            </Form.Group>

          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>Address</Form.Label>
              <Form.Control
                type="text"
                name="address"
                id="address"
                placeholder="Enter address"
                value={formValues.address}
                onChange={handleChange}
              />
              {formErrors.address && (
                <div className="text-danger">{formErrors.address}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>State</Form.Label>
              <Form.Control
                type="text"
                name="state"
                
                id="state"
                placeholder="Enter state"
                value={formValues.state}
                onChange={handleChange}
              />
              {formErrors.state && (
                <div className="text-danger">{formErrors.state}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Pincode</Form.Label>
              <Form.Control
                type='text'
                name="pincode"
                id="pincode"
                placeholder='Enter the pincode'
                value={formValues.pincode}
                onChange={handleChange}
              >
                
              </Form.Control>
              {formErrors.pincode && (
                <div className="text-danger">{formErrors.pincode}</div>
              )}
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col}>
              <Form.Label>City</Form.Label>
              <Form.Control
                as="select"
                name="city"
                id="employee_city"
                value={formValues.city}
                onChange={handleChange}
              >
                <option value="">Select a City</option>
                <option value="Bangalore">Bangalore</option>
                <option value="Mumbai">Mumbai</option>
                <option value="Delhi">Delhi</option>
              </Form.Control>
              {formErrors.city && (
                <div className="text-danger">{formErrors.city}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Phone</Form.Label>
              <Form.Control
                type="text"
                name="phone"
                id="employee_phone"
                placeholder="Enter phone"
                value={formValues.phone}
                onChange={handleChange}
              />
              {formErrors.phone && (
                <div className="text-danger">{formErrors.phone}</div>
              )}
            </Form.Group>

            <Form.Group as={Col}>
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="text"
                name="email"
                id="employee_email"
                value={formValues.email}
                placeholder="Enter email"
                onChange={handleChange}
              />
              {formErrors.email && (
                <div className="text-danger">{formErrors.email}</div>
              )}
            </Form.Group>
          </Row>
          <Button className="align-center" variant="primary" type="submit">
            Submit
          </Button>{" "}
          <Link
            to="/"
            className="btn btn-secondary"
            id="AddEmployeeCancelButton"
          >
            Cancel
          </Link>
        </Form>
      </Container>
    </>
  )
}

export default Addworkspaces