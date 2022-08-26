package com.taufik.androidfundamental.data.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.taufik.androidfundamental.BuildConfig
import com.taufik.androidfundamental.data.api.news.ApiService
import com.taufik.androidfundamental.data.local.entity.NewsEntity
import com.taufik.androidfundamental.data.local.room.NewsDao
import com.taufik.androidfundamental.data.response.news.NewsResponse
import com.taufik.androidfundamental.utils.news.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()
    private val apiKey = BuildConfig.API_KEY

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> {
        result.value = Result.Loading
        val client = apiService.getNews(apiKey)
        client.enqueue(object : Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { articlesItem ->
                            val isBookmarked = newsDao.isNewsBookmarked(articlesItem.title)
                            val news = NewsEntity(
                                articlesItem.title,
                                articlesItem.publishedAt,
                                articlesItem.urlToImage,
                                articlesItem.url,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = newsDao.getNews()
        result.addSource(localData) {
            result.value = Result.Success(it)
        }

        return result
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}