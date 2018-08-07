package kz.batana.intranet_v4.ui.teacher_page.courses

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course


class TeacherCourseAdapter(private var dataset: ArrayList<Course>,
                           private var datasetId: ArrayList<String>,
                           private val listener: OnItemClickListener)
    : RecyclerView.Adapter<TeacherCourseAdapter.CoursesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses_card, parent, false)
        return CoursesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(dataset[position])
    }


    inner class CoursesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(course: Course){
            val courseName = itemView.findViewById<TextView>(R.id.text_view_course_card_view_name)
            courseName.text = course.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var pos : Int = adapterPosition
            var course : Course = dataset[pos]
            log("clicked course=$course")

            listener.onItemClicked(course, datasetId[pos])

        }

    }

    interface OnItemClickListener {
        fun onItemClicked(course: Course, courseId: String)
    }
}
