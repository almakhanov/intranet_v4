package kz.batana.intranet_v4.ui.admin_page

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_all_users_adapter_admins.view.*
import kotlinx.android.synthetic.main.item_all_users_adapter_students.view.*
import kotlinx.android.synthetic.main.item_all_users_adapter_teachers.view.*
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class AdminMainUserListAdapter(private var dataset: ArrayList<Any>,
                               private val listener: OnItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object HolderTypes {
        const val STUDENTS = 0
        const val TEACHERS = 1
        const val ADMINS = 2
    }

    override fun getItemViewType(position: Int): Int = when (dataset[position]) {
        is Student -> HolderTypes.STUDENTS
        is Teacher -> HolderTypes.TEACHERS
        else -> {
            HolderTypes.ADMINS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = run {
        when (viewType) {
            HolderTypes.STUDENTS -> StudentListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_all_users_adapter_students, parent, false))
            HolderTypes.TEACHERS -> TeacherListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_all_users_adapter_teachers, parent, false))
            else -> AdminListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_all_users_adapter_admins, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StudentListViewHolder -> {
                val obj = dataset[position] as Student
                holder.itemView.text_view_student_card_view_name.text = obj.name
                holder.itemView.setOnClickListener {
                    listener.onItemClicked(obj)
                }
            }
            is TeacherListViewHolder -> {
                val obj = dataset[position] as Teacher
                holder.itemView.text_view_teacher_card_view_name.text = obj.name
                holder.itemView.setOnClickListener {
                    listener.onItemClicked(obj)
                }
            }
            is AdminListViewHolder -> {
                val obj = dataset[position] as Admin
                holder.itemView.text_view_admin_card_view_first_name.text = obj.name
                holder.itemView.setOnClickListener {
                    listener.onItemClicked(obj)
                }
            }
        }
    }


    inner class StudentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class TeacherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class AdminListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClicked(user: Any)
    }
}