package com.fengniao.kaiyan.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.ui.activity.SearchResultActivity
import com.fengniao.kaiyan.ui.adapter.SearchAdapter
import com.fengniao.kaiyan.util.KeyboardUtils
import com.fengniao.kaiyan.wedegit.CircularRevealAnim
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : DialogFragment(), CircularRevealAnim.AnimListener, ViewTreeObserver.OnPreDrawListener,
        DialogInterface.OnKeyListener, View.OnClickListener {

    var data: MutableList<String> = arrayListOf("脱口秀", "城会玩", "666", "笑cry", "漫威",
            "清新", "匠心", "VR", "心理学", "舞蹈", "品牌广告", "粉丝自制", "电影相关", "萝莉", "魔性"
            , "第一视角", "教程", "毕业设计", "奥斯卡", "燃", "冰与火之歌", "温情", "线下campaign", "公益")

    lateinit var mCircularRevealAnim: CircularRevealAnim
    lateinit var mAdatper: SearchAdapter
    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.fragment_search, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width: Int = metrics.widthPixels * 0.98.toInt()
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)
    }

    private fun init() {
        mAdatper = SearchAdapter(context, data)
        val manager = FlexboxLayoutManager(context)
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        recycler_view.layoutManager = manager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdatper

        tv_hint.typeface = Typeface.createFromAsset(activity.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim.setAnimaListener(this)
        dialog.setOnKeyListener(this)
        iv_search.viewTreeObserver.addOnPreDrawListener(this)
        iv_search.setOnClickListener(this)
        iv_back.setOnClickListener(this)
    }


    override fun onPreDraw(): Boolean {
        iv_search.viewTreeObserver.removeOnPreDrawListener(this)
        mCircularRevealAnim.show(iv_search, rootView)
        return true
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            hideAnim()
        } else if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            search()
        }
        return false
    }

    override fun onHideAnimationEnd() {
        et_search.setText("");
        dismiss();
    }

    override fun onShowAnimationEnd() {
        if (isVisible) {
            KeyboardUtils.openKeyboard(activity, et_search)
        }
    }


    private fun search() {
        val searchKey = et_search.text.toString()
        if (TextUtils.isEmpty(searchKey.trim({ it <= ' ' }))) {
            Toast.makeText(activity, "请输入关键字", Toast.LENGTH_SHORT).show()
        } else {
            hideAnim()
            val keyWord = et_search.text.toString().trim()
            val intent: Intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            activity?.startActivity(intent)
        }

    }

    private fun hideAnim() {
        KeyboardUtils.closeKeyboard(activity, et_search);
        mCircularRevealAnim.hide(iv_search, rootView)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                hideAnim()
            }
            R.id.iv_search -> {
                search()
            }
        }
    }

}
