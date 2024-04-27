package com.example.shoppingcartapplication_roomdbversion.Fragments;
//---------------------------------------------------------------------------------------//

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppingcartapplication_roomdbversion.Entities.CartItemEntity;
import com.example.shoppingcartapplication_roomdbversion.R;
import com.example.shoppingcartapplication_roomdbversion.ViewModelActivities.MainActivityViewModel;
import com.example.shoppingcartapplication_roomdbversion.ViewModelFragments.ItemDetailViewModel;

//---------------------------------------------------------------------------------------//
public class ItemDetailFragment extends Fragment {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "ItemDetailFragment";

    //---------------------------------------------------------------------------------------//
    //Constructors
    public ItemDetailFragment() {
        Log.i(LOG_TAG, "ItemDetailFragment()");
    }

    //---------------------------------------------------------------------------------------//
    //Factory Methods
    public static ItemDetailFragment newInstance() {
        Log.i(LOG_TAG, "newInstance()");
        ItemDetailFragment fragment = new ItemDetailFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    //---------------------------------------------------------------------------------------//
    //Life Cycle Functions
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(LOG_TAG, "onAttach(Context context)");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate(Bundle savedInstanceState) " + savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) " + inflater + " " + container + " " + savedInstanceState);

        ItemDetailViewModel itemDetailViewModel = new ViewModelProvider(this).get(ItemDetailViewModel.class);
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        View v = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ImageView iv1 = v.findViewById(R.id.FragmentItemDetails_Iv1);
        TextView tv1 = v.findViewById(R.id.FragmentItemDetails_Tv1);
        TextView tv2 = v.findViewById(R.id.FragmentItemDetails_Tv2);
        TextView tv3 = v.findViewById(R.id.FragmentItemDetails_Tv3);
        Button bt1 = v.findViewById(R.id.FragmentItemDetails_Bt1);

        mainActivityViewModel.getItemEntityItemDetailFragment().observe(getViewLifecycleOwner(), itemEntity -> {
            Log.i(LOG_TAG, "mainActivityViewModel.getItemEntityItemDetailFragment().observe(getViewLifecycleOwner(), itemEntity -> {}");
            iv1.setImageResource(itemEntity.getImage());
            tv1.setText(itemEntity.getName());
            String p = "$" + itemEntity.getPrice();
            tv2.setText(p);
            tv3.setText(itemEntity.getDescription());
        });

        bt1.setOnClickListener(v1 -> {
            itemDetailViewModel.insertToCartItemEntity(CartItemEntity.getDefault(mainActivityViewModel.getItemEntityItemDetailFragment().getValue().getIdentity()));
            Toast.makeText(requireActivity(), R.string.ItemDetailFragment_SelectedItemAdded, Toast.LENGTH_SHORT).show();
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
}