const express=require('express');
const {signupProfessor,signupStudent,login,resetPassword}=require('../controllers/auth')
const {isLoggedin}=require('../middleware/isAuthenticated')
const authRouter=express.Router();
const {wrapAsync}=require('../utils/asyncErrorHandler')

authRouter.post('/signup/students',wrapAsync(signupStudent));
authRouter.post('/signup/professors',wrapAsync(signupProfessor));
authRouter.post('/login',wrapAsync(login));
authRouter.put('/resetpass',isLoggedin,wrapAsync(resetPassword))
// authRouter.put('/resetpass',isLoggedin,resetPassword);

module.exports={authRouter}
