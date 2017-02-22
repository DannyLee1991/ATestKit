# ATestKit

ATestKit是一款Android测试小工具。目前支持的功能有：

- 查看Activity中所有可以被反射获取的变量的值
- 查看应用内存开销、CPU消耗、流量使用情况
- 可以执行当前界面中通过`@TestMethod`标注过的方法
- 可以查看命令行输出（此功能还在完善中）
- 为应用添加一个后门Activity，可以用于放置开发中的一些后门入口

![](/img/demo.gif)

## 如何使用

在`build.gradle`中加入引用，不同的编译使用不同的引用：

```
dependencies {
    debugCompile 'com.dannylee:atestkit:0.3'
    releaseCompile 'com.dannylee:atestkit_no_op:0.3'
}
```

### 查看Activity反射得到的参数

需要在被查看的Activity中添加：

``` java
@Override
protected void onStart() {
    super.onStart();
    TestKit.registActivity(this);
}

@Override
protected void onStop() {
    super.onStop();
    TestKit.unRegistActivity(this);
}
```

### 查看内存流量等通用信息

在 Application 中：

``` java
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 注册通用信息
        TestKit.registAllCommonTestType();
    }
}
```

### 为Activity中的方法设置调用入口

在需要支持此功能的Activity中添加：

``` java
@Override
protected void onStart() {
    super.onStart();
    TestKit.registEvent(this);
}

@Override
protected void onStop() {
    super.onStop();
    TestKit.unRegistEvent(this);
}
```

在需要被调用到的方法上添加`@TestMethod`注解：

``` java
@TestMethod(name = "showToast", description = "just show Toast!", args = {"hello world!"})
private void showToast(String toast) {
    Toast.makeText(getApplication(), toast, Toast.LENGTH_SHORT).show();
}
```

### 添加后门Activity

若要添加后门Activity，需要创建一个后门Activity的代理类，继承自`BackDoorActivityProxy`：

``` java
public class SampleBackDoorActivityProxy extends BackDoorActivityProxy {

    private static SampleBackDoorActivityProxy mInstance = new SampleBackDoorActivityProxy();

    public static SampleBackDoorActivityProxy getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            ...
        }
    }
}
```

并且在 Application 中注册：

``` java
public class SampleApplication extends Application {
  @Override
  public void onCreate() {
      super.onCreate();
      // 设置后门activity代理
      TestKit.setBackDoorActivityProxy(SampleBackDoorActivityProxy.getInstance());
  }
}
```

### 执行命令（完善中）

若想要查看命令行输出结果，可以直接调用：


``` java
String cmdStr = "logcat"
TestKit.executeCmd("cmdStr");
```

---

