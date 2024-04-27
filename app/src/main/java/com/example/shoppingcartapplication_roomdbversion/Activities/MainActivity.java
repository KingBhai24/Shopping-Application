/*
-------------------------------------------------------
-------------------------------------------------------
Functionalities:
-------------------------------------------------------
ActivityMain:
Options Menu
-------------------------------------------------------
ItemRecyclerViewFragment:
    Recycler_view:
    Search
        Filter_results
    ViewHolder_OnClickListener:
        launchDetailFragment
    ViewHolder_onLongClickListener:
        Action_mode_call_back
            addSelectedItemsToCart
    Cart_button:
        launchCartFragment
    Toggle_button:
        toggleRecyclerViewLayout
-------------------------------------------------------
ItemDetailFragment:
    Add_To_Cart
-------------------------------------------------------
CartItemRecyclerViewFragment:
    decrease
    increase
    Remove
-------------------------------------------------------
-------------------------------------------------------
Resolved
-------------------------------------------------------
onSaveInstanceState
fragment add/replace and see function calls
make enum file for all keys for savedBundleState
save should only save and not update (DB)
cartItem remove id and make itemId as primary id and foreign key
onLongClick on one item, put it in selected list and call bind again for entire list as notifyDataSetChanged()? overhead?
onLongClick() first time, set visibility of checkbox of selected item to visible only and not for all
-------------------------------------------------------
-------------------------------------------------------
Errors
-------------------------------------------------------
Always creating new instance of fragments. How about saving them and utilizing them again and again when needed? saves a lot of Cpu as they will not get destroyed and recreated.
custom fonts
Integer File for center attribute
what happens onBackPressed?
always writing and reading directly from db on change??
all compiler suggestions like suppress int etc to be removed?
return types of functions db boolean
Log everything
After onDestroyActionMode() only update viewHolders marked checked and visible on screen.
save data in onStop or onPause? next fragment starts after onPause or onStop?
Activity ViewModel to save which fragments were there.
Async tasks
database write issues with async calls. multiple async calls writing to db.
use of notify data set changed
diff util class
Different Repository for different classes
Loading all the data in ViewModels prematurely. Load them when they are needed.
When rotating fragments are destroyed and recreated by android. Creating new fragments creates extra fragments with new view models so state saving does not work. Use fragments created by android after rotation instead of creating new ones.
save data of adapters in view models to survive configuration change. Make view model in adapter or use listeners to communicate with fragment to get data from view model.
-------------------------------------------------------
-------------------------------------------------------
*/
package com.example.shoppingcartapplication_roomdbversion.Activities;
//---------------------------------------------------------------------------------------//

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Fragments.CartItemListFragment;
import com.example.shoppingcartapplication_roomdbversion.Fragments.ItemDetailFragment;
import com.example.shoppingcartapplication_roomdbversion.Fragments.ItemListFragment;
import com.example.shoppingcartapplication_roomdbversion.Helpers.DatabaseHelper;
import com.example.shoppingcartapplication_roomdbversion.R;
import com.example.shoppingcartapplication_roomdbversion.ViewModelActivities.MainActivityViewModel;

//---------------------------------------------------------------------------------------//
public class MainActivity extends AppCompatActivity implements ItemListFragment.Listener {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "MainActivity";
    private int frameLayoutCount;
    private FrameLayout frameLayout1, frameLayout2;
    private MenuItem menuItem1;
    private MainActivityViewModel mainActivityViewModel;

    //---------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate(Bundle savedInstanceState) " + savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setContentView(R.layout.activity_main);

        frameLayoutCount = getResources().getInteger(R.integer.frameLayoutCount);
        if (frameLayoutCount == 1) { //portrait
            frameLayout1 = findViewById(R.id.ActivityMainPort_Fl1);
            changeFragment(frameLayout1, new ItemListFragment(), false);
        } else if (frameLayoutCount == 2) { //landscape
            frameLayout1 = findViewById(R.id.ActivityMainLand_Fl1);
            frameLayout2 = findViewById(R.id.ActivityMainLand_Fl2);
            changeFragment(frameLayout1, new ItemListFragment(), false);
        }

        //DatabaseHelper HDB = DatabaseHelper.getDB(this);
        //enterDbEntries(HDB);
    }

    //---------------------------------------------------------------------------------------//
    //Options Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, "onCreateOptionsMenu(Menu menu)");
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        menuItem1 = menu.findItem(R.id.MainActivityMenu_Mi2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.i(LOG_TAG, "onOptionsItemSelected(MenuItem menuItem)");
        if (menuItem.getItemId() == R.id.MainActivityMenu_Mi1) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //---------------------------------------------------------------------------------------//
    //ItemListFragment.Listener
    @Override
    public void launchItemDetailFragment() {
        if (frameLayoutCount == 1) {
            changeFragment(frameLayout1, new ItemDetailFragment(), true);
        } else if (frameLayoutCount == 2) {
            changeFragment(frameLayout2, new ItemDetailFragment(), false);
        }
    }

    @Override
    public void launchFragmentListCartItems() {
        if (frameLayoutCount == 1) {
            changeFragment(frameLayout1, new CartItemListFragment(), true);
        } else if (frameLayoutCount == 2) {
            changeFragment(frameLayout2, new CartItemListFragment(), false);
        }
    }

    //---------------------------------------------------------------------------------------//
    //Class Utility Functions
    private void enterDbEntries(DatabaseHelper HDB) {
        HDB.getItemDao().insert(new ItemEntity("Apple", 100, "abc", R.drawable.apple));
        HDB.getItemDao().insert(new ItemEntity("Ball", 200, "abc", R.drawable.ball));
        HDB.getItemDao().insert(new ItemEntity("Cat", 300, "abc", R.drawable.cat));
        HDB.getItemDao().insert(new ItemEntity("Dog", 400, "abc", R.drawable.dog));
        HDB.getItemDao().insert(new ItemEntity("Elephant", 500, "abc", R.drawable.elephant));
        HDB.getItemDao().insert(new ItemEntity("Fish", 600, "abc", R.drawable.fish));
        HDB.getItemDao().insert(new ItemEntity("Giraffe", 700, "abc", R.drawable.giraffe));
        HDB.getItemDao().insert(new ItemEntity("Hen", 800, "abc", R.drawable.hen));
        HDB.getItemDao().insert(new ItemEntity("Igloo", 900, "abc", R.drawable.igloo));
        HDB.getItemDao().insert(new ItemEntity("Jaguar", 1000, "abc", R.drawable.jaguar));
        HDB.getItemDao().insert(new ItemEntity("Kite", 1100, "abc", R.drawable.kite));
        HDB.getItemDao().insert(new ItemEntity("Lion", 1200, "abc", R.drawable.lion));
        HDB.getItemDao().insert(new ItemEntity("Moon", 1300, "abc", R.drawable.moon));
        HDB.getItemDao().insert(new ItemEntity("Necklace", 1400, "abc", R.drawable.necklace));
    }

    private void changeFragment(FrameLayout frameLayout, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    //---------------------------------------------------------------------------------------//
}