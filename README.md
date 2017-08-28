# LiteUtilities
Speed up your android development by removing boilerplate code

#### To use this library in your project, do as follows:

1. In your top level `build.gradle` file, in the `repository` section add the `maven { url 'https://jitpack.io' }` as shown below
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the `LiteUtilities` dependency in your app level `build.gradle` file
```gradle
compile 'com.github.gurleensethi:LiteUtilities:v1.0.2'
```

#### Current Features
* [RecyclerUtils](#recyclerutils) - Remove the need to make an adapter everytime, set up recycler adapter in as little as 4 lines.
* [ScrollUtils](#scrollutils) - Easily hide/show FloationActionButton on scroll when using RecyclerView or NestedScrollView.
* [ToastUtils](#toastutils) - Creating toasts are just a function away.
* [SPUtils](#sputils) - Simple DSL for Shared Preferences.

#### Motivation
Primary motivation behind the development of this library is to hide the day-to-day boilerplate code that android developers have to deal with, by providing a simple and concise API, but also maintaining complete functionality at the same time.

RecyclerUtils
======

RecyclerUtils contain a very handy class named `RecyclerAdapterUtil` which can be used to make recycler adapters in as little as 4 lines. No need to create a separate adapter class for every recycler view.

The constructor of `RecyclerAdapterUtil` takes 3 parameters.
* Context - Application `Context`.
* ItemList - `List` of objects of type T which will be used as the primary data source for setting data to view holder items.
* LayoutResourceId - Resource id of the layout that represents single item view for RecyclerView.

So to create a recycler adapter that displays a list of strings, you would write something like this:
```kotlin
val list = listOf("Test", "1", "2", "3", "This is a test", "123")
val recyclerAdapter = RecyclerAdapterUtil<String>(this, list, R.layout.item_recycler_view)
recyclerAdapter.addViewsList(R.id.textView, R.id.imageView)
```
The `addViewsList` function is important, pass the id's of all views contained in the single layout file provided in constructor (in this case it is`R.layout.item_recycler_view`) that you want to refer to while binding data. There are two ways to pass these id's.
##### All the views that you want to reference while binding data should be provided before hand, else the app will not function properly.
The `addViewsList` function is important, pass the id's of all views contained in the single layout file provided in constructor (in this case it is`R.layout.item_recycler_view`) that you want to refer to while binding data. There are two ways to pass these id's.
```kotlin
recyclerAdapter.addViewsList(R.id.textView, R.id.imageView)
                  /* OR */
val listOfViews = listOf(R.id.textView, R.id.imageView)
recyclerAdapter.addViewsList(listOfViews)
```
To bind data, add data bind listener:
```kotlin
recyclerAdapter.addOnDataBindListener { itemView, item, position. innerViews -> 
            val textView = innerViews[R.id.textView] as TextView
            textView.text = item
        }
```
`addOnDataBindListener` is a lambda which provides three items:
* `itemView` - The ViewHolder itself.
* `item` - Data item from the list.
* `position` - Position of the data item in the list.
* `innerViews` - A `Map<Int, View>` containing the reference to the views that were provided in the `addViewsList` function.

### Click Listeners
You can also add `OnClickListener` and `OnLongClickListener` simply by implementing two lambdas.

```kotlin
//OnClickListener
recyclerAdapter.addOnClickListener { item, position -> 
            //Take action when item is pressed
        }

//OnLongClickListener
recyclerAdapter.addOnLongClickListener { item, position ->
            //Take action when item is long pressed
        }
```

Both `addOnClickListener` and `addOnLongClickListener` provide lambda with two parameters:
* `item` - Data item from the list.
* `position` - Position of the data item in the list.

### Using Builder pattern for more consice code

Use `RecyclerAdapterUtil.Builder` to chain functions as shown below.

```kotlin
RecyclerAdapterUtil.Builder(this, list, R.layout.item_recycler_view)
                .viewsList(R.id.textView, R.id.imageView)
                .bindView { itemView, item, position, innerViews ->
                    val textView = innerViews[R.id.textView] as TextView
                    textView.text = item
                }
                .addClickListener { item, position ->
                    //Take action when item is pressed
                }
                .addLongClickListener { item, position ->
                    //Take action when item is long pressed
                }
                .into(recyclerView)
```

`into(RecyclerView)` function takes the reference of `RecyclerView` and directly sets the adapter to it so you don't have to do it explicitly.
If you want the object of adapter and want to set it manually use `build()` instead of `into(RecyclerView)`.

ScrollUtils
======

Hide FloatingActionButton when user scrolls up and show it again when scrolled down. You can achieve this by using function `hideFloatingActionButtonOnScroll` on `NestedScrollView` and `RecyclerView`. These functions are implemented as extension functions.
```kotlin
val nestedScrollView = findViewById(R.id.nestedScrollView) as NestedScrollView
val floatingActionButton = findViewById(R.id.floatingActionButton) as FloatingActionButton

nestedSrollView.hideFloatingActionButtonOnScroll(floatingActionButton)
```

### Take custom action when scrolled up and down
If you want to take custom action when scrolled up or down you can implement `ScrollListener` using the function `addScrollListener(ScrollListener)`. This works with both `NestedScrollView` and `RecyclerView`.

```kotlin
nestedScrollView.addScrollListener(object : ScrollListener {
            override fun scrolledDown() {
                //Take Action when user scrolls down
            }

            override fun scrolledUp() {
                //Take Action when user scrolls up
            }
        })
```

ToastUtils
======

Making toast has never been easier. Just use `shortToast(String)` for making short toast and `longToast(String)` for making long ones. These functions are implemented as extension functions on `Context`, so wherever `Context` is available, these functions can be used.

```kotlin
shortToast("This is a short toast")
longToast("This is a long toast")
```

### Making colored Toasts
To make a toast with custom background and text color use `coloredShortToast(message, backgroundColor, textColor)` or `coloredLongToast(message, backgroundColor, textColor)`.

Both of these functions take three parameters:
* `message`: String displayed by the toast.
* `backgroundColor`: Background Color of the toast.
* `textColor`: Color of the text shown.

```kotlin
coloredShortToast("Colored short toast", R.color.darker_gray, R.color.black)
coloredLongToast("Colored long toast", R.color.darker_gray, R.color.black)
```

SPUtils
======

Easy DSL for sharedpreferences. No need to write long lines of code when using SharedPreferences. The below functions are implemented as extension functions on `Context`, so they are available wherever `Context` is available.

### Storing values
To use the default SharedPreferences file which is provided by the library itself, use `defaultSharedPreferences` function which takes a lambda for required operations, much easier to understand with an example. The mode used to open file is `MODE_PRIVATE`.

```kotlin
defaultSharedPreferences {
            putString("string", "Some Value 123")
            putInt("integer", 1)
        }
```

If you want to use your own file and mode the use `sharedPreferences(fileName, mode, lambda)`.

```kotlin
sharedPreferences("SP", Context.MODE_PRIVATE) {
            putString("string", "Some Value 123")
            putInt("integer", 1)
        }
```

### Fetching values
To get value from default SharedPreferences use `getFromDefaultSharedPreferences<T>(key, defaultValue)`.

```kotlin
getFromDefaultSharedPreferences<String>("string", "default value")
```

You can also eliminate the need to specify a type explicitly, but in that case the type will be inferred from the type of `defaultValue` which is the second parameter so you can write.

```kotlin
getFromDefaultSharedPreferences("string", "default value")
```

To get from custom SharedPreferences file use `getFromSharedPreferences<T>(fileName, key, defaultValue)`.

```kotlin
getFromSharedPreferences<String>("SP", "string", "default")
                /* OR */
getFromSharedPreferences("SP", "string", "default")
```

Support
======

The primary purpose of this library is to speed up development process by removing boilerplate code, so if you have and idea or a new feature that meets the requirement and enhances the library as a whole or you found a bug in the existing code please open an [issue](https://github.com/gurleensethi/LiteUtilities/issues/new), it is much appreciated.
