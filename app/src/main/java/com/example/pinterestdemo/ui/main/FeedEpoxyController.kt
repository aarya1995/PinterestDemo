package com.example.pinterestdemo.ui.main

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.TypedEpoxyController
import com.example.pinterestdemo.R
import com.example.pinterestdemo.repository.Image
import com.example.pinterestdemo.util.BaseEpoxyModel
import com.google.android.material.shape.RoundedCornerTreatment
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.feed_item_card.view.*

class FeedEpoxyController : TypedEpoxyController<FeedState>() {

    override fun buildModels(data: FeedState) {

        data.feed.forEach { story ->
            feedItem {
                id(story.id.toString())
                author(story.author)
                body(story.body)
                image(story.image)
            }
        }
    }
}

@EpoxyModelClass(layout = R.layout.feed_item_card)
abstract class FeedItemEpoxyModel : BaseEpoxyModel() {

    @EpoxyAttribute lateinit var author: String
    @EpoxyAttribute lateinit var body: String
    @EpoxyAttribute lateinit var image: Image

    override fun bind(view: View) {
        view.author.text = author
        view.body.text = body
        Picasso.get().load(image.url)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.bg_placeholder)
            .transform(RoundedCornersTransformation(22, 0))
            .into(view.image)
    }
}
