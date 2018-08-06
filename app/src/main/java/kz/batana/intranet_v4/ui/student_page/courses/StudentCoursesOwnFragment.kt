package kz.batana.intranet_v4.ui.student_page.courses


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kz.batana.intranet_v4.R


class StudentCoursesOwnFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = StudentCoursesOwnFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_student_courses_own, container, false)
    }


}
