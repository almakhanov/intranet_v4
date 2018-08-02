package kz.batana.intranet_v4.ui.admin_page

import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class AdminMainPresenter(private val view: AdminMainMVP.View) : AdminMainMVP.Presenter {

    private val interactor = AdminMainInteractor(this)

    override fun getStudentsList() {
        interactor.getStudentsList()
    }


    override fun receivedStudentList(studentsList: ArrayList<Student>) {
        var studentsAnyList = ArrayList<Any>()
        studentsAnyList.addAll(studentsList)
        view.receivedStudentList(studentsAnyList)
    }

    override fun getTeachersList() {
        interactor.getTeachersList()
    }

    override fun receivedTeacherList(teachersList: ArrayList<Teacher>) {
        var teachersAnyList = ArrayList<Any>()
        teachersAnyList.addAll(teachersList)
        view.receivedTeacherList(teachersAnyList)
    }

    override fun getAdminList() {
        interactor.getAdminsList()
    }

    override fun receivedAdminList(adminsList: ArrayList<Admin>) {
        var adminsAnyList = ArrayList<Any>()
        adminsAnyList.addAll(adminsList)
        view.receivedAdminList(adminsAnyList)
    }
}