package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

interface StudentMainMVP {
    interface View{
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>)
        fun msg(message: String)
        fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>)

    }
    interface Presenter{
        fun getCourseListWithTeachers()
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>)
        fun saveCourse(course: Course, teacher: Teacher, courseId: String)
        fun msg(message: String)
        fun getCourseOwnList()
        fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>)
    }
    interface Interactor{
        fun getCourseListWithTeachers()
        fun saveCourse(course: Course, courseId: String)
        fun getCourseOwnList()
    }
    interface StudentProfileFragmentListener{}

    interface StudentCoursesAllFragementListener {
        fun getCourseListWithTeachers()
        fun msg(message: String)
        fun saveCourse(course: Course, teacher: Teacher, courseId: String)
        fun getCourseOwnList()
    }

    interface StudentCoursesOwnFragementListener {
        fun getCourseOwnList()
    }
}