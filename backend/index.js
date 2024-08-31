const express=require('express'); //require express
const { FirebaseError } = require('firebase/app');
const { MongoServerError, MongoServerSelectionError, MongoTopologyClosedError } = require('mongodb');
const {connectTodb}=require('./db'); //for connection to database
const {authRouter}=require('./routers/auth');
const {courseRouter}=require('./routers/courses')
const app=express();
const PORT=process.env.PORT || 3000


//middleware
app.use(express.json())
app.use('/auth',authRouter);
app.use('/courses',courseRouter);

// app.use('/api/posts',postRouter);
// app.use('/api/users',userRouter)
// app.use('/api/groups',groupRouter);

app.get('/',(req,res)=>{
    res.status(200).json({message:"Home"})
})
//custom error handler
app.use((err,req,res,next)=>{
    const {status=501}=err;
    let {message="Server Error"}=err;
    if(err instanceof MongoServerSelectionError){
        message="Connection Timeout!!!, Please try after sometim"
    }
    if(err instanceof MongoServerError){
        message="Database Error, Please check your inputs"
    }
    if(err instanceof MongoTopologyClosedError){
        message="Can't Connect to Database at the moment!!"
    }
    if(err instanceof FirebaseError){
        message=err.code.slice(5)
    }
    console.log(err)
    return res.status(status).json({message})
})

//undefined route
app.all('*',(req,res)=>{
    return res.status(404).json({message:'Invalid Endpoint'})
})

connectTodb()
.then(()=>{
    app.listen(PORT)
})
module.exports=app