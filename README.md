## Android FreeSizeDraggableLayout

-----

A viewgroup enable custom size of childviews and change their position which has same size by drag

---
### Analyze


[FreeSizeDraggableLayout实现过程解析](http://alivebao.github.io/2016/04/16/FreeSizeDraggableLayout/)


### Demo


![FreeSizeDraggableLayout](http://7xsv7c.com2.z0.glb.clouddn.com/freeseizedraggablelayout_demo_compress_0.2.gif)


### [Download Demo](http://7xsv7c.com2.z0.glb.clouddn.com/freeseizedraggablelayout_demo_0.2.apk)


### Usage
----

#### Gradle

```groovy
dependencies {
   compile 'com.miao:freesizedraggablelayout:0.0.2'
}
```

#### Maven 

```xml
<dependency>
  <groupId>com.miao</groupId>
  <artifactId>freesizedraggablelayout</artifactId>
  <version>0.0.2</version>
  <type>pom</type>
</dependency>
```

Use it in your own code:

1.create the viewgroup on your xml:
```xml
	<com.miao.freesizedraggablelayout.FreeSizeDraggableLayout
        android:id="@+id/fsd_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```	
2.set the viewgroup and set the size of it
```java
FreeSizeDraggableLayout fsdLayout = (FreeSizeDraggableLayout) findViewById(R.id.fsd_layout);
fsdLayout.setUnitWidthNum(4);
fsdLayout.setUnitHeightNum(4);
```
3.create a DetailView list:
```java
List<DetailView> list = new ArrayList<>();
/*
DetailView:
public DetailView(Point p, int width, int height, View v) {
    setPoint(p);
    setWidhtNum(width);
    setHeightNum(height);
    setView(v);
}
*/
list.add(new DetailView(new Point(0, 0), 2, 2, createButton(1)));
list.add(new DetailView(new Point(2, 0), 2, 2, createButton(2)));
list.add(new DetailView(new Point(0, 2), 2, 2, createButton(3)));
list.add(new DetailView(new Point(2, 2), 2, 2, createButton(4)));
/*
private Button createButton(int i) {
    final Button btn = new Button(getApplicationContext());
    btn.setText("Button - " + i);
    btn.setBackgroundColor(Color.BLACK);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), btn.getText(), Toast.LENGTH_SHORT).show();
        }
    });
    return btn;
}
*/
```
4.set the list as ViewGroup's datasource
```java
freeSizeDraggableLayout.setList(list);
```

PS: Function of **change position between group position and draggled item**(in v0.0.1, we can just change two view has the same size, we can change the position of **whole group of views** and **the view dragged** if they have the same size in v0.0.2. just like the third scenario in demo) is added in version 0.0.2, set it disable if you don't need it:
```java
freeSizeDraggableLayout.setGroupChangeEnable(false);
```