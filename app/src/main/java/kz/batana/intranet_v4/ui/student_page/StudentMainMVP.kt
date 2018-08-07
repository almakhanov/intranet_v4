package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Teacher

interface StudentMainMVP {
    interface View{
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>)
        fun msg(message: String)
        fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>)
        fun sendTranscriptData(markList: ArrayList<Int>, ownCourseList: ArrayList<Course>, courseIds: ArrayList<String>)

    }
    interface Presenter{
        fun getCourseListWithTeachers()
        fun saveCourse(course: Course, teacher: Teacher, courseId: String)
        fun msg(message: String)
        fun getCourseOwnList()
        fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>)
        fun getTranscriptData()
        fun sendTranscriptData(marks: ArrayList<Mark>, courseIds: ArrayList<String>, courseList: ArrayList<Course>, courseIdsList: ArrayList<String>)
        fun sendAllCourseTeacherData(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>, courseOwnList: ArrayList<Course>, courseOwnIdsList: ArrayList<String>)
    }
    interface Interactor{
        fun getCourseListWithTeachers()
        fun saveCourse(course: Course, courseId: String)
        fun getCourseOwnList()
        fun getTranscriptData()
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

    interface StudentTranscriptFragmentListener {
        fun getTranscriptData()
    }
}