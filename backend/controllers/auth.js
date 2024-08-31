const jwt=require('jsonwebtoken'); //jwt for api security
const path=require('path')
require('dotenv').config({path:path.resolve(__dirname+'../.env')})
const key=process.env.SECRETKEY //secret key to sign the payload for jwt 
//conneting to firebase for user authentication
const {initializeApp}=require('firebase/app');
const {getAuth,createUserWithEmailAndPassword, signInWithEmailAndPassword, sendPasswordResetEmail}=require('firebase/auth')
const {getClient}=require('../db');
const {studentSignup,professorSignup,userLogin}=require('../utils/schemaValidator')
const client=getClient();
const _db=client.db(process.env.DBNAME);
const {ExpressError}=require('../utils/customErrorHandler')
const firebaseConfig={
    apiKey: process.env.APIKEY,
    authDomain: process.env.AUTHDOMAIN,
    projectId: process.env.PROJECTID,
    storageBucket: process.env.STORAGEBUCKET,
    messagingSenderId: process.env.MESSAGINGSENDERID,
    appId: process.env.APPID
};


const firebaseapp=initializeApp(firebaseConfig);
const auth = getAuth(firebaseapp);

//student signup
const signupStudent= async (req,res)=>{
    const validatedData= studentSignup.safeParse(req.body);
    if(validatedData.success){
        const {admno,email,password}=req.body
    
        const foundStudentbyEmail = await _db.collection('students').findOne({email:email})
        if(foundStudentbyEmail){
            throw new ExpressError("Email already in use",501)
        }
        const foundStudentByAdmNo=await _db.collection('students').findOne({email:email})
        if(foundStudentByAdmNo){
            throw new ExpressError("Account already exists with given Admission Number",501)
        }
        const userInfo= await createUserWithEmailAndPassword(auth,email,password)
        const userId=userInfo.user.uid;
        const userDetails={
            admno,
            email:email,
            userid:userId,
            role:"student",
            createdAt:new Date()
        }
        //store user profile in mongodb
        await _db.collection('students').insertOne(userDetails);
        const token=jwt.sign({firebaseuserid:userId,email:email,role:"student"},key);
        return res.status(200).json({message:"SignedUp successfully",token});
    }
    else{
        //zod validation falied
        const errors=validatedData.error.issues;
        const errorMessages=errors.map(data=>{
            return `${data.path[0]} : ${data.message}`
        })
        const message={"Error":"Invalid Input","Errors":errorMessages}
        throw new ExpressError(message,501)
    }

}

//professor signup
const signupProfessor= async (req,res)=>{
    const validatedData= professorSignup.safeParse(req.body);
    if(validatedData.success){
        const {username,email,password}=req.body
    
        const foundProfbyEmail = await _db.collection('professors').findOne({email:email})
        if(foundProfbyEmail){
            throw new ExpressError("Email already in use",501)
        }
        const userInfo= await createUserWithEmailAndPassword(auth,email,password)
        const userId=userInfo.user.uid;
        const userDetails={
            username,
            email:email,
            userid:userId,
            role:"professor",
            createdAt:new Date()
        }
        //store user profile in mongodb
        await _db.collection('professors').insertOne(userDetails);
        const token=jwt.sign({firebaseuserid:userId,email:email,role:"professor"},key);
        return res.status(200).json({message:"SignedUp successfully",token});
    }
    else{
        //zod validation falied
        const errors=validatedData.error.issues;
        const errorMessages=errors.map(data=>{
            return `${data.path[0]} : ${data.message}`
        })
        const message={"Error":"Invalid Input","Errors":errorMessages}
        throw new ExpressError(message,501)
    }

}


const login = async (req,res)=>{
    const validatedData=userLogin.safeParse(req.body);
    if(validatedData.success){
            const {email,password}=req.body
            const userInfo=await signInWithEmailAndPassword(auth,email,password);
            const userId=userInfo.user.uid
            const stuObject=await _db.collection('students').findOne({userid:userId})
            const profObject=await _db.collection('professors').findOne({userid:userId})
            let role;
            if(stuObject){
                role="student"
            }
            if(profObject){
                role="professor"
            }
            const token= jwt.sign({firebaseuserid:userId,email:email,role},key)
            return res.status(200).send({message:"signed in succesfully",token:token})
    }
    else{
        //zod validation errors
        const errors=validatedData.error.issues;
        const errorMessages=errors.map(data=>{
            return `${data.path[0]} : ${data.message}`
        })
        const message={"Error":"Invalid Input","Errors":errorMessages}
        throw new ExpressError(message,501)
    }
}

const resetPassword= async (req,res)=>{
    const email=req.email
    try{
        await sendPasswordResetEmail(auth,email);
        return res.status(200).json({message:"Password reset link has been mailed to you"})
    }
    catch(error){
        return res.status(501).json({message:"Can't Send mail to reset password at the moment"})
    }
}

module.exports={
    signupStudent,signupProfessor,login,resetPassword
}