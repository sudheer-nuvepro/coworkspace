import React, { Fragment, useState, useEffect } from "react";
import { Form, Button, Row, Col } from "react-bootstrap";
import { axiosRequest } from "../apis/api";
import { getCoworkspaceByCity,getCoworkspaceByName,getCoworkspaceByPincode,getAllWorkspaces } from "../apis/coworkspaceApis";

const FilterWorkspaces = (props) => {
    const [filter, setfilter] = useState("");
    const [searchValue, setSearchValue] = useState("");
    const [filteredData, setFilteredData] = useState([]);

    const handleSearch = async () => {
        const token=JSON.parse(localStorage.getItem("user")).token;
        if (filter !== "" && searchValue !== "") {
            const apiUrl = 'http://localhost:8080'

            if (filter === "city") {
                getCoworkspaceByCity(searchValue,token).then((response) => {
                    setFilteredData(response);
                }).catch((error) => {
                    setFilteredData([
                        {
                            "workspaceId": 5000,
                            "coworkingSpaceName": "Workspaces not found",
                            "totalCapacity": "",
                            "phoneNumber": "",
                            "companyEmail": "",
                            "address": "",
                            "city": "",
                            "state": "",
                            "pincode": "",
                            "rating": "not found",
                            "cabFacilityAvailable": ""
                        }])
                    console.error("Error fetching coworkspace details:", error);
                });
            } else if (filter === "name") {
                getCoworkspaceByName(searchValue,token).then((response) => {
                    setFilteredData(response);
                }).catch((error) => {
                    setFilteredData([
                        {
                            "workspaceId": 5000,
                            "coworkingSpaceName": "Workspaces not found",
                            "totalCapacity": "",
                            "phoneNumber": "",
                            "companyEmail": "",
                            "address": "",
                            "city": "",
                            "state": "",
                            "pincode": "",
                            "rating": "not found",
                            "cabFacilityAvailable": ""
                        }])
                    console.error("Error fetching coworkspace details:", error);
                })
            } else if (filter === "pincode") {
                getCoworkspaceByPincode(searchValue,token).then((response) => {
                    setFilteredData(response);
                }).catch((error) => {
                    setFilteredData([
                        {
                            "workspaceId": 5000,
                            "coworkingSpaceName": "Workspaces not found",
                            "totalCapacity": "",
                            "phoneNumber": "",
                            "companyEmail": "",
                            "address": "",
                            "city": "",
                            "state": "",
                            "pincode": "",
                            "rating": "not found",
                            "cabFacilityAvailable": ""
                        }])
                    console.error("Error fetching coworkspace details:", error);
                })
            }
            else {
                try {
                    getAllWorkspaces(token).then((response) => {
                        setFilteredData(response);
                    }).catch((error) => {
                        setFilteredData([
                            {
                                "workspaceId": 5000,
                                "coworkingSpaceName": "Workspaces not found",
                                "totalCapacity": "",
                                "phoneNumber": "",
                                "companyEmail": "",
                                "address": "",
                                "city": "",
                                "state": "",
                                "pincode": "",
                                "rating": "not found",
                                "cabFacilityAvailable": ""
                            }])
                        console.error("Error fetching coworkspace details:", error);
                    })
                    axiosRequest
                        .get(`${apiUrl}/getAllCoworkspaces`)
                        .then((response) => {
                            setFilteredData(response.data);

                        })
                        .catch((error) => {
                            setFilteredData([
                                {
                                    "workspaceId": 5000,
                                    "coworkingSpaceName": "Workspaces not found",
                                    "totalCapacity": "",
                                    "phoneNumber": "",
                                    "companyEmail": "",
                                    "address": "",
                                    "city": "",
                                    "state": "",
                                    "pincode": "",
                                    "rating": "not found",
                                    "cabFacilityAvailable": ""
                                }])
                            console.error("Error fetching coworkspace details:", error);
                        });
                } catch {
                    console.log("Something went wrong")
                }

            }
        }
    };

    const handleReset = () => {
        setFilteredData([]);
        setSearchValue('')
        setfilter('')
    };
    useEffect(() => {
        props.onFilter(filteredData);
    }, [filteredData, props]);
    return (
        <Fragment>
            <Row>
                <Col>
                    <Form.Control
                        as="select"
                        value={filter}
                        onChange={(e) => setfilter(e.target.value)}
                        style={{ borderColor: '#6c757d' }}
                    >
                        <option value="">Select Filter</option>
                        <option value="city" >City</option>
                        <option value="name">Name</option>
                        <option value="pincode">Pincode</option>
                    </Form.Control>
                </Col>
                <Col>
                    <Form.Control
                        type="text"
                        value={searchValue}
                        onChange={(e) => setSearchValue(e.target.value)}
                        style={{ borderColor: '#6c757d' }}
                    />
                </Col>
                <Col md={3}>
                    <Row>
                        <Col>
                            <Button className="mt-2" variant="primary" onClick={handleSearch}>
                                Search
                            </Button>
                        </Col>
                        <Col>
                            <Button
                                className="mt-2"
                                variant="secondary"
                                onClick={handleReset}
                            >
                                Reset
                            </Button>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </Fragment>
    );
};

export default FilterWorkspaces;