package com.lmiceli.gamedatabase.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lmiceli.gamedatabase.R
import com.lmiceli.gamedatabase.data.entities.Game
import com.lmiceli.gamedatabase.databinding.GamesFragmentBinding
import com.lmiceli.gamedatabase.ui.base.BasicFragment
import com.lmiceli.gamedatabase.ui.compose.theme.Charcoal
import com.lmiceli.gamedatabase.ui.compose.theme.GDAppTheme
import com.lmiceli.gamedatabase.utils.autoCleared
import com.lmiceli.gamedatabase.utils.image.ImageUrlBuilder
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
                    // todox theme
//                    color = MaterialTheme.colorScheme.background
                    color = Charcoal
                ) {

                    val games = viewModel.games.collectAsLazyPagingItems()

                    // todox     if (lazyPagingItems.loadState.refresh == LoadState.Loading) {

                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

                        if (games.loadState.refresh == LoadState.Loading) {
                            item {
                                Text(
                                    text = "Waiting for items to load from the backend",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        // todox rounded corners
                        // todox on click
                        // todox move to other file/s
                        // todox tests
                        // todox check sizes
                        // todox refresh?
                        // use coverurl


                        items(count = games.itemCount) { index ->
                            val item = games[index]!!
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        onClickedGame(item.id)
                                    }
                                    .padding(2.dp)
                            ) {

//                            Log.d("todox", item.coverUrl?:"nada")
                                GameImage(item)
//                                GameTitle(item.name)
                            }
                        }

                        if (games.loadState.append == LoadState.Loading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
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
    private fun GameImage(item: Game) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImageUrlBuilder.getSmallImage(item.coverUrl))
                .crossfade(true)
                .build(),
    //                                placeholder = painterResource(R.drawable.placeholder),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(255.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(2))
        )
    }

    @Composable
    private fun GameTitle(text: String) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 21.sp, color = Color.White)) {
                append(text)
            }
        }

        Text(
            text = annotatedString,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
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
