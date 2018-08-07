package kz.batana.intranet_v4.ui.teacher_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Student

interface TeacherMainMVP {
    interface View{
        fun sendStudentData(studentsList: Student, studentsIdList: String)
        fun msg(message: String)
    }

    interface Presenter{
        fun sendStudentData(student: Student, studentId: String)
        fun getCourseStudentsListForFragment(courseID: String)
        fun putMark(markValue: String, studentId: String, courseId: String)
        fun onSuccessMessage(message: String)
    }

    interface Interactor {
        fun getCourseStudentsList(courseId: String)
        fun putMark(markValue: Int, studentId: String, courseId: String)
    }

    interface TeacherProfileFragmentListener{
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