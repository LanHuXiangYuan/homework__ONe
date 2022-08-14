package com.qxy.tiktlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnNextLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.qxy.tiktlin.databinding.FragmentHomeBinding
import com.qxy.tiktlin.home.ui.MoviesAdapter
import com.qxy.tiktlin.utils.doOnApplyWindowInsets
import com.qxy.tiktlin.utils.executeAfter
import com.qxy.tiktlin.utils.launchAndRepeatWithViewLifecycle
import com.qxy.tiktlin.utils.setContentMaxWidth
import com.rdc.myapplication.model.bean.MovieBean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var homeRecyclerView: RecyclerView

    private lateinit var moviesAdapter: MoviesAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel: HomeViewModel by viewModels()

        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        homeRecyclerView = binding.recyclerviewMovie

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 间隔rv底部使得item滑动在底部bar之上
        binding.recyclerviewMovie.doOnApplyWindowInsets { v, insets, padding ->
            val systemInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.updatePadding(bottom = padding.bottom + systemInsets.bottom)
        }

        // Session list configuration
        moviesAdapter = MoviesAdapter(
            viewLifecycleOwner,
        )
        homeRecyclerView.apply {
            adapter = moviesAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }
        binding.swipeRefreshLayout.doOnNextLayout {
            setContentMaxWidth(it)
        }

        homeViewModel.getShowUseData()
        launchAndRepeatWithViewLifecycle {
            launch {
                homeViewModel.homeUiData.collect { updateHomeUi(it.list) }
            }

            launch {
                homeViewModel.errorMessage.collect { errorMsg ->
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun updateHomeUi(homeUiData: List<MovieBean>?) {
        // Require everything to be loaded.
        val list = homeUiData?: return

        moviesAdapter.submitList(list)
        homeRecyclerView.run {
            if (itemDecorationCount > 0) {
                for (i in itemDecorationCount - 1 downTo 0) {
                    removeItemDecorationAt(i)
                }
            }
        }

        binding.executeAfter {
            isEmpty = list.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}