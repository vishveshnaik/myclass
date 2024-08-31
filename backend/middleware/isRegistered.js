const jwt=require('jsonwebtoken')
const dotenv=require('dotenv')
dotenv.config();
const key=process.env.SECRETKEY
const {getClient}=require('../db')
const client=getClient();
const _db=client.db(process.env.DBNAME);
const {ObjectId}=require('mongodb')
const isRegistered= async (req,res,next)=>{
    try{
        const courseid=new ObjectId(req.body.courseid);
        const registered=await _db.collection('courses').aggregate([
            {
                $match:{_id:courseid, 
                    $expr:{
                        $in:[courseid,"$students"]
                    }
                }
            }
        ]).toArray()
        if(registered.length===0){
            return res.status(501).json({"message":"You are not registered for this course"})
        }
        next()
    }
    catch(err){
        return res.status(501).json({message:"server side error"})
    }
}

module.exports={isStudent}