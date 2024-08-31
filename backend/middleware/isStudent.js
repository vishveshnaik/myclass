const jwt=require('jsonwebtoken')
const dotenv=require('dotenv')
dotenv.config();
const key=process.env.SECRETKEY


const isStudent= (req,res,next)=>{
    let token=req.headers.authorization;
    try{
        if(token){
            token=token.split(' ')[1];
            const user=jwt.verify(token,key);
            if(req.role==="student"){
                next()
            }
            else{
                return res.status(501).json({message:"Unauthorized Action"});    
            }
        }
        else{
            return res.status(501).json({message:"Unauthorized Action"});
        }
    }
    catch(error){
        return res.status(501).json({message:"Unauthorized Action"});
        
    }
}

module.exports={isStudent}