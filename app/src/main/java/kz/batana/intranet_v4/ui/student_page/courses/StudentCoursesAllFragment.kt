package kz.batana.intranet_v4.ui.student_page.courses


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_student_courses_all.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainMVP


class StudentCoursesAllFragment : Fragment(), StudentCoursesAdapter.OnItemClickListener {

    private var listener: StudentMainMVP.StudentCoursesAllFragementListener? = null
    private lateinit var courseListAdapter: StudentCoursesAdapter
    private lateinit var courseList: ArrayList<Course>
    private lateinit var teacherList: ArrayList<Teacher>

    companion object {
        @JvmStatic
        fun newInstance() = StudentCoursesAllFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        courseList = ArrayList()
        teacherList = ArrayList()
        listener?.getCourseListWithTeachers()

        return inflater.inflate(R.layout.fragment_student_courses_all, container, false)
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

    override fun onCourseClicked(course: Course, teacher: Teacher) {
        log("~~~Clicked~~~")
        log("~~~$course~~~")
        log("~~~$teacher~~~")
    }

    fun putCoursesListIntoRecyclerView(list: ArrayList<Course>, teacher: Teacher){
        for(it in list){
            courseList.add(it)
            teacherList.add(teacher)
        }

        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_student_courses_all_list?.layoutManager = layout
        courseListAdapter = StudentCoursesAdapter(courseList, teacherList, this)
        recycler_view_student_courses_all_list?.adapter = courseListAdapter
        courseListAdapter.notifyDataSetChanged()

        log("arr list size = ${courseList.size}")
    }


}
