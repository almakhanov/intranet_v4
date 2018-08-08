package kz.batana.intranet_v4.ui.teacher_page.courses


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_teacher_courses.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainMVP
import kz.batana.intranet_v4.ui.teacher_page.courses.course_add.CourseAddActivity


class TeacherCoursesFragment : Fragment(), TeacherCoursesMVP.View, TeacherCourseAdapter.OnItemClickListener {


    private val presenter : TeacherCoursesMVP.Presenter by lazy{ TeacherCoursesPresenter(this) }
    private var listener: TeacherMainMVP.TeacherCoursesFragmentListener? = null
    private lateinit var teacherListAdapter: TeacherCourseAdapter

    companion object {
        @JvmStatic
        fun newInstance() = TeacherCoursesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_courses, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        loadOn()
        presenter.getCourseList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeacherMainMVP.TeacherCoursesFragmentListener) {
            listener = context as TeacherMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherCoursesFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()

        //fab click
        fab_teacher_courses_list_add.setOnClickListener{
            startActivity(Intent(activity,CourseAddActivity::class.java))
        }
    }

    override fun putCoursesListIntoRecyclerView(courseList: ArrayList<Course>, idList: ArrayList<String>) {
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_teacher_courses_list?.layoutManager = layout
        teacherListAdapter = TeacherCourseAdapter(courseList, idList, this)
        recycler_view_teacher_courses_list?.adapter = teacherListAdapter
        teacherListAdapter.notifyDataSetChanged()

        log("arr list size = ${courseList.size}")
        loadOff()
    }

    override fun onItemClicked(course: Course, courseId: String) {
        listener?.openCourseStudentsList(course, courseId)
    }

    private fun loadOn(){
        progress_bar_teacher_courses.visibility = ProgressBar.VISIBLE
        recycler_view_teacher_courses_list.visibility = RecyclerView.GONE
    }

    private fun loadOff(){
        progress_bar_teacher_courses.visibility = ProgressBar.GONE
        recycler_view_teacher_courses_list.visibility = RecyclerView.VISIBLE
    }




}
