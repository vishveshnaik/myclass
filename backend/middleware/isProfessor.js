const jwt=require('jsonwebtoken')
const dotenv=require('dotenv')
dotenv.config();
const key=process.env.SECRETKEY


const isProf= (req,res,next)=>{
    let token=req.headers.authorization;
    try{
        if(token){
            token=token.split(' ')[1];
            const user=jwt.verify(token,key);
            if(req.role==="professor"){
                next()
            }
            else{
                return res.status(501).json({message:"Unauthorized Action, incompatible role"});    
            }
        }
        else{
            return res.status(501).json({message:"Unauthorized Action, please signin"});
        }
    }
    catch(error){
        return res.status(501).json({message:"Unauthorized Action,err"});
        
    }
}

module.exports={isProf}