package kz.batana.intranet_v4.ui.teacher_page.courses.course_students


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
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_course_students.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainMVP
import org.jetbrains.anko.support.v4.toast

const val ARG_PARAM = "new_instance"

class CourseStudentsFragment : Fragment(), CourseStudentsAdapter.OnItemClickListener {


    private var listener: TeacherMainMVP.CourseStudentsFragementListener? = null
    private lateinit var studentListAdapter: CourseStudentsAdapter
    private lateinit var studentsList: ArrayList<Student>
    private lateinit var studentIdList: ArrayList<String>
    lateinit var courseID: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseID = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_course_students, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        loadOn()
        studentIdList = ArrayList()
        studentsList = ArrayList()
        listener?.getCourseStudentsList(courseID)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeacherMainMVP.CourseStudentsFragementListener) {
            listener = context as TeacherMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement CourseStudentsFragementListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener?.applyToolbarTitle("Courses")
        listener = null
    }

    fun putCoursesListIntoRecyclerView(studentIds: ArrayList<String>, courseId: String, studentsList: ArrayList<Student>, markList: ArrayList<Int>){
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_course_students?.layoutManager = layout
        studentListAdapter = CourseStudentsAdapter(studentsList, studentIds, markList, this)
        recycler_view_course_students?.adapter = studentListAdapter
        studentListAdapter.notifyDataSetChanged()

        App.log("CourseStudentsAdapter list size = ${studentsList.size}")
        loadOff()
    }



    override fun onItemClicked(student: Student, studentId: String) {
        var dialog: AlertDialog
        val viewDialog: View = layoutInflater.inflate(R.layout.custom_put_mark_dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(viewDialog)
        val dialogEditText = viewDialog.findViewById<View>(R.id.edit_text_custom_dialog_put_mark) as EditText
        builder.setTitle("Evaluation dialog")
        builder.setMessage("Put mark to ${student.name}")
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    loadOn()
                    listener?.putMark(dialogEditText.text.toString(), studentId, courseID)
                    listener?.getCourseStudentsList(courseID)
                }
                DialogInterface.BUTTON_NEGATIVE -> toast("Choice canceled!")
            }
        }

        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }


    companion object {
        @JvmStatic
        fun newInstance(courseId: String) =
                CourseStudentsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM, courseId)
                    }
                }
    }

    private fun loadOn(){
        progress_bar_teacher_course_students.visibility = ProgressBar.VISIBLE
        recycler_view_course_students.visibility = RecyclerView.GONE
    }

    private fun loadOff(){
        progress_bar_teacher_course_students.visibility = ProgressBar.GONE
        recycler_view_course_students.visibility = RecyclerView.VISIBLE
    }




}
