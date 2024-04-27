package com.example.shoppingcartapplication_roomdbversion.Fragments;
//---------------------------------------------------------------------------------------//

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcartapplication_roomdbversion.Enums.ActionMenuModeEnum;
import com.example.shoppingcartapplication_roomdbversion.Objects.CartItemObject;
import com.example.shoppingcartapplication_roomdbversion.R;
import com.example.shoppingcartapplication_roomdbversion.RecyclerViewAdapters.CartItemRecyclerViewAdapter;
import com.example.shoppingcartapplication_roomdbversion.ViewModelFragments.CartItemListViewModel;

import java.util.HashSet;
import java.util.List;

//---------------------------------------------------------------------------------------//
public class CartItemListFragment extends Fragment implements CartItemRecyclerViewAdapter.Listener {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "CartItemListFragment";
    private CartItemRecyclerViewAdapter adp;
    private MenuItem menuItem1;
    private CartItemListViewModel cartItemListViewModel;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public CartItemListFragment() {
        Log.i(LOG_TAG, "CartItemListFragment()");
    }

    //---------------------------------------------------------------------------------------//
    //Factory Functions
    public static CartItemListFragment newInstance() {
        Log.i(LOG_TAG, "newInstance()");
        CartItemListFragment fragment = new CartItemListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //---------------------------------------------------------------------------------------//
    //LifeCycle Functions
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(LOG_TAG, "onAttach(Context context)");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate(Bundle savedInstanceState) " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) " + inflater + " " + container + " " + savedInstanceState);

        cartItemListViewModel = new ViewModelProvider(this).get(CartItemListViewModel.class);

        View v = inflater.inflate(R.layout.fragment_cart_item_list, container, false);
        RecyclerView rv1 = v.findViewById(R.id.FragmentCartItemList_Rv1);
        TextView tv2 = v.findViewById(R.id.FragmentCartItemList_Tv2);

        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rv1.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        adp = new CartItemRecyclerViewAdapter(this);
        rv1.setAdapter(adp);

        cartItemListViewModel.getCartItemEntities().observe(getViewLifecycleOwner(), cartItemEntities -> {
            Log.i(LOG_TAG, "cartItemListViewModel.getCartItemEntities().observe(getViewLifecycleOwner(), cartItemEntities -> {}");
            List<CartItemObject> temp = cartItemListViewModel.getCartItemObjects(cartItemEntities);
            adp.setCartItems(temp);
            tv2.setText(calculateCartItemsTotal(temp));
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
            mode.getMenuInflater().inflate(R.menu.cart_item_list_fragment_menu, menu);
            menuItem1 = menu.findItem(R.id.CartItemListFragmentMenu_Mi1);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Log.i(LOG_TAG, "onPrepareActionMode(ActionMode mode, Menu menu)");
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.i(LOG_TAG, "onActionItemClicked(ActionMode mode, MenuItem item)");
            if (item.getItemId() == R.id.CartItemListFragmentMenu_Mi2) {
                HashSet<Integer> temp = adp.getSelectedItems();
                cartItemListViewModel.deleteSelectedCartItems(temp);
                Toast.makeText(requireActivity(), R.string.CartItemListFragment_SelectedItemsRemoved, Toast.LENGTH_SHORT).show();
                mode.finish();
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
    //Class Utility Functions
    private String calculateCartItemsTotal(List<CartItemObject> cartItemObjects) {
        int sum = 0;
        for (CartItemObject n : cartItemObjects) {
            sum = sum + n.getPrice() * n.getCount();
        }
        String p = "$" + sum;
        return p;
    }

    //---------------------------------------------------------------------------------------//
    //CartItemRecyclerViewAdapter.Listener
    @Override
    public void countIncreased(int identity) {
        cartItemListViewModel.countIncreased(identity);
    }

    @Override
    public void countDecreased(int identity) {
        cartItemListViewModel.countDecreased(identity);
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
}