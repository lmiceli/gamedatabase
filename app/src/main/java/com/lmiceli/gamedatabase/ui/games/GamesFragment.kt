package com.lmiceli.gamedatabase.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.lmiceli.gamedatabase.R
import com.lmiceli.gamedatabase.databinding.GamesFragmentBinding
import com.lmiceli.gamedatabase.ui.base.BasicFragment
import com.lmiceli.gamedatabase.ui.compose.theme.GDAppTheme
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
//        adapter = GamesAdapter(this)
//        binding.gamesRv.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.gamesRv.adapter = adapter


        binding.composeView.setContent {
            GDAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val games = viewModel.games.collectAsLazyPagingItems()

                    // todox     if (lazyPagingItems.loadState.refresh == LoadState.Loading) {




                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

                        if (games.loadState.refresh == LoadState.Loading) {
                            item {
                                Text(
                                    text = "Waiting for items to load from the backend",
                                    modifier = Modifier.fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        items(count = games.itemCount) { index ->
                            val item = games[index]
                            Text("Index=$index: ${item?.name}", fontSize = 20.sp)
                        }

                        if (games.loadState.append == LoadState.Loading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier.fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        GDAppTheme {
            Greeting("Android")
        }
    }

    // todo: I did not have time to figure out how to get error messages to ui
    //  after migrating to paging data but I plan to eventually do it
    private fun setupObservers() {
//        viewModel.games.observe(
//            viewLifecycleOwner,
//            { gamesData: PagingData<Game> ->
//                setLoading(false)
//                launchOnLifecycleScope {
//                    adapter.submitData(gamesData)
//                }
//            }
//        )
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
