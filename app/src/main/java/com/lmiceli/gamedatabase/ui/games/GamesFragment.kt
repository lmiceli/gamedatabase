package com.lmiceli.gamedatabase.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.lmiceli.gamedatabase.R
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.databinding.GamesFragmentBinding
import com.lmiceli.gamedatabase.ui.base.BasicFragment
import com.lmiceli.gamedatabase.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class GamesFragment : BasicFragment(), GamesAdapter.GameItemListener {

    private var binding: GamesFragmentBinding by autoCleared()
    private val viewModel: GamesViewModel by viewModels()
    private lateinit var adapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GamesFragmentBinding.inflate(inflater, container, false)
        binding.swipeContainer.setOnRefreshListener { adapter.refresh() }

        binding.toolbar.title = getString(R.string.games)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = GamesAdapter(this)
        binding.gamesRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.gamesRv.adapter = adapter

    }

    // todo: I did not have time to figure out how to get error messages to ui
    //  after migrating to paging data but I plan to eventually do it
    private fun setupObservers() {
        viewModel.games.observe(
            viewLifecycleOwner,
            { gamesData: PagingData<Game> ->
                setLoading(false)
                launchOnLifecycleScope {
                    adapter.submitData(gamesData)
                }
            }
        )
    }

    override fun onClickedGame(id: Long) {

        findNavController().navigate(
            R.id.action_gamesFragment_to_gameDetailFragment,
            bundleOf("id" to id)
        )
    }

    fun setLoading(loading: Boolean) {
        this.binding.swipeContainer.isRefreshing = loading
    }
}
