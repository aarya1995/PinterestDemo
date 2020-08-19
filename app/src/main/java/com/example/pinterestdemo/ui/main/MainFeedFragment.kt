package com.example.pinterestdemo.ui.main

import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.pinterestdemo.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_feed.view.*

class MainFeedFragment : BaseMvRxFragment() {

    private val feedViewModel: FeedViewModel by fragmentViewModel()
    private val feedEpoxyController = FeedEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedViewModel.maybeFetchPage(intArrayOf(0))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.recycler_view.setController(feedEpoxyController)
        val layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        view.recycler_view.layoutManager = layoutManager
        view.recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val list = IntArray(2)
                feedViewModel.maybeFetchPage(layoutManager.findLastVisibleItemPositions(list))
            }
        })
    }

    /**
     * Override this to handle any state changes from MvRxViewModels created through MvRx Fragment delegates.
     */
    override fun invalidate() = withState(feedViewModel) {
        feedEpoxyController.setData(it)
        if (it.requestStatus == FeedViewModel.RequestStatus.FAILED) {
            Snackbar.make(requireView(), "Failed to Fetch Feed :-(", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = MainFeedFragment()
        const val TAG = "MainFeedFragment"
    }
}
