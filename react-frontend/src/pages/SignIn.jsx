import React from 'react'
import { Button,Container,Card,Form,Alert } from 'react-bootstrap';
import { useState } from "react";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import axios from 'axios'
import { Link, useNavigate } from "react-router-dom";
import myImage from '../';
function SignIn() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [loggedIn, setLoggedIn] = useState(false);
    
    const preDefinedEmail = "admin@gmail.com";
    const preDefinedPassword = "Pa$$w0rd";
    const apiUrl1=process.env.REACT_APP_API_URL
    const handleSignIn = (e) => {
      e.preventDefault();
      // if (
      //   email.trim() === preDefinedEmail &&
      //   password.trim() === preDefinedPassword
      // ) {
      //   const userCredentials = {
      //     email: preDefinedEmail,
      //     password: preDefinedPassword,
      //   };
      //   setLoggedIn(true);
      //   localStorage.setItem("user", JSON.stringify(userCredentials));
      // } else {
      //   setErrorMessage("Invalid email or password");
      //   setTimeout(() => {
      //     setErrorMessage("");
      //   }, 5000);
      //   return;
      // }
      if(email===preDefinedEmail && password===preDefinedPassword){
        
        axios.post(`${process.env.REACT_APP_API_URL}/authenticate`, { username:email, password:password })
        .then(async (response) => {
          setLoggedIn(true);
          await localStorage.setItem("user", JSON.stringify({email, isAdmin:true, token:response.data.token}));
          navigate('/')
        })
        .catch(async (error) => {
          axios.post(`${process.env.REACT_APP_API_URL}/user/register`, {username:email, password:password}).then((response)=>{
            console.log(response)
            axios.post(`${process.env.REACT_APP_API_URL}/authenticate`, { username:email, password:password }).then(async (response) => {
              setLoggedIn(true);
              await localStorage.setItem("user", JSON.stringify({email, isAdmin:true, token:response.data.token}));
              navigate('/')
            })
          })
        })
      }
      else{
        
      axios.post(`${apiUrl1}/authenticate`, { username:email, password:password }).then(async (response) => {
        
        setLoggedIn(true);
        await localStorage.setItem("user", JSON.stringify({email, isAdmin:false, token:response.data.token}));
        axios.get(`${process.env.REACT_APP_API_URL}/getAllCustomers`, {
          headers: {
              Authorization: `Bearer ${Object(response.data).token}`,
          },}
          ).then((response) => {
              const customer=response.data.filter((customer)=>customer.customerEmail===email)
              localStorage.setItem("customer", JSON.stringify(customer));
              navigate('/')
          }).catch((error) => {
              console.log(error)
          })
        
      }).catch((error) => {
        if(error?.response?.status===403 || error?.response?.status===400 || error.code==="ERR_NETWORK"){
          setErrorMessage(`ERROR_NETWORK: connection refused to ${process.env.REACT_APP_API_URL}`);
        }else{
          setErrorMessage("Invalid email or password");
        }
        
      })
    };
  }
  
    
    
    return (
      <Container fluid className=" m-0 p-0">
        {!loggedIn && (
          <div className="signin">
            <Container className="form d-flex justify-content-center align-items-center vh-100">
              <Card className="form-card">
                <h2>Sign In</h2>
  
                {errorMessage && (
                  <Alert
                    variant="danger"
                    onClose={() => setErrorMessage("")}
                    dismissible
                  >
                    {errorMessage}
                  </Alert>
                )}
  
                <Form>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Control
                      type="text"
                      placeholder="Enter your username"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className='signin-input'
                    />
                  </Form.Group>
  
                  <Form.Group controlId="password" className="password">
                    <Form.Control
                      type={showPassword ? "text" : "password"}
                      name="password"
                      placeholder="Enter your password"
                      value={password}
                      className='signin-input'
                      onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button
                      onClick={() => setShowPassword(!showPassword)}
                      className="password-toggle-btn signin-input"
                    >
                      {showPassword ? (
                        <AiOutlineEyeInvisible />
                      ) : (
                        <AiOutlineEye />
                      )}
                    </Button>
                  </Form.Group>
                  <Button
                    variant="primary"
                    className="mt-2"
                    onClick={handleSignIn}
                  >
                    Sign In
                  </Button>
                  {/* <img src={process.env.PUBLIC_URL + '/logo192.png'} alt="Example" /> */}
                </Form>
                <div className="text-center mt-3">
                  ( New user? {" "}
                  <b>
                    <i><Link to="/signup">Sign Up</Link></i>
                  </b>{" "}
                  )
                </div>
              </Card>
            </Container>
          </div>
        )}
      </Container>
    );
}

export default SignIn