const uuid=require('uuid').v4
const {getClient}=require('../db')
const client=getClient();
const {ObjectId}=require('mongodb')
const _db=client.db(process.env.DBNAME);
const {mybucket}=require('../utils/gcp')
const {ExpressError}=require('../utils/customErrorHandler')
const getCourses= async (req,res)=>{
    const profid=req.firebaseuserid

    const courses=await _db.collection('courses').aggregate([
        {
            $match:{profid:profid}
        },
        {
            $project: {
              profid:0
            }
        }
    ]).toArray()
    return res.status(200).json({"courses":courses})
    
}

//get the courses that student has registered in
const getMyCourses= async (req,res)=>{
    const id=req.firebaseuserid
    const courses=await _db.collection('courses').aggregate([
        {
            $match:{
                $expr:{
                    $in:[id,"$students"]
                }
            }
        }
    ]).toArray()
    return res.status(200).json({courses})
}

const createCourse = async (req,res)=>{
    //course code, name
    const {code,name}=req.body
    // const courseid=uuid()
    const profid=req.firebaseuserid
    // create course
    const course={
        code,
        name,
        materials:[],
        profid
    }
    //need to write transaction
    const insertedDoc=await _db.collection('courses').insertOne(course);
    const course_id=insertedDoc.insertedId
    await _db.collection('users').updateOne({userid:profid},
        {$push:{courses: course_id}}
    )
    return res.status(200).json({"message":"Course Created"})
}

const deleteCourse= async (req,res)=>{
    let id=new ObjectId(req.body._id)
    const profid=req.firebaseuserid
    await _db.collection('courses').deleteOne({_id:id})
    await _db.collection('professors').updateOne({userid:profid},
        {$pull :{courses: id}}
    )
    //we need to delete the registrations of students for this course
    //from the registrations collection
    return res.status(200).json({"message":"course deleted"})

}

const uploadMaterials = async (req,res)=>{
    const courseid=new ObjectId(req.body.courseid)
    if(!req.file){
        throw new ExpressError("Missing material to attach to the course",501);
    }
    const options={
        destination:req.file.filename, //name of the file with which we want our uploaded file to store with- basically the name of the file in bucket
        preconditionOpts:{ifGenerationMatch:0}
    }
    const output=await mybucket.upload(req.file.path,options);
    const publicURL=`https://storage.googleapis.com/${output[0].metadata.bucket}/${req.file.filename}`
    const newElement={
        url:publicURL,
        createdAt:new Date(),
    }
    await _db.collection('courses').updateOne({_id:courseid},{
        $push:{ materials: newElement}
    })
    return res.status(200).json({message:"File attatched"})
}

const uploadAssignments = async (req,res)=>{
    const courseid=new ObjectId(req.body.courseid)
    const text=req.body.description
    if(!req.file){
        throw new ExpressError("Missing assigment to attach to course",501);
    }
    const options={
        destination:req.file.filename, //name of the file with which we want our uploaded file to store with- basically the name of the file in bucket
        preconditionOpts:{ifGenerationMatch:0}
    }
    const output=await mybucket.upload(req.file.path,options);
    const publicURL=`https://storage.googleapis.com/${output[0].metadata.bucket}/${req.file.filename}`
    const newElement={
        url:publicURL,
        text,
        courseid,
        createdAt:new Date()
    }
    await _db.collection('assignments').insertOne(newElement);
    return res.status(200).json({message:"Assignment uploaded"})
}

const getAnnouncements = async (req,res)=>{
    const courseid = new ObjectId(req.body.courseid);
    const announcements=await _db.collection('announcements').find({courseid:courseid}).toArray()
    return res.status(200).json({announcements:announcements})
}
const UploadAnnouncement = async (req,res) =>{
    const courseid = new ObjectId(req.body.courseid);
    const text=req.body.description
    const obj = {
        courseid,createdAt:new Date(),text
    };
    await _db.collection('announcements').insertOne(obj);
    return res.status(200).json({message:"Announcement Posted"});
}
const DeleteAnnouncement = async (req,res)=>{
    const announcementId = new ObjectId(req.body.announcementid);
    console.log(announcementId);
    await _db.collection('announcements').deleteOne({_id:announcementId});
    return res.status(200).json({message:"Announcement Deleted"});
}

