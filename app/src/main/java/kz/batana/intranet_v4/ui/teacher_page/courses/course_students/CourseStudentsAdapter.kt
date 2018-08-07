package kz.batana.intranet_v4.ui.teacher_page.courses.course_students

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student

class CourseStudentsAdapter(private var dataset: ArrayList<Student>,
                            private var datasetId: ArrayList<String>,
                            private val listener: OnItemClickListener)
    : RecyclerView.Adapter<CourseStudentsAdapter.StudentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses_card, parent, false)
        return StudentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
        holder.bind(dataset[position])
    }


    inner class StudentsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(student: Student){
            val studentName = itemView.findViewById<TextView>(R.id.text_view_course_card_view_name)
            studentName.text = student.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var pos : Int = adapterPosition
            var student : Student= dataset[pos]
            App.log("clicked course=$student")

            listener.onItemClicked(student, datasetId[pos])

        }

    }

    interface OnItemClickListener {
        fun onItemClicked(student: Student, studentId: String)
    }
}