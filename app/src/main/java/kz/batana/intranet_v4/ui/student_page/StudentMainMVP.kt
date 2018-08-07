package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

interface StudentMainMVP {
    interface View{
        fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>)
        fun msg(message: String)
        fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>)
        fun sendTranscriptData(markList: ArrayList<Int>, ownCourseList: ArrayList<Course>, courseIds: ArrayList<String>)
        fun sendStudentInfo(student: Student, email: String, gpa: String)
        fun sendStudentData(student: Student)

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
        fun getStudentData()
        fun getProfileInfo()
        fun sendProfileInfo(student: Student, email: String, studentId: String, studentCourses: ArrayList<Course>, studentCoursesIds: ArrayList<String>, studentMarkList: ArrayList<Int>)
        fun sendStudentData(student: Student)
    }
    interface Interactor{
        fun getCourseListWithTeachers()
        fun saveCourse(course: Course, courseId: String)
        fun getCourseOwnList()
        fun getTranscriptData()
        fun getStudentData()
        fun getProfileInfo()
    }
    interface StudentProfileFragmentListener{
        fun getProfileInfo()
    }

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