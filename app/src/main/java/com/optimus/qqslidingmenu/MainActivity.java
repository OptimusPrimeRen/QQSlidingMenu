package com.optimus.qqslidingmenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.optimus.qqslidingmenu.widget.SlidingMenu;

public class MainActivity extends Activity {
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingMenu = (SlidingMenu)findViewById(R.id.id_menu);
    }

    public void toggleMenu(View view) {
        slidingMenu.toggle();
    }
}
