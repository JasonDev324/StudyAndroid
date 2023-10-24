package jasondev324.github.io

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener

/**
 * Developer:GDPCCoder
 * Date:2023/9/21
 * Content: 该类可能仅使用于点击无跳转的需求上下滑判断
 */
class RVRefshLoadMoreListener : OnTouchListener {
    var startY: Float = 0f
    var recyclerView: RecyclerView? = null
    var mListener: OnPullOnScrollListener? = null
    var mAdapter: RecyclerView.Adapter<*>? = null

    public fun bind(
        rv: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        listener: OnPullOnScrollListener?
    ) {
        recyclerView = rv
        mAdapter = adapter
        mListener = listener
        recyclerView?.setOnTouchListener(this)
        recyclerView?.addOnItemTouchListener(object :SimpleOnItemTouchListener(){
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) {
                    startY = e.y
                }
                return false
            }
        })

    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val lastPos =
                    (recyclerView?.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition();
                var total = mAdapter?.itemCount?.minus(1)

                val endY = event.y
                if (startY < endY && (lastPos == 0)) {
                    // 用户向下滑动
                    mListener?.onRefresh()
                } else if (startY > endY && (lastPos == total)) {
                    // 用户向上滑动
                    mListener?.onLoadMore()
                }
            }
        }
        return false
    }


    interface OnPullOnScrollListener {
        fun onRefresh() //下拉刷新的方法
        fun onLoadMore() //上拉加载更多的方法
    }
}