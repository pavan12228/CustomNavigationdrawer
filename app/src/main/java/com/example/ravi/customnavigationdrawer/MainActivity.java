package com.example.ravi.customnavigationdrawer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   List<Pojo> pojoList=new ArrayList<>();
    Toolbar mytoolbar;
    DrawerLayout drawer;
    ListView listView;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    String name;
    MainFragment mainFragment;
    private Bundle bundle;
    ActionBarDrawerToggle drawerToggle;
     public static final String Root_url="http://api.androidhive.info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              insertUser();
        //Setting up Toolbar
        mytoolbar = (Toolbar) findViewById(R.id.mytoolbar);
        mytoolbar.setTitle(ArrayContainer.TitleArray[0]);
        setSupportActionBar(mytoolbar);

        listView = (ListView) findViewById(R.id.listView);
        //Setting up the HeaderView for our ListView
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.profile_header_view, listView, false);
        ViewGroup header1 = (ViewGroup) inflater.inflate(R.layout.footer, listView, false);
        listView.addHeaderView(header);
        listView.addHeaderView(header1);

        //Navigation List Items
        listView.setAdapter(new NavListViewAdapter(this,pojoList));
        listView.setOnItemClickListener(this);
        //Method that Loads initial Fragment
        LoadFragment(0);
        //DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        //DrawerToggle is responsible for listing drawer actions
        drawerToggle = new ActionBarDrawerToggle(this, drawer, mytoolbar, R.string.draweropen, R.string.drawerclose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        };
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    private void insertUser() {

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(Root_url).build();

        Apiservice api = adapter.create(Apiservice.class);


              api.Mymeth(new Callback<JsonArray>() {
                  @Override
                  public void success(JsonArray jsonElements, Response response) {

                      for (int i = 0; i < jsonElements.size(); i++) {

                             JsonObject jsonObject1= jsonElements.get(i).getAsJsonObject();
                                 name= jsonObject1.get("title").getAsString();
                                  String image=jsonObject1.get("image").getAsString();
                                         Pojo pojo=new Pojo();
                          pojo.setNmae(name);
                          pojo.setImage(image);
                            pojoList.add(pojo);
                      }









                  }

                  @Override
                  public void failure(RetrofitError error) {
                      Toast.makeText(MainActivity.this, "retro error"+error.toString(), Toast.LENGTH_SHORT).show();
                  }
              });















    }

    // This method loads Fragment when ever I click item from ListView
    public void LoadFragment(int item) {
        //Bundle is useful to send data between Activities and Fragments with (KEY, VALUE) pair
        //We are storing our Item Click Position

             if(item==0){
                 mainFragment = new MainFragment();
                 fManager = this.getSupportFragmentManager();
                 fTransaction = fManager.beginTransaction();
                 fTransaction.replace(R.id.fragment_container, mainFragment);
                 fTransaction.commit();


             }else if(item==1){
                 SecondFragment secondFragment  = new SecondFragment();
                 fManager = this.getSupportFragmentManager();
                 fTransaction = fManager.beginTransaction();
                 fTransaction.replace(R.id.fragment_container, secondFragment);
                 fTransaction.commit();

             }









    }

    //This will handle our Navigation Item Click
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        // As we are using HeaderView the First Position is occupied by our HeaderView
        // Our Actual Navigation Item will start from index 1
        // To get corresponding items from Arrays we need to use below logic
        if (position != 0) {
            position = position - 1;
            LoadFragment(position);
            mytoolbar.setTitle(ArrayContainer.TitleArray[position]);
        } else if (view!=null){
            TextView textView= (TextView) view.findViewById(R.id.textView);
            TextView textView1= (TextView) view.findViewById(R.id.textView3);
                      textView.setText(name);
                      textView1.setText(name);
            Toast.makeText(this, "You Clicked HeaderView", Toast.LENGTH_LONG).show();
        }
        // Close the drawer id open
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
        }

    }


}