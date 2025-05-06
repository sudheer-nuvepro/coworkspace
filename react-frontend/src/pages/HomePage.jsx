import React, { Fragment, useState,useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { Button, Container, Navbar, Row, Col } from "react-bootstrap";
import { Navigate, Link, useNavigate } from "react-router-dom";
import FilterWorkspaces from "./FilterWorkspaces";
import GetallCoworkspaces from "./GetallCoworkspaces";



function HomePage() {

  const navigate = useNavigate();
  const [workspaces, setWorkspaces] = useState([]);
  const [showBooking,setShowBooking]=useState(true);
  const user = localStorage.getItem("user");
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"))
    if (!user) {
        navigate('/signin');
    }
    if(user.isAdmin){
        setShowBooking(false)
    }
    if(window.location.href.includes('bookings')){
        setShowBooking(false)
    }
  })
  if (!user) {
    return <Navigate to={"/signin"} />;
  }
  const isAdmin = JSON.parse(localStorage.getItem("user")).isAdmin;
  if(isAdmin){
    localStorage.removeItem("customer")
  }
  const getFilteredData = (data) => {
    setWorkspaces(data)
  }
  const signOutHandler = () => {
    localStorage.removeItem('user');
    navigate('/signin');
  };
  
  return (
    <Fragment>
      <Container fluid className="m-0 p-0" style={{ height: "96vh" }}>
        <Navbar
          className="bg-body-tertiary"
          bg="dark"
          data-bs-theme="dark"
          sticky="top"
        >
          <Container className="d-flex justify-content-between">
            <Navbar.Brand href="" className="ml-auto">
              <span className="text-bold">Work-From-Here</span>
            </Navbar.Brand>
            <div>
              {showBooking && <Button id="bookingsButton" className="btn btn-success mx-3" onClick={()=>{
                navigate('/bookings')
              }} >
                Bookings
              </Button>}
              <Button id="logoutButton" className="btn btn-danger ml-2" onClick={signOutHandler}>
                Sign out
              </Button>
            </div>
          </Container>
        </Navbar>
        <Container className="mt-4">
          <h1 className=" text-center text-bold">Find Your Workplace</h1>

          <Row className="mt-5">
            <Col md={8}>
              <FilterWorkspaces onFilter={getFilteredData} />
            </Col>
            <Col md={2}></Col>
            <Col md={2} style={{ float: "right" }}>
              {isAdmin && (
                <Link
                  variant="primary"
                  className="form-control btn btn-sm btn-success"
                  to="/add-workspace"
                >
                  Add Workspace
                </Link>
              )}

            </Col>
          </Row>
          {/* </Card> */}
          <div>
            <GetallCoworkspaces workspacesFromFilter={workspaces} />
          </div>
        </Container>
      </Container>
    </Fragment>
  );
}

export default HomePage;
