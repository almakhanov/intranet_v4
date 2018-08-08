package kz.batana.intranet_v4.ui.student_page.courses

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

class StudentCoursesAdapter(private var Courses: ArrayList<Course>,
                            private var Teachers: ArrayList<Teacher>,
                            private var courseIds: ArrayList<String>,
                            private val listener: OnItemClickListener)
    : RecyclerView.Adapter<StudentCoursesAdapter.CoursesViewHolder>() {


    private var datasetCourses: ArrayList<Course> = Courses
    private var datasetTeachers: ArrayList<Teacher> = Teachers
    private var courseIdList: ArrayList<String> = courseIds

    private var dataFilter = CourseListFilter(datasetCourses)
    private var indexes: ArrayList<Int> = ArrayList()

    fun filter(year: String){
        indexes = dataFilter.findByYear(year)
        datasetCourses = dataFilter.getNewCourses(indexes, Courses)
        datasetTeachers = dataFilter.getNewTeachers(indexes, Teachers)
        courseIdList = dataFilter.getNewCoursesIds(indexes, courseIds)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses_card_with_teacher, parent, false)
        return CoursesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datasetCourses.size
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(datasetCourses[position], datasetTeachers[position])
    }


    inner class CoursesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(course: Course, teacher: Teacher){
            val courseName = itemView.findViewById<TextView>(R.id.text_view_course_card_view_course_name)
            val teacherName = itemView.findViewById<TextView>(R.id.text_view_course_card_view_teacher_name)
            val year = itemView.findViewById<TextView>(R.id.text_view_course_card_view_year_name)
            courseName.text = course.name
            teacherName.text = teacher.name
            year.text = course.year.toString()
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var pos : Int = adapterPosition
            var course : Course = datasetCourses[pos]
            var teacher : Teacher = datasetTeachers[pos]
            var courseId = courseIdList[pos]
            log("clicked course=$course\nteacher=$teacher")

            listener.onCourseClicked(course, teacher, courseId)

        }

    }

    interface OnItemClickListener {
        fun onCourseClicked(course: Course, teacher: Teacher, courseId: String)
    }
}