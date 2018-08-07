package kz.batana.intranet_v4.ui.student_page.courses


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_student_courses_own.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainMVP
import kz.batana.intranet_v4.ui.teacher_page.courses.TeacherCourseAdapter


class StudentCoursesOwnFragment : Fragment(), TeacherCourseAdapter.OnItemClickListener {

    private var listener: StudentMainMVP.StudentCoursesOwnFragementListener? = null
    private lateinit var courseListAdapter: TeacherCourseAdapter
    private lateinit var courseList: ArrayList<Course>

    companion object {
        @JvmStatic
        fun newInstance() = StudentCoursesOwnFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_courses_own, container, false)
        courseList = ArrayList()
        listener?.getCourseOwnList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is StudentMainMVP.StudentCoursesAllFragementListener) {
            listener = context as StudentMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement StudentCoursesAllFragementListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onItemClicked(course: Course, courseId: String) {
        log(course.toString())
    }

    fun putCoursesListIntoRecyclerView(list: ArrayList<Course>, listId: ArrayList<String>){
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_student_courses_own_list?.layoutManager = layout
        courseListAdapter = TeacherCourseAdapter(list, listId, this)
        recycler_view_student_courses_own_list?.adapter = courseListAdapter
        courseListAdapter.notifyDataSetChanged()

        log("arr list size = ${list.size}")
    }

}
