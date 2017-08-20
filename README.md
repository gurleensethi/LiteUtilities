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
compile 'com.github.gurleensethi:'
```

#### Current Features
* [RecyclerUtils](recyclerutils) - Remove the need to make an adapter everytime, set up recycler adapter in as little as 4 lines.
* [ScrollUtils](scrollutils) - Easily hide/show FloationActionButton on scroll when using RecyclerView or NestedScrollView.
* [ToastUtils](toastutils) - Creating toasts are just a function away.
* [SPUtils](sputils) - Simple DSL for Shared Preferences.

#### Motivation
Primary motivation behind the development of this library is to hide the day-to-day boilerplate code that android developers have to deal with, by providing a simple and concise API, but also maintaining complete functionality at the same time.

## Usage and API

* ### RecyclerUtils
RecyclerUtils contain a very handy class named `RecyclerAdapterUtil` which can be used to make recycler adapters in as little as 4 lines. No need to create a separate class for every recycler view.

The constructor of `RecyclerAdapterUtil` takes 3 parameters.
* Context - Application `Context`.
* ItemList - `List` of objects of type T which will be used as the primary data source for setting data to view holder items.
* LayoutResourceId - Resource id of the layout that represents single item view for RecyclerView.

So to create a recycler adapter that displays a list of strings, you would write something like this:
```kotlin
val list = listOf("Test", "1", "2", "3", "This is a test", "123")
val recyclerAdapter = RecyclerAdapterUtil<String>(this, list, R.layout.item_recycler_view)
```
To bind data, add data bind listener:
```kotlin
recyclerAdapter.addOnDataBindListener { itemView, item, position -> 
            val textView = itemView.findViewById<TextView>(R.id.textView)
            textView.text = item
        }
```
`addOnDataBindListener` is a lambda which provides three items:
* `itemView` - The ViewHolder itself.
* `item` - Data item from the list.
* `position` - Position of the data item in the list.

#### Click Listeners
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

#### Using Builder pattern for more consice code

Use `RecyclerAdapterUtil.Builder` to chain functions as shown below.

```kotlin
RecyclerAdapterUtil.Builder(this, list, R.layout.item_recycler_view)
                .bindView { itemView, item, position ->
                    val textView = itemView.findViewById<TextView>(R.id.textView)
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
