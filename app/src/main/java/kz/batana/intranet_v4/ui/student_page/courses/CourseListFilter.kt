package kz.batana.intranet_v4.ui.student_page.courses

import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

class CourseListFilter(var courses: ArrayList<Course>) {

    fun findByYear(year: String): ArrayList<Int>{
        var newArrList: ArrayList<Int> = ArrayList()
        for((index, it) in courses.withIndex()){
            if(it.year.toString().contains(year)){
                newArrList.add(index)
            }
        }
        log(newArrList.toString())
        return newArrList
    }

    fun getNewCourses(inds: ArrayList<Int>, courses: ArrayList<Course>):  ArrayList<Course>{
        var newArrList: ArrayList<Course> = ArrayList()
        for(it in inds){
            newArrList.add(courses[it])
        }
        return newArrList
    }

    fun getNewCoursesIds(inds: ArrayList<Int>, courseIds: ArrayList<String>):  ArrayList<String>{
        var newArrList: ArrayList<String> = ArrayList()
        for(it in inds){
            newArrList.add(courseIds[it])
        }
        return newArrList
    }

    fun getNewTeachers(inds: ArrayList<Int>, teachers: ArrayList<Teacher>):  ArrayList<Teacher>{
        var newArrList: ArrayList<Teacher> = ArrayList()
        for(it in inds){
            newArrList.add(teachers[it])
        }
        return newArrList
    }
}