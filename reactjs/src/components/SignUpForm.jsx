import React, { useState, useRef, useContext, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function SignUpForm () {

  return (
    <section className="d-flex vh-100" style={{ backgroundColor: "rgb(33,37,41)" }}>
      <div className="container-fluid row justify-content-center align-content-center">
        <div className="card bg-dark" style={{borderRadius: '1rem'}}>
          <div className="card-body p-5 text-center">
            <h2 className="text-white">회원가입</h2>
            <p className="text-white-50 mt-2 mb-5">서비스 사용을 위해서 회원가입을 해주세요</p>
            <div className="mb-2">
              <form method="POST">
                <div className="mb-3">
                  <label className="form-label text-white">Email address</label>
                  <input type="email" className="form-control" name="email" />
                </div>
                <div className="mb-3">
                  <label className="form-label text-white">Password</label>
                  <input type="password" className="form-control" name="password" />
                </div>
                <div className="mb-3">
                  <label className="form-label text-white">Nickname</label>
                  <input type="text" className="form-control" name="nickname" />
                </div>
                <button type="submit" className="btn btn-primary">회원가입</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default SignUpForm;