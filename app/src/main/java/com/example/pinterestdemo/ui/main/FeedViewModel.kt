package com.example.pinterestdemo.ui.main

import android.util.Log
import com.airbnb.mvrx.*
import com.example.pinterestdemo.dagger.MainComponent
import com.example.pinterestdemo.repository.FeedRepository
import com.example.pinterestdemo.repository.Pin
import io.reactivex.disposables.Disposable

data class FeedState(
    val feed: List<Pin> = emptyList(),
    val nextPageId: String? = "1",
    val requestStatus: FeedViewModel.RequestStatus = FeedViewModel.RequestStatus.UNINITIALIZED
): MvRxState

class FeedViewModel(
    initialState: FeedState,
    private val feedRepository: FeedRepository
): BaseMvRxViewModel<FeedState>(initialState, debugMode = true) {

    private lateinit var feedFetchDisposable: Disposable

    fun maybeFetchPage(lastVisibleScrollPositions: IntArray) = withState { state ->
        if (state.requestStatus != RequestStatus.LOADING
            && state.nextPageId != null
            && (lastVisibleScrollPositions[0] >= state.feed.lastIndex
                    || lastVisibleScrollPositions[1] >= state.feed.lastIndex)) {
            fetch(state.nextPageId)
        }
    }

    private fun fetch(page: String) {
        feedFetchDisposable = feedRepository.fetchFeed(page).subscribe({
            setState {
                copy(
                    feed = feed + it.stories,
                    nextPageId = it.nextPageId,
                    requestStatus = RequestStatus.FINISHED
                )
            }
        }, {
            Log.d(TAG, "Error fetching feed: ${it}")
            setState { copy(requestStatus = RequestStatus.FAILED) }
        })
    }

    override fun onCleared() {
        super.onCleared()
        feedFetchDisposable?.dispose()
    }

    companion object : MvRxViewModelFactory<FeedViewModel, FeedState> {

        override fun create(viewModelContext: ViewModelContext, state: FeedState): FeedViewModel {
            return FeedViewModel(
                FeedState(),
                MainComponent.injector.feedRepository()
            )
        }

        const val TAG = "FeedViewModel"
    }

    enum class RequestStatus {
        UNINITIALIZED, LOADING, FINISHED, FAILED
    }
}
