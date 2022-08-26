package com.taufik.androidfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidfundamental.adapter.news.NewsAdapter
import com.taufik.androidfundamental.data.news.Result
import com.taufik.androidfundamental.databinding.FragmentNewsBinding
import com.taufik.androidfundamental.factory.NewsViewModelFactory
import com.taufik.androidfundamental.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private var tabName: String? = null
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: NewsViewModelFactory = NewsViewModelFactory.getInstance(requireContext())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        tabName = arguments?.getString(ARG_TAB)

        newsAdapter = NewsAdapter {
            if (it.isBookmarked) {
                viewModel.deleteNews(it)
            } else {
                viewModel.saveNews(it)
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        when (tabName) {
            TAB_NEWS -> {
                viewModel.getHeadlineNews.observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                newsAdapter?.submitList(it.data)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(requireContext(), "Terjadi kesalahan ${it.error}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            TAB_BOOKMARK -> {
                viewModel.getBookmarkedNews.observe(viewLifecycleOwner) {
                    showLoading(false)
                    newsAdapter?.submitList(it)
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.isVisible = isShow
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        newsAdapter = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}