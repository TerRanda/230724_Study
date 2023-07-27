import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap/dist/js/bootstrap";

function MainNavigation () {

  return (
    <header>
      <nav className='navbar navbar-expand-sm bg-dark navbar-dark'>
        <div className="container-fluid">
          <Link to='/' className='navbar-brand'>LOGO</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className='collapse navbar-collapse justify-content-between' id='navbarSupportedContent'>
            <ul className='navbar-nav'>
              <li className='nav-item'><Link to='/' className='nav-link'>Home</Link></li>
              <li className='nav-item'><Link to='/about' className='nav-link'>About</Link></li>
            </ul>
            <ul className='navbar-nav'>
              <li className='nav-item'><Link to='/login' className='nav-link'>Login</Link></li>
              <li className='nav-item'><Link to='/signup' className='nav-link'>SignUp</Link></li>
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
}

export default MainNavigation;