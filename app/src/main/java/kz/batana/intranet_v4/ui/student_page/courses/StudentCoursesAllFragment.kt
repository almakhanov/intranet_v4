package kz.batana.intranet_v4.ui.student_page.courses


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
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
    private lateinit var courseIdList: ArrayList<String>

    companion object {
        @JvmStatic
        fun newInstance() = StudentCoursesAllFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


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

    override fun onStart() {
        super.onStart()
        loadOn()
        courseList = ArrayList()
        teacherList = ArrayList()
        courseIdList = ArrayList()
        listener?.getCourseListWithTeachers()
    }

    override fun onResume() {
        super.onResume()
        button_student_courses_all_search.setOnClickListener{
            courseListAdapter.filter(edit_text_student_courses_all_search.text.toString())
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCourseClicked(course: Course, teacher: Teacher, courseId: String) {
        var dialog: AlertDialog
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("${course.name} by ${teacher.name}")
        builder.setMessage("Do you want to take course of ${course.name}?")
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    loadOn()
                    courseList = ArrayList()
                    teacherList = ArrayList()
                    courseIdList = ArrayList()
                    listener?.saveCourse(course, teacher, courseId)
                    listener?.getCourseListWithTeachers()
                }

                DialogInterface.BUTTON_NEGATIVE -> listener?.msg("Choice cancelled!")
            }
        }

        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

    fun putCoursesListIntoRecyclerView(list: ArrayList<Course>, teacher: Teacher, courseIds: ArrayList<String>){
        for(it in list){
            courseList.add(it)
            teacherList.add(teacher)
        }
        courseIdList.addAll(courseIds)

        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_student_courses_all_list?.layoutManager = layout
        courseListAdapter = StudentCoursesAdapter(courseList, teacherList, courseIdList, this)
        recycler_view_student_courses_all_list?.adapter = courseListAdapter
        courseListAdapter.notifyDataSetChanged()
        courseListAdapter.filter(edit_text_student_courses_all_search.text.toString())
        log("arr list size = ${courseList.size}")
        loadOff()
    }


    private fun loadOn(){
        progress_bar_student_courses_all.visibility = ProgressBar.VISIBLE
        recycler_view_student_courses_all_list.visibility = RecyclerView.GONE
    }

    private fun loadOff(){
        progress_bar_student_courses_all.visibility = ProgressBar.GONE
        recycler_view_student_courses_all_list.visibility = RecyclerView.VISIBLE
    }



}
