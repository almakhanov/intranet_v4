package kz.batana.intranet_v4.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_all_users_adapter_header.view.*
import kotlinx.android.synthetic.main.item_all_users_adapter_students.view.*
import kotlinx.android.synthetic.main.item_all_users_adapter_teachers.view.*
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class ListAdapter(private var dataset: ArrayList<Any>,
                      private val listener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemViewType(position: Int): Int = when(dataset[position]){
        is Student -> HolderTypes.STUDENTS
        is Teacher -> HolderTypes.TEACHERS
        else -> {
            HolderTypes.HEADER
        }
    }

    object HolderTypes{
        const val STUDENTS = 0
        const val TEACHERS = 1
        const val HEADER = 2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = run {
        when(viewType){
            HolderTypes.STUDENTS -> StudentListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_all_users_adapter_students, parent, false))

            HolderTypes.TEACHERS -> TeacherListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_all_users_adapter_teachers, parent, false))

            else->{
                HeaderViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_all_users_adapter_header, parent, false))
            }

        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is StudentListViewHolder ->{
                val obj = dataset[position] as Student
                holder.itemView.studentFirstNameCardViewTextView.text = obj.name
                holder.itemView.setOnClickListener{
                    listener.onStudentClicked(obj)
                }
            }
            is TeacherListViewHolder ->{
                val obj = dataset[position] as Teacher
                holder.itemView.teacherFirstNameCardViewTextView.text = obj.name
                holder.itemView.setOnClickListener{
                    listener.onTeacherClicked(obj)
                }
            }
            is HeaderViewHolder ->{
                val obj = dataset[position] as String
                holder.itemView.headerCardViewTextView.text = obj
                holder.itemView.setOnClickListener{
                    listener.onHeaderClicked(obj)
                }
            }
        }
    }

    inner class StudentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class TeacherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    interface OnItemClickListener{
        fun onHeaderClicked(h: String)
        fun onStudentClicked(s: Student)
        fun onTeacherClicked(t: Teacher)
    }


}