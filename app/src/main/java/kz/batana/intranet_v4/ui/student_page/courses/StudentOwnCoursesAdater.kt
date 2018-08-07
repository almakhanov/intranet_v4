package kz.batana.intranet_v4.ui.student_page.courses

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course

class StudentOwnCoursesAdater(private var datasetAverageMark: ArrayList<Int>,
                              private var datasetCourse: ArrayList<Course>,
                              private var courseIdList: ArrayList<String>,
                              private val listener: OnItemClickListener)
    : RecyclerView.Adapter<StudentOwnCoursesAdater.CoursesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student_transcrit_card_view, parent, false)
        return CoursesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datasetCourse.size
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(datasetCourse[position], datasetAverageMark[position])
    }


    inner class CoursesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(course: Course, averageMarkValue: Int){
            val courseName = itemView.findViewById<TextView>(R.id.text_view_card_view_transcript_course_name)
            val markValue = itemView.findViewById<TextView>(R.id.text_view_card_view_transcript_course_score)
            courseName.text = course.name
            markValue.text = averageMarkValue.toString()
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var pos : Int = adapterPosition
            var course : Course = datasetCourse[pos]
            var mark : Int = datasetAverageMark[pos]
            var courseId = courseIdList[pos]
            App.log("clicked course=$course")

            listener.onCourseClicked(course, mark, courseId)

        }

    }

    interface OnItemClickListener {
        fun onCourseClicked(course: Course, markValue: Int, courseId: String)
    }
}