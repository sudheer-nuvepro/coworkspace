import './App.css';
import SignIn from './pages/SignIn';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage';
import SignUp from './pages/SignUp';
import Addworkspaces from './pages/Addworkspaces';
import GetRoom from './pages/GetRoom';
import ManageWorkspace from './pages/ManageWorkspace';
import Payment from './pages/Payment';
import Bookings from './pages/Bookings';

function App() {
    return (
        <BrowserRouter>
        <Routes>
          <Route path="/signin" element={<SignIn />} />
          <Route path="/" element={<HomePage />} />
          <Route path="rooms/:id/:checkInTime/:checkOutTime" element={<GetRoom/>}/>
          <Route path='/add-workspace' element={<Addworkspaces />} />
          <Route path='/signup' element={<SignUp />} />
          <Route path='/manage-workspace/:workspaceId' element={<ManageWorkspace />} />
          <Route path='/payment' element={<Payment />} />
          <Route path='/bookings' element={<Bookings />} />
        </Routes>
      </BrowserRouter>
    )
}

export default App;
