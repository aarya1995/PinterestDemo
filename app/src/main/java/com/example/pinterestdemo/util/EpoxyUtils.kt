package com.example.pinterestdemo.util

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder

/**
 * A simple View Holder that you can use when you just need to bind up some fields with epoxy.
 */
class SimpleEpoxyHolder : EpoxyHolder() {

    lateinit var view: View

    override fun bindView(itemView: View) {
        view = itemView
    }
}

/**
 * A simple base EpoxyModel that works with [SimpleEpoxyHolder].
 */
abstract class BaseEpoxyModel : EpoxyModelWithHolder<SimpleEpoxyHolder>() {

    override fun bind(holder: SimpleEpoxyHolder) {
        bind(holder.view)
    }

    abstract fun bind(view: View)
}
