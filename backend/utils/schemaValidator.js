const {z}=require('zod');
const {generateErrorMessage}=require('zod-error')
const studentSignup= z.object({
    admno: z.string(),
    email: z.string().email(),
    password:z.string().min(6)
}).strict()

const professorSignup= z.object({
    username: z.string().min(6),
    email: z.string().email(),
    password:z.string().min(6)
}).strict()


const userLogin= z.object({
    email: z.string().email(),
    password: z.string().min(6)
}).strict()

module.exports= {studentSignup,professorSignup,userLogin}