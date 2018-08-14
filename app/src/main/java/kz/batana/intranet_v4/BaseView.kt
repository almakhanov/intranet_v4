package kz.batana.intranet_v4

interface BaseView<out P : BasePresenter<*>> {
    val presenter: P

    fun showLoading(){}
    fun hideLoading(){}
}