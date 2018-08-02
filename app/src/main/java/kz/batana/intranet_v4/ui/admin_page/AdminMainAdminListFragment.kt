package kz.batana.intranet_v4.ui.admin_page


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_admin_main_admin_list.*
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Admin


class AdminMainAdminListFragment : Fragment(), AdminMainUserListAdapter.OnItemClickListener {

    private var listener: AdminMainMVP.AdminListFragmentListener? = null

    private lateinit var adminListAdapter: AdminMainUserListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = AdminMainAdminListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_main_admin_list, container, false)
        listener?.getAdminsList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AdminMainMVP.AdminListFragmentListener) {
            listener = context as AdminMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement AdminListFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun putAdminsListIntoRecyclerView(adminsList: ArrayList<Any>) {
        var layout = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_view_admin_list?.layoutManager = layout
        adminListAdapter = AdminMainUserListAdapter(adminsList, this)
        recycler_view_admin_list?.adapter = adminListAdapter
        adminListAdapter.notifyDataSetChanged()

        log("arr list size = ${adminsList.size}")
    }

    override fun onItemClicked(user: Any) {
        log("#1=a->"+user.toString())
        var clickedAdmin = user as Admin
        log("#2=a->"+clickedAdmin.toString())
    }


}
