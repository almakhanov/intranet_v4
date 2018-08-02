package kz.batana.intranet_v4.ui.admin_page


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_admin_main_student_list.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student


class AdminMainStudentListFragment : Fragment(), AdminMainUserListAdapter.OnItemClickListener{

    private var listener: AdminMainMVP.StudentListFragmentListener? = null

    private lateinit var studentListAdapter: AdminMainUserListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = AdminMainStudentListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_admin_main_student_list, container, false)
        listener?.getStudentsList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AdminMainMVP.StudentListFragmentListener) {
            listener = context as AdminMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement StudentListFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun putStudentsListIntoRecyclerView(studentsList: ArrayList<Any>) {
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_student_list?.layoutManager = layout
        studentListAdapter = AdminMainUserListAdapter(studentsList, this)
        recycler_view_student_list?.adapter = studentListAdapter
        studentListAdapter.notifyDataSetChanged()

        log("arr list size = ${studentsList.size}")
    }

    override fun onItemClicked(user: Any) {
        log("#1=a->"+user.toString())
        var clickedStudent = user as Student
        log("#2=a->"+clickedStudent.toString())
    }

}
