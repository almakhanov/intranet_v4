package kz.batana.intranet_v4.ui.teacher_page.courses

import kz.batana.intranet_v4.data.Entities.Course

interface TeacherCoursesMVP {

    interface View{
        fun putCoursesListIntoRecyclerView(courseList: ArrayList<Course>)
    }

    interface Presenter{
        fun getCourseList()
        fun receivedStudentList(courseList: ArrayList<Course>)
    }

    interface Interactor{
        fun getCourseList()
    }

}