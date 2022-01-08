package com.lmiceli.gamedatabase.ui.gamedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.lmiceli.gamedatabase.R
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.databinding.GameDetailFragmentBinding
import com.lmiceli.gamedatabase.ui.base.BasicFragment
import com.lmiceli.gamedatabase.utils.Resource
import com.lmiceli.gamedatabase.utils.autoCleared
import com.lmiceli.gamedatabase.utils.image.ImageUrlBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class GameDetailFragment : BasicFragment() {

    private var binding: GameDetailFragmentBinding by autoCleared()
    private val viewModel: GameDetailViewModel by viewModels()
    private val yearSDF = SimpleDateFormat("yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GameDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.game.observe(viewLifecycleOwner, { resource ->

            if (resource is Resource.SUCCESS || resource is Resource.CACHED) {
                resource.data?.let { game ->
                    bindGame(game)
                }
            } else if (resource is Resource.ERROR) {
                showError(resource)
            }

        })
    }

    private fun bindGame(game: Game) {
        binding.name.text = game.name
        binding.summary.text = game.summary
        binding.rating.text = requireContext().getString(R.string.rating, game.aggregatedRating)
        binding.genre.text = requireContext().getString(R.string.genre, game.genre)
        binding.platforms.text = game.platforms

        game.published?.let {
            binding.releaseYear.text =
                requireContext().getString(R.string.released, getPublicationYear(it))
        }

        game.companies?.let {
            binding.companies.text = it
        }

        val glide = Glide.with(this)

        glide.load(ImageUrlBuilder.getBigImage(game.coverUrl))
            .dontAnimate()
            .timeout(20000)
            .thumbnail(
                glide.load(ImageUrlBuilder.getSmallImage(game.coverUrl))
            )
            .into(binding.image)
    }

    private fun getPublicationYear(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return yearSDF.format(date)
    }

}
