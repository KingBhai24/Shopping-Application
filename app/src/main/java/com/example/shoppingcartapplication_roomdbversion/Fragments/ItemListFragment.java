package com.example.shoppingcartapplication_roomdbversion.Fragments;
//---------------------------------------------------------------------------------------//

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Enums.ActionMenuModeEnum;
import com.example.shoppingcartapplication_roomdbversion.Enums.RecyclerViewLayoutManagerEnum;
import com.example.shoppingcartapplication_roomdbversion.R;
import com.example.shoppingcartapplication_roomdbversion.RecyclerViewAdapters.ItemRecyclerViewAdapter;
import com.example.shoppingcartapplication_roomdbversion.ViewModelActivities.MainActivityViewModel;
import com.example.shoppingcartapplication_roomdbversion.ViewModelFragments.ItemListViewModel;

import java.util.HashSet;

//---------------------------------------------------------------------------------------//
public class ItemListFragment extends Fragment implements ItemRecyclerViewAdapter.Listener {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "ItemListFragment";
    private ItemListViewModel itemListViewModel;
    private MainActivityViewModel mainActivityViewModel;
    private Listener listener;
    private ItemRecyclerViewAdapter adp;
    private MenuItem menuItem1;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public ItemListFragment() {
        Log.i(LOG_TAG, "ItemListFragment()");
    }

    //---------------------------------------------------------------------------------------//
    //Factory Methods
    public static ItemListFragment newInstance() {
        Log.i(LOG_TAG, "newInstance()");
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //---------------------------------------------------------------------------------------//
    //Life Cycle Functions
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(LOG_TAG, "onAttach(Context context)");
        if (context instanceof Listener) {
            listener = (Listener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate(Bundle savedInstanceState) " + savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

        itemListViewModel = new ViewModelProvider(this).get(ItemListViewModel.class);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        View v = inflater.inflate(R.layout.fragment_list_item, container, false);
        EditText et1 = v.findViewById(R.id.FragmentListItem_Pt1);
        RecyclerView rv1 = v.findViewById(R.id.FragmentListItems_Rv1);
        Button bt1 = v.findViewById(R.id.FragmentListItems_Bt1);
        Button bt2 = v.findViewById(R.id.FragmentListItems_Bt2);

        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(getRecyclerViewLayoutManager());
        rv1.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        adp = new ItemRecyclerViewAdapter(this, itemListViewModel.getItems());
        rv1.setAdapter(adp);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((Filterable) adp).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt1.setOnClickListener(v1 -> {
            listener.launchFragmentListCartItems();
        });
        bt2.setOnClickListener(v1 -> {
            rv1.setLayoutManager(toggleRecyclerViewLayoutManager());
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(LOG_TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LOG_TAG, "onDetach()");
        listener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "onSaveInstanceState(Bundle outState)");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(LOG_TAG, "onViewStateRestored(Bundle savedInstanceState)");
    }

    //---------------------------------------------------------------------------------------//
    //Action Mode Menu
    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.i(LOG_TAG, "onCreateActionMode(ActionMode mode, Menu menu)");
            mode.getMenuInflater().inflate(R.menu.item_list_fragment_menu, menu);
            menuItem1 = menu.findItem(R.id.ItemListFragmentMenu_Mi1);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Log.i(LOG_TAG, "onPrepareActionMode(ActionMode mode, Menu menu)");
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.i(LOG_TAG, "onActionItemClicked(ActionMode mode, MenuItem item)");
            if (item.getItemId() == R.id.ItemListFragmentMenu_Mi2) {
                HashSet<Integer> temp = adp.getSelectedItems();
                itemListViewModel.addSelectedItems(temp);
                mode.finish();
                Toast.makeText(requireActivity(), R.string.ItemListFragment_SelectedItemsAdded, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            Log.i(LOG_TAG, "onDestroyActionMode(ActionMode mode)");
            adp.setMode(ActionMenuModeEnum.DEFAULT_MODE);
        }
    };

    //---------------------------------------------------------------------------------------//
    //ItemRecyclerViewAdapter.Listener
    @Override
    public void launchItemDetailFragment(ItemEntity itemEntity) {
        mainActivityViewModel.setItemEntityItemDetailFragment(itemEntity);
        listener.launchItemDetailFragment();
    }

    @Override
    public void startActionMode() {
        requireActivity().startActionMode(actionModeCallback);
    }

    @Override
    public void showSelectedItemsCount(int count) {
        menuItem1.setTitle(String.valueOf(count));
    }

    //---------------------------------------------------------------------------------------//
    public interface Listener {
        void launchItemDetailFragment();

        void launchFragmentListCartItems();
    }

    //---------------------------------------------------------------------------------------//
    //Class Utility Functions
    private RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        RecyclerView.LayoutManager layoutManager = null;
        RecyclerViewLayoutManagerEnum temp = itemListViewModel.getRecyclerViewLayoutManagerEnum();

        if (temp == RecyclerViewLayoutManagerEnum.LINEAR_LAYOUT)
            layoutManager = new LinearLayoutManager(requireActivity());
        else if (temp == RecyclerViewLayoutManagerEnum.GRID_LAYOUT)
            layoutManager = new GridLayoutManager(requireActivity(), 2);
        return layoutManager;
    }

    private RecyclerView.LayoutManager toggleRecyclerViewLayoutManager() {
        RecyclerView.LayoutManager layoutManager = null;
        RecyclerViewLayoutManagerEnum temp = itemListViewModel.getRecyclerViewLayoutManagerEnum();

        if (temp == RecyclerViewLayoutManagerEnum.LINEAR_LAYOUT) {
            layoutManager = new GridLayoutManager(requireActivity(), 2);
            itemListViewModel.setRecyclerViewLayoutManagerEnum(RecyclerViewLayoutManagerEnum.GRID_LAYOUT);
        } else if (temp == RecyclerViewLayoutManagerEnum.GRID_LAYOUT) {
            layoutManager = new LinearLayoutManager(requireActivity());
            itemListViewModel.setRecyclerViewLayoutManagerEnum(RecyclerViewLayoutManagerEnum.LINEAR_LAYOUT);
        }
        return layoutManager;
    }
    //---------------------------------------------------------------------------------------//
}