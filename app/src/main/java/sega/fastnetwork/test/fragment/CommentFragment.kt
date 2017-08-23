package sega.fastnetwork.test.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.content_comments.view.*
import sega.fastnetwork.test.R
import sega.fastnetwork.test.adapter.CommentAdapter
import sega.fastnetwork.test.manager.AppManager
import sega.fastnetwork.test.model.Comment
import sega.fastnetwork.test.presenter.CommentPresenter
import sega.fastnetwork.test.util.Constants


/**
 * A simple [Fragment] subclass.
 */
class CommentFragment : Fragment(), CommentAdapter.OncommentClickListener, CommentPresenter.CommentView {



    override fun getCommentDetail(listcomment: ArrayList<Comment>) {

        adapter!!.commentsList.clear()
        adapter!!.commentsList = listcomment
        adapter!!.notifyDataSetChanged()
        comments_list.scrollToPosition(adapter!!.commentsList.size-1)

    }


    override fun isCommentSuccessful(isCommentSuccessful: Boolean) {

    }



    var mCommentPresenter: CommentPresenter? = null

    override fun oncommentClicked(position: Int) {
    }

    override fun setErrorMessage(errorMessage: String) {
        println(errorMessage)
    }


    private var id: String = ""
    var adapter : CommentAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(R.layout.fragment_comment, container, false) as View
        // Inflate the layout for this fragment
        id = arguments.getString(Constants.product_ID)
        Log.e("ASD", id)
        adapter = CommentAdapter(context, this)
        val layoutManager = LinearLayoutManager(context)
        v.comments_list.layoutManager = layoutManager
        v.comments_list.setHasFixedSize(true)
        v.comments_list.adapter = adapter
        mCommentPresenter = CommentPresenter(this)
        mCommentPresenter!!.refreshcomment(id)
        v.buttoncomment.setOnClickListener {
            //            Log.e("cmtttt",AppAccountManager.)
            Log.e("cmtttt", AppManager.getAppAccountUserId(activity) + " " + id + " " + v.writecomment.text.toString())
            mCommentPresenter!!.addcomment(AppManager.getAppAccountUserId(activity), id, v.writecomment.text.toString())
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(writecomment.windowToken, 0)
            v.writecomment.setText("")
            v.writecomment.clearFocus()

        }
        return v


    }

}// Required empty public constructor