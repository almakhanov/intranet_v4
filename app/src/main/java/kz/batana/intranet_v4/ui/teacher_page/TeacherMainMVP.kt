package kz.batana.intranet_v4.ui.teacher_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

interface TeacherMainMVP {
    interface View{
        fun msg(message: String)
        fun sendTeacherInfo(teacher: Teacher, email: String)
        fun sendStudentData(studentIds: ArrayList<String>, courseId: String, studentsList: ArrayList<Student>, markList: ArrayList<Int>)
    }

    interface Presenter{
        fun getCourseStudentsListForFragment(courseID: String)
        fun putMark(markValue: String, studentId: String, courseId: String)
        fun onSuccessMessage(message: String)
        fun getTeacherProfile()
        fun sendTeacherInfo(teacher: Teacher, email: String)
        fun sendStudentData(studentIds: ArrayList<String>, courseId: String, studentsList: ArrayList<Student>, markList: ArrayList<Int>)
    }

    interface Interactor {
        fun getCourseStudentsList(courseId: String)
        fun putMark(markValue: Int, studentId: String, courseId: String)
        fun getTeacherProfile()
    }

    interface TeacherProfileFragmentListener{
        fun getTeacherProfile()
    }

    interface TeacherCoursesFragmentListener {
        fun openCourseStudentsList(course: Course, courseId: String)
    }

    interface CourseStudentsFragementListener {
        fun getCourseStudentsList(courseID: String)
        fun applyToolbarTitle(title: String)
        fun putMark(markValue: String, studentId: String, courseId: String)
    }
}