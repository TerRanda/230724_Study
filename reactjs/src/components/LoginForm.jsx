import React, { useState, useRef, useContext, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function LoginForm () {
  return (
    <section className="d-flex vh-100" style={{ backgroundColor: "rgb(33,37,41)" }}>
      <div className="container-fluid row justify-content-center align-content-center">
        <div className="card bg-dark" style={{borderRadius: '1rem'}}>
          <div className="card-body p-5 text-center">
            <h2 className="text-white">LOGIN</h2>
            <p className="text-white-50 mt-2 mb-5">서비스를 사용하려면 로그인을 해주세요!!!!</p>
            <div className="mb-2">
              <form action="/login" method="POST">
                <div className="mb-3">
                  <label className="form-label text-white">Email address</label>
                  <input type="email" style={{}} className="form-control" name="username" />
                </div>
                <div className="mb-3">
                  <label className="form-label text-white">Password</label>
                  <input type="password" style={{}} className="form-control" name="password" />
                </div>
                <button type="submit" className="btn btn-primary">로그인</button>
              </form>
              <br />
              <div>
                <a href="http://localhost:8090/oauth2/authorization/google">
                  <img src="/230724_Study/img/google3.png" alt="google" />
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}

export default LoginForm;