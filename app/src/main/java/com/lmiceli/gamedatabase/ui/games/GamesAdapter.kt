package com.lmiceli.gamedatabase.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.databinding.RowGameBinding
import com.lmiceli.gamedatabase.ui.base.extensions.visibleOrInvisible
import com.lmiceli.gamedatabase.utils.image.ImageUrlBuilder


class GamesAdapter(private val listener: GameItemListener) :
    PagingDataAdapter<Game, GamesAdapter.GameViewHolder>(GameDC()) {

    interface GameItemListener {
        fun onClickedGame(id: Long)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val binding: RowGameBinding =
            RowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GameViewHolder(
        private val itemBinding: RowGameBinding,
        private val listener: GameItemListener
    ) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private var game: Game? = null
        private val glide = Glide.with(itemBinding.root)

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Game?) {
            this.game = item
            itemBinding.name.text = item?.name
            itemBinding.image.visibleOrInvisible(item?.coverUrl != null)

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(RoundedCorners(16))

            item?.coverUrl?.let {
                glide
                    .load(ImageUrlBuilder.getSmallImage(item.coverUrl))
                    .apply(requestOptions)
                    .into(itemBinding.image)
            }

        }

        override fun onClick(v: View?) {
            game?.id?.let {
                listener.onClickedGame(it)
            }
        }

    }

    private class GameDC : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean {
            return newItem == oldItem
        }
    }


}

