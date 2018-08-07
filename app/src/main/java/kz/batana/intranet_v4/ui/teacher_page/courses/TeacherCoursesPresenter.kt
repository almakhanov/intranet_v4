package kz.batana.intranet_v4.ui.teacher_page.courses

import kz.batana.intranet_v4.data.Entities.Course

class TeacherCoursesPresenter(private val view: TeacherCoursesMVP.View) : TeacherCoursesMVP.Presenter {

    private val interactor = TeacherCoursesInteractor(this)

    override fun getCourseList() {
        interactor.getCourseList()
    }

    override fun receivedStudentList(courseList: ArrayList<Course>, idList: ArrayList<String>) {
        view.putCoursesListIntoRecyclerView(courseList, idList)
    }

}