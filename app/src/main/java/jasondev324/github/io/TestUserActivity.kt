package jasondev324.github.io

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import jasondev324.github.io.databinding.ActivityTestUserBinding


class TestUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestUserBinding
    private lateinit var testAdapter: PhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testAdapter = PhotoAdapter()
        testAdapter.addData("DIU")
//        binding.viewPager2.adapter = testAdapter
//        binding.viewPager2.setPageTransformer(
//            MarginPageTransformer(
//                resources.getDimension(R.dimen.dp_10).toInt()
//            )
//        )
        binding.rvStudy.adapter = testAdapter
        val pagerSnapHelper = PagerSnapHelper()

        val onTouchListener = RVRefshLoadMoreListener()
        onTouchListener.bind(binding.rvStudy,
            testAdapter,
            object : RVRefshLoadMoreListener.OnPullOnScrollListener {
                override fun onLoadMore() {
                    Toast.makeText(this@TestUserActivity, "到底部了--", Toast.LENGTH_SHORT).show()
                }

                override fun onRefresh() {
                    Toast.makeText(this@TestUserActivity, "到顶", Toast.LENGTH_SHORT).show()
                }
            })
        pagerSnapHelper.attachToRecyclerView(binding.rvStudy)
    }

    var curVideoIndex = 0
    var videoIDs = arrayListOf<String>()
    fun onUpdateStatus(view: View) {
        videoIDs.clear()
//制造数据，当接口返回的列表
        //制造数据，当接口返回的列表
        for (i in 0..14) {
            if (i == 5) {
                videoIDs.add("DIU")
                continue
            }
            videoIDs.add("$i--CLOT")
        }

        //                todo 查找当前视频所在列表的index
        for (i in videoIDs.indices) {
            if (videoIDs[i].contains("DIU")) {
                curVideoIndex = i
                break
            }
        }

        val lastList: ArrayList<String> = ArrayList()
        val nextList: ArrayList<String> = ArrayList()
        //创建数据
        for (i in 0 until curVideoIndex) {
            val videoInfoDto: String = (videoIDs.get(i))
            lastList.add(videoInfoDto)
        }
        testAdapter.addData(0, lastList)
        for (i in curVideoIndex + 1 until videoIDs.size) {
            val videoInfoDto: String = (videoIDs.get(i))
            nextList.add(videoInfoDto)
        }
        testAdapter.addData(nextList)
    }

    class PhotoAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.user_test_page) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(
                R.id.tvTitle, "${item}"
            )
            holder.getView<ShapeableImageView>(R.id.ivTest).setOnClickListener {
                Toast.makeText(holder.itemView.context, "dianji", Toast.LENGTH_SHORT).show()
            }
        }
    }
}