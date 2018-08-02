package kz.batana.intranet_v4.ui.admin_page


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_admin_main_teacher_list.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Teacher

class AdminMainTeacherListFragment : Fragment(), AdminMainUserListAdapter.OnItemClickListener {

    private var listener: AdminMainMVP.TeacherListFragmentListener? = null

    private lateinit var teacherListAdapter: AdminMainUserListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = AdminMainTeacherListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_main_teacher_list, container, false)
        listener?.getTeachersList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AdminMainMVP.TeacherListFragmentListener) {
            listener = context as AdminMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun putTeachersListIntoRecyclerView(teachersList: ArrayList<Any>) {
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_teacher_list?.layoutManager = layout
        teacherListAdapter = AdminMainUserListAdapter(teachersList, this)
        recycler_view_teacher_list?.adapter = teacherListAdapter
        teacherListAdapter.notifyDataSetChanged()

        log("arr list size = ${teachersList.size}")
    }

    override fun onItemClicked(user: Any) {
        log("#1=a->"+user.toString())
        var clickedTeacher = user as Teacher
        log("#2=a->"+clickedTeacher.toString())
    }


}
