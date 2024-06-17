import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Components/Login/Login';
import Patrimony from './Components/Patrimony/Patrimony';
import Users from './Components/User/User';

function App() {
  return (
    <Router>
      <Routes>
      <Route path='/' element={<Login/>}></Route>
        <Route path='/login' element={<Login/>}></Route>
        <Route path='/patrimony' element={<Patrimony/>}></Route>
        <Route path='/user' element={<Users/>}></Route>
      </Routes>
    </Router>
  );
}

export default App;
