# MiraRecycleView

### Description

MiraRecycleView is a RecyclerView(advanced and flexible version of ListView) with pulling to refresh, loading more, swiping to dismiss, draging and drop, animations ,sticky header,show or hide toolbar and FAB when scrolling and many other features.You can use it ```just like RecyclerView```. Support AndroidX now.

Notice that MiraRecycleView is a project under development.

[Your donations is highly appreciated. Thank you!](#donations)

### Features:

* Swipe to refresh(SwipeRefreshLayout)
* Many kinds of animations
* Loading more when reach the last item(infinite scrolling)
* Custom views in loading more
* Support different layout in adapter
* Loading adapter with animation


#### Quick Setup (Basic Usage)
##### 1.Using Gradle:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
dependencies {
    ...
    implementation 'com.github.yehiareda4:MiraRecycle:1.0.0'
}
```
```maven
        <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	<dependency>
	    <groupId>com.github.yehiareda4</groupId>
	    <artifactId>MiraRecycle</artifactId>
	    <version>1.0.0</version>
	</dependency>
```

##### 2.Usage:

--> in Java

``` xml
    <com.reda.yehia.mairrecycle.MiraVRecycleView
            android:id="@+id/mira_recycle_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

```
``` java
    binding.fragmentMyOrdersRvOrders.setUp(R.layout.item_order, 10, 1, linearLayoutManager, LinearLayoutManager.VERTICAL, false, new LoadMore() {
                       @Override
                       public void onLoadMore(int current_page) {
                           setOrdersData();
                       }

                       @Override
                       public void onRefresh() {
                           setOrdersData();
                       }

                       @Override
                       public void onReset() {
                           ordersHistoryDataList = new ArrayList<>();
                           ordersAdapter = new OrdersAdapter(getActivity(), ordersHistoryDataList);
                           binding.fragmentMyOrdersRvOrders.setAdapter(ordersAdapter);
                       }
                   });

```
--> in kotlin

``` xml
    <com.reda.yehia.mairrecycle.MiraVkRecycleView
            android:id="@+id/mira_recycle_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

```
``` kotlin

        binding.miraRecycleView.setUp(
            R.layout.item_test, 5, 1,
            LinearLayoutManager(this), LinearLayout.VERTICAL, true, object : LoadMoreK() {
                override fun onLoadMore(current_page: Int) {
                    if (current_page > 1) {
                        setData()
                    }
                }

                override fun onRefresh() {
                    setData()
                }

                override fun onReset() {
                    dataList.clear()
                    adapter = MiraRecyclerAdapter(this@SplashActivity, dataList)
                    binding.miraRecycleView.setAdapter(adapter)
                }

                override fun onInit() {
                    dataList = ArrayList()
                    adapter = MiraRecyclerAdapter(this@SplashActivity, dataList)
                    binding.miraRecycleView.setAdapter(adapter)
                }
            }

```

For more details, you can read the Wiki and the demo of the project.




### Version Log
* ***v1.0.0*** support Java and Kotlin and migrate to AndroidX


### Upcoming features:
* support Java and Kotlin and migrate to AndroidX

> Notice that it might not be the latest version


#### Welcome to fork and PR (pull request)
If you have some good ideas, please tell us. My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue. If you want to use a rapid development framework for developing apps, you can try [MiraAndroid Framework](https://github.com/yehiareda4/MiraRecycle).

### Screenshot
![MiraRecycle](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/MiraRecycleView/ultimate_recyclerview11.gif)
![MiraRecycle](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/MiraRecycleView/ultimate_recyclerview12.gif)
![MiraRecycle](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/MiraRecycleView/ultimate_recyclerview7.gif)
![MiraRecycle](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/MiraRecycleView/ultimate_recyclerview3.gif)
![MiraRecycle](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/MiraRecycleView/ultimate_recyclerview9.gif)

