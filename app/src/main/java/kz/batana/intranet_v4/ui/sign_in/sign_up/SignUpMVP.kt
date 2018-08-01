package kz.batana.intranet_v4.ui.sign_in.sign_up

import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.data.Entities.User

interface SignUpMVP {

    interface View{
        fun msg(message: String)
        fun onSuccess()
        fun getCurrentSignUpper() : String
    }

    interface StudentFragmentListener{
        fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                             confirmPassword: String)
    }

    interface TeacherFragmentListener{
        fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String,
                             confirmPassword: String, secretCode: String)

    }

    interface AdminFragmentListener{
        fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String,
                           secretCode: String)

    }

    interface Presenter{
        fun onSuccess()
        fun onFailed(exception: Exception)
        fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String,
                           secretCode: String)
        fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                             confirmPassword: String)
        fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String,
                             confirmPassword: String, secretCode: String)
    }

    interface Interactor {
        fun saveNewStudent(student: Student)
        fun registerUserIntoFirebaseAuth(user: User)
        fun saveNewAdmin(admin: Admin)
        fun saveNewTeacher(teacher: Teacher)
    }
}