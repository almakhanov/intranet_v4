package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

interface StudentMainMVP {
    interface View{
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher)
    }
    interface Presenter{
        fun getCourseListWithTeachers()
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher)
    }
    interface Interactor{
        fun getCourseListWithTeachers()
    }
    interface StudentProfileFragmentListener{}

    interface StudentCoursesAllFragementListener {
        fun getCourseListWithTeachers()
    }
}