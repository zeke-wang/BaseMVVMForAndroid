# BaseMVVMForAndroid
This is a project that can quickly create an MVVM project.

## 技术介绍
项目为单Activity项目，使用Jetpack navigation进行导航

依赖注入使用Hilt

序列化使用kotlin序列化插件

## 项目架构

### 数据缓存
项目使用Jetpack datastore来替代了SharePreferences，提供Flow支持方便使用
由于datastore是异步的，缓存的token等热数据需要在应用启动的时候进行加载，因此store分为三个文件存储：
- AppConfigManage （App相关的配置文件存储）
- AuthManger （Token或认证信息存储）
- UserInfoManager （用户信息存储）

### 事件总线
eventbus包下提供了FlowEvent提供了全局的事件总线

### 项目中如何创建Fragment
#### 创建Fragment
基类BaseFragment只提供两个抽象函数initView和initData需要子类自行实现，分别用来初始化话view和数据初始化

BaseBindingVMFragment继承BaseFragment提供了viewBinding和viewModel的支持，需要自己创建好viewModel类（需要实现BaseViewModel，在下文介绍）
同时BaseBindingVMFragment集中收集了FlowEvent的全局事件

#### Fragment扩展
```kotlin
// 没有状态的的Flow收集器
fun <T> Fragment.safeCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            flow.collect { collector(it) }
        }
    }
}

// 基于UiState状态类收集器 
fun <T> Fragment.collectUiState(
    flow: StateFlow<UiState<T>>,
    onSuccess: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    safeCollect(flow) { state ->
        when (state) {
            is UiState.Loading -> onLoading?.invoke()
            is UiState.Success -> onSuccess(state.data)
            is UiState.Error -> onError?.invoke(state.exception)
            is UiState.Idle -> {}
        }
    }
}
```

### 项目中的ViewModel
BaseViewModel提供了基础的全局事件封装：Toast提示事件、ShowLoading事件
通过提供上述事件方便在ViewModel中快速实现发送事件不需要每次都通过FlowEvent去手动发送，使用相应的函数发送即可，使用方式如下：
```kotlin
// Toast事件
tip("这是提示")

// ShowLoading事件
showLoading("加载中")
hideLoading()
```
BaseEventViewModel继承BaseViewModel，里面提供了一次性事件的支持，例如登录成功这种单次通知事件，使用的SharedFlow，它不需要持有状态，类似的操作成功事件，可以通过在ViewModel中绑定一个Intent（需要手动创建Intent类提供意图）
```kotlin
// 创建意图
sealed class LoginIntent {
    object LoginSuccess : LoginIntent()
}

// 通过BaseEventViewModel提供的sendPageEvent发送意图
sendPageEvent(LoginIntent.LoginSuccess)

// 在fragment中进行收集即可
safeCollect(viewModel.pageEvent) {
    when (it) {
        is LoginIntent.LoginSuccess -> {
            navigationRouter.navigateWithPopUpTo(
                R.id.mainFragment,
                R.id.loginFragment,
                true
            )
        }
    }
}
```

