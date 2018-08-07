package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class StudentMainPresenter(private val view: StudentMainMVP.View) : StudentMainMVP.Presenter {

    private val interactor = StudentMainInteractor(this)

    override fun getCourseListWithTeachers() {
        interactor.getCourseListWithTeachers()
    }


    override fun saveCourse(course: Course, teacher: Teacher, courseId: String) {
        interactor.saveCourse(course, courseId)
    }

    override fun msg(message: String) {
        view.msg(message)
    }

    override fun getCourseOwnList() {
        interactor.getCourseOwnList()
    }

    override fun getTranscriptData() {
        interactor.getTranscriptData()
    }

    override fun sendTranscriptData(marks: ArrayList<Mark>, courseIds: ArrayList<String>, courseList: ArrayList<Course>, courseIdsList: ArrayList<String>) {
        var ownCourseList: ArrayList<Course> = ArrayList()
        for(idIn in courseIds){
            for((index, idOut) in courseIdsList.withIndex()){
                if(idIn == idOut){
                    ownCourseList.add(courseList[index])
                }
            }
        }



        var markList: ArrayList<Int> = ArrayList()
        for(it in marks){
            markList.add(it.value)
        }
        var ownCourseIds: ArrayList<String> = ArrayList()
        ownCourseIds.addAll(courseIds)
        for((ind, idIn) in courseIdsList.withIndex()){
            var found = false
            for((index, idOut) in courseIds.withIndex()){
                if(idIn == idOut){
                    found = true

                }
            }
            if(!found){
                ownCourseList.add(courseList[ind])
                markList.add(0)
                ownCourseIds.add(idIn)
            }
        }


        log("Transcript :: ${markList.size}==${ownCourseList.size}==${ownCourseIds.size}")
        view.sendTranscriptData(markList, ownCourseList, ownCourseIds)
    }

    override fun sendCourseOwnList(list: ArrayList<Course>, listIds: ArrayList<String>) {
        view.sendCourseOwnList(list, listIds)
        log(list.toString())
    }

    override fun sendAllCourseTeacherData(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>, courseOwnList: ArrayList<Course>, courseOwnIdsList: ArrayList<String>) {
        var allList:ArrayList<Course> = ArrayList()
        var allIdList: ArrayList<String> = ArrayList()

        for((ind1, it1) in idList.withIndex()){
            var found = false
            for((int2, it2) in courseOwnIdsList.withIndex()) {
                if (it1 == it2) found = true
            }
            if(!found){
                allList.add(list[ind1])
                allIdList.add(idList[ind1])
            }
        }


        view.sendCoursesListAndTeacher(allList, teacher, allIdList)
    }

    override fun getStudentData() {
        interactor.getStudentData()
    }

    override fun sendStudentData(student: Student) {
        view.sendStudentData(student)
    }

    override fun getProfileInfo() {
        interactor.getProfileInfo()
    }

    override fun sendProfileInfo(student: Student, email: String, studentId: String, studentCourses: ArrayList<Course>,
                                 studentCoursesIds: ArrayList<String>, studentMarkList: ArrayList<Int>) {
        var sumOfCredits = 0
        for(it in studentCourses){
            sumOfCredits += it.credit
        }

        var sumOfPoints = 0.0
        for((ind, it) in studentCourses.withIndex()){
            sumOfPoints += it.credit * getScore(studentMarkList[ind])
        }

        var gpa: Double = sumOfPoints / sumOfCredits
        val gpaStr = "%.2f".format(gpa)

        view.sendStudentInfo(student, email, gpaStr)
    }

    private fun getScore(value: Int): Double{
        return when(value){
            in 95..100 -> 4.0
            in 90..95 -> 3.67
            in 85..90 -> 3.33
            in 80..85 -> 3.0
            in 75..80 -> 2.67
            in 70..75 -> 2.33
            in 65..70 -> 2.0
            in 60..65 -> 1.67
            in 55..60 -> 1.33
            in 50..55 -> 1.0
            else -> 0.0

        }
    }
}