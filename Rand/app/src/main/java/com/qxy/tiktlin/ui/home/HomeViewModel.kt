package com.qxy.tiktlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qxy.tiktlin.model.network.AppConfig.CLIENT_KEY
import com.qxy.tiktlin.model.network.AppConfig.CLIENT_SECRET
import com.qxy.tiktlin.model.network.RankApi
import com.qxy.tiktlin.model.network.TokenApi
import com.qxy.tiktlin.utils.WhileViewSubscribed
import com.rdc.myapplication.model.bean.HeatBean
import com.rdc.myapplication.model.bean.MovieBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Collections.list
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _isEnpty = MutableStateFlow(true)
    val isEnpty: StateFlow<Boolean> = _isEnpty

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val errorMessage: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, WhileViewSubscribed)


    private val _Token = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val token: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, WhileViewSubscribed)


    data class HomeUiData(
        val list: List<MovieBean>? = null,
    )

    private val _homeUiData = MutableStateFlow(HomeUiData())
    val homeUiData: StateFlow<HomeUiData> =_homeUiData

    private val refreshSignal = MutableSharedFlow<Unit>()
    private val loadDataSignal: Flow<Unit> = flow {
        emit(Unit)
        emitAll(refreshSignal)
    }

    private val _swipeRefreshing = MutableStateFlow(false)
    val swipeRefreshing: StateFlow<Boolean> = _swipeRefreshing

    fun onSwipeRefresh() {
        viewModelScope.launch {
            _swipeRefreshing.emit(true)
            _isLoading.emit(false)
            //代表首页的一个下拉刷新操作
            delay(500)
            _isLoading.emit(true)
            _swipeRefreshing.emit(false)
        }
    }

    fun getTokenData() {
        viewModelScope.launch {
          //  val listResult = TokenApi.retrofitService.getAccessToken()
           // _Token.value = listResult
        }
    }

    fun getRankData() {
        viewModelScope.launch {
          //  val listResult = RankApi.retrofitService.getMovieRankData(CLIENT_SECRET,,"authorization_code",CLIENT_KEY)
          //  _homeUiData.value = listResult
        }
    }

    fun getShowUseData() {
        val list= ArrayList<MovieBean>()
        val movieBean=MovieBean("a"
        ,"囧妈"
        ,"中国","2020-01-25"
        ,"徐峥"
        ,"徐峥 袁泉 沈腾 吴云芳 陈奇 黄梅莹 欧丽娅 贾冰 郭京飞"
        ,"https://p3-dy.bytecdn.cn/obj/compass/9ac412ae054b57f69c0967a8eb5e25c9"
        , HeatBean("789200","789200","684900","684900"))
        for (i in 0 until 20) {
            list.add(movieBean) // 输出: 0 ~ 99
        }
        _homeUiData.value=(HomeUiData(list));
    }

}