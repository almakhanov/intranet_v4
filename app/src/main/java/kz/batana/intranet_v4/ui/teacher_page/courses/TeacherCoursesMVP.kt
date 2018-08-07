package kz.batana.intranet_v4.ui.teacher_page.courses

import kz.batana.intranet_v4.data.Entities.Course

interface TeacherCoursesMVP {

    interface View{
        fun putCoursesListIntoRecyclerView(courseList: ArrayList<Course>, idList: ArrayList<String>)
    }

    interface Presenter{
        fun getCourseList()
        fun receivedStudentList(courseList: ArrayList<Course>, idList: ArrayList<String>)
    }

    interface Interactor{
        fun getCourseList()
    }

}