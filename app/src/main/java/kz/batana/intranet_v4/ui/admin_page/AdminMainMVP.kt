package kz.batana.intranet_v4.ui.admin_page

import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

interface AdminMainMVP {

    interface View{
        fun receivedStudentList(studentsList: ArrayList<Any>)
        fun receivedTeacherList(teacherList: ArrayList<Any>)
        fun receivedAdminList(adminList: ArrayList<Any>)
    }

    interface StudentListFragmentListener{
        fun getStudentsList()
    }

    interface TeacherListFragmentListener{
        fun getTeachersList()
    }

    interface AdminListFragmentListener{
        fun getAdminsList()
    }

    interface Presenter{
        fun getStudentsList()
        fun receivedStudentList(studentsList: ArrayList<Student>)
        fun getTeachersList()
        fun receivedTeacherList(teachersList: ArrayList<Teacher>)
        fun getAdminList()
        fun receivedAdminList(adminsList: ArrayList<Admin>)
    }

    interface Interactor {
        fun getStudentsList()
        fun getTeachersList()
        fun getAdminsList()
    }
}