const submitAssignments= async (req,res)=>{
    const courseid=new ObjectId(req.body.courseid)
    const assignmentid=new ObjectId(req.body.assignmentid)
    if(!req.file){
        throw new ExpressError("Missing file to submit",501);
    }
    const options={
        destination:req.file.filename, //name of the file with which we want our uploaded file to store with- basically the name of the file in bucket
        preconditionOpts:{ifGenerationMatch:0}
    }
    const output=await mybucket.upload(req.file.path,options);
    const publicURL=`https://storage.googleapis.com/${output[0].metadata.bucket}/${req.file.filename}`
    const newElement={
        url:publicURL,
        assignmentid,
        courseid,
        createdAt:new Date()
    }
    await _db.collection('submissions').insertOne(newElement);
    return res.status(200).json({message:"Solution Submitted, file uploaded"})


}


const gradeAssignment= async (req,res)=>{
    const submissionid=new ObjectId(req.body.submissionid)
    const grade=req.body.grade
    const feedback = req.body.feedback
    await _db.collection('submissions').updateOne({_id:submissionid},
    {
        $set:{grade:grade,feedback:feedback}
    })
    return res.status(200).json({"message":"Assignment graded"})
}

const getAllSubmissions = async (req,res) =>{
    const assignmentid = new ObjectId(req.body.assignmentid);
    const submissions = await _db.collection('submissions').aggregate([{$match:{assignmentid:assignmentid}}]
    ).toArray();
    return res.status(200).json({content:submissions});
}

const getAllAssignments = async (req,res) =>{
    const courseid = new ObjectId(req.body.courseid);
    const assignments = await _db.collection('assignments').aggregate([
        {
            $match:{courseid:courseid}
        }
    ]).toArray();
    return res.status(200).json({assignments:assignments});
}
const getAllMaterials = async (req,res) =>{
    const courseid  = new ObjectId(req.body.courseid);
    const courseDetails = await _db.collection('courses').findOne({_id:courseid});
     return res.status(200).json({materials:courseDetails.materials});
}
const Comments = async (req,res) =>{
    const sourceid = new ObjectId(req.body.sourceid);
    // which source type is comment e.g : whether the comment is for assignment, announcement?
    const sourcetype = req.body.sourcetype;
    // source id is for which material or which assignment the comment was made for
    // publisher_id is who commented
    const text=req.body.comment
    const comment = {
        publisher_id : req.firebaseuserid,
        text,
        modified:new Date(),
        sourceid,
        sourcetype
    }
    await _db.collection('comments').insertOne(comment);
    return res.status(200).json({message:"Successfully Commented"});
}
const DeleteComments = async (req,res) =>{
    const commentid = new ObjectId(req.body.commentid);
    await _db.collection('comments').deleteOne({_id:commentid});
    return res.status(200).json({message:"Comment Deleted Successfully"});
}
const UpdateComments = async (req,res) =>{
        const commentid = new ObjectId(req.body.commentid);
        const text = req.body.comment;
        await _db.collection('comments').updateOne({_id:commentid},
        {$set:{text:text,modified:new Date()}}
        );
        
        
        return res.status(200).json({message:"Comment Updated Successfully"});
}

const getComments=async (req,res)=>{
    const sourceid=new ObjectId(req.body.sourceid)
    const sourcetype=req.body.sourcetype

    const comments=await _db.collection('comments').find({sourceid:sourceid,sourcetype:sourcetype}).toArray()
    return res.status(200).json({"comments":comments})
}

const registerStudents= async (req,res)=>{
    const courseid=new ObjectId(req.body.courseid)
    const studentMails=req.body.mails
    console.log(studentMails)
    let userids=[]
    for(mail of studentMails){
        const studentObject = await _db.collection('students').findOne({email:mail});
        const userid=studentObject.userid;
        userids.push(userid);
    }
    await _db.collection('courses').updateOne({_id:courseid},{
        $push:{ students: { $each: userids}}
    },{upsert:true})
    return res.status(200).json({"message":"All the students are registered"})
}

module.exports={createCourse,UploadAnnouncement,DeleteAnnouncement,getCourses,deleteCourse,DeleteComments,
    uploadMaterials,uploadAssignments,submitAssignments,gradeAssignment,Comments,getAllAssignments,getAllSubmissions,
    getAllMaterials,UpdateComments,registerStudents,getComments,getMyCourses,getAnnouncements}