import React, { useState, useEffect, Fragment } from "react";
import Card from "react-bootstrap/Card";
import { Container, Row, Col,  Button} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import BookingModal from "../components/BookingModal";
import { getAllWorkspaces } from "../apis/coworkspaceApis";

function GetallCoworkspaces({ workspacesFromFilter }) {
  const navigate = useNavigate();
  const [workspaces, setWorkspaces] = useState([]);
  const isAdmin=JSON.parse(localStorage.getItem("user")).isAdmin;
  useEffect(() => {
    fetchData();
  }, [workspacesFromFilter]);

  const fetchData = () => {
    
    if (workspacesFromFilter.length > 0) {
      setWorkspaces(workspacesFromFilter)
    } else {

      try {
        const token=JSON.parse(localStorage.getItem("user")).token;
        getAllWorkspaces(token).then((response) => {
          setWorkspaces(response);
        }).catch((error) => {
          if (error.response?.status === 404 || error.response?.status === 403) {
            console.log("error", error.response.status)
            navigate('/signin')
          }
        })
      } catch (error) {
        console.log(error.response.status)
        if (error.response.status === 404 || error.response.status === 403) {
          console.log("error", error.response.status)
          navigate('/signin')
        } 
      }
    }
  };
  return (
    <Fragment>
      <Container>
        <Row style={{ display: "flex", flexWrap: "wrap" }}>
          {workspaces.length > 0 ? (
            workspaces.map((workspace) => (
              <Col key={workspace.workspaceId} xs={12} sm={6} md={4}>
                <Card className="workspace-cards hover-card" style={{ height: "90%" }}>
                  <Card.Body>
                    <Card.Title>{workspace.coworkingSpaceName}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                      Rating: {workspace.rating}
                    </Card.Subtitle>
                    <Card.Text>{workspace.totalCapacity} seats available</Card.Text>
                    <Card.Text>
                      {workspace.address}, {workspace.city}, {workspace.state} - {workspace.pincode}
                    </Card.Text>
                    <Card.Text>{workspace.phoneNumber}</Card.Text>
                    {workspace.cabFacilityAvailable && (
                      <Card.Text>Cab facility available</Card.Text>
                    )}
                    {!workspace.cabFacilityAvailable && (
                      <Card.Text>Cab facility not available</Card.Text>
                    )}
                    <Card.Link   >
                      <BookingModal workspaceid={workspace.workspaceId}/>
                    </Card.Link>
                    {isAdmin && (
                      <Link to={`/manage-workspace/${workspace.workspaceId}`}>
                      <Button variant="secondary" style={{marginLeft:"10px"}}>Manage</Button>
                    </Link>
                    )}
                    
                  </Card.Body>
                </Card>
              </Col>
            ))
          ) : (
            <p>No data found</p>
          )}
          
        </Row>
      </Container>
    </Fragment>
  );
}

export default GetallCoworkspaces;
