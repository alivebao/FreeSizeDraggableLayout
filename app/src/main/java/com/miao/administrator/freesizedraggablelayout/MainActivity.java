package com.miao.administrator.freesizedraggablelayout;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.miao.freesizedraggablelayout.DetailView;
import com.miao.freesizedraggablelayout.FreeSizeDraggableLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int DEMONUM = 4;
    private Button btnNext;
    private FreeSizeDraggableLayout fsdLayout;
    int iDemoID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView() {
        fsdLayout = (FreeSizeDraggableLayout) findViewById(R.id.fsd_layout);
        fsdLayout.setUnitWidthNum(4);
        fsdLayout.setUnitHeightNum(4);

        setData(fsdLayout, iDemoID);
        btnNext = (Button) findViewById(R.id.btn_Next_Demo);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(fsdLayout, ++iDemoID);
            }
        });
    }

    private void setData(FreeSizeDraggableLayout freeSizeDraggableLayout, int id) {
        List<DetailView> list = new ArrayList<>();
        if (id % DEMONUM == 0) {
            list.add(new DetailView(new Point(0, 0), 2, 2, createButton(1)));
            list.add(new DetailView(new Point(2, 0), 2, 2, createButton(2)));
            list.add(new DetailView(new Point(0, 2), 2, 2, createButton(3)));
            list.add(new DetailView(new Point(2, 2), 2, 2, createButton(4)));
        }
        if (id % DEMONUM == 1) {
            list.add(new DetailView(new Point(0, 0), 2, 2, createButton(1)));
            list.add(new DetailView(new Point(2, 0), 2, 1, createButton(2)));
            list.add(new DetailView(new Point(2, 1), 2, 1, createButton(3)));
            list.add(new DetailView(new Point(0, 2), 4, 2, createButton(4)));
        }
        if (id % DEMONUM == 2) {
            list.add(new DetailView(new Point(0, 0), 2, 1, createButton(1)));
            list.add(new DetailView(new Point(0, 1), 2, 1, createButton(2)));
            list.add(new DetailView(new Point(2, 0), 2, 2, createButton(3)));
            list.add(new DetailView(new Point(0, 2), 1, 2, createButton(4)));
            list.add(new DetailView(new Point(1, 2), 1, 2, createButton(5)));
            list.add(new DetailView(new Point(2, 2), 1, 2, createButton(6)));
            list.add(new DetailView(new Point(3, 2), 1, 1, createButton(7)));
            list.add(new DetailView(new Point(3, 3), 1, 1, createButton(8)));
        }
        if (id % DEMONUM == 3) {
            list.add(new DetailView(new Point(0, 0), 2, 2, createWebView(1)));
            list.add(new DetailView(new Point(2, 0), 2, 1, createWebView(2)));
            list.add(new DetailView(new Point(2, 1), 2, 1, createWebView(3)));
            list.add(new DetailView(new Point(0, 2), 4, 2, createWebView(4)));
        }
        freeSizeDraggableLayout.setList(list);
    }

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

    private WebView createWebView(int i) {
        WebView webView = new WebView(getApplicationContext());
        webView.loadData("<h2>" + i + "</h2><h2>" + i + "</h2><p style=\"color:red\">this is a webview</p><p style=\"color:green\">this is a webview</p><p style=\"color:blue\">this is a webview</p><p>this is a webview</p>", "text/html", "utf-8");
        webView.setBackgroundColor(Color.GRAY);
        return webView;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
