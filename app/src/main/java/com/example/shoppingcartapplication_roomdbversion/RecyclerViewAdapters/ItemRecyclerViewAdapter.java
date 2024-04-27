package com.example.shoppingcartapplication_roomdbversion.RecyclerViewAdapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcartapplication_roomdbversion.Entities.ItemEntity;
import com.example.shoppingcartapplication_roomdbversion.Enums.ActionMenuModeEnum;
import com.example.shoppingcartapplication_roomdbversion.R;
import com.example.shoppingcartapplication_roomdbversion.ViewModelFragments.ItemListViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> implements Filterable {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private final String LOG_TAG = "ItemRecyclerViewAdapter";

    private final Listener listener;
    private final List<ItemEntity> items;
    private List<ItemEntity> filteredItems;
    private final HashSet<Integer> selectedItems;
    private ActionMenuModeEnum actionMenuModeEnum;
    private Filter filter;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public ItemRecyclerViewAdapter(Listener listener, List<ItemEntity> items) {
        Log.i(LOG_TAG, "ItemRecyclerViewAdapter(Listener listener, List<ItemEntity> items)");
        this.listener = listener;
        this.items = items;
        this.filteredItems = items;
        this.selectedItems = new HashSet<>();
        actionMenuModeEnum = ActionMenuModeEnum.DEFAULT_MODE;
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setters

    public HashSet<Integer> getSelectedItems() {
        return selectedItems;
    }

    //---------------------------------------------------------------------------------------//
    //View Holder Class
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView name, price;
        public final ImageView iv1;
        public final CheckBox cb1;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Log.i(LOG_TAG, "ItemViewHolder(View itemView) " + itemView);
            name = itemView.findViewById(R.id.ItemRecyclerViewAdapterViewHolder_Tv1);
            price = itemView.findViewById(R.id.ItemRecyclerViewAdapterViewHolder_Tv2);
            iv1 = itemView.findViewById(R.id.ItemRecyclerViewAdapterViewHolder_Iv1);
            cb1 = itemView.findViewById(R.id.ItemRecyclerViewAdapterViewHolder_Cb1);

            itemView.setOnClickListener(v -> {
                Log.i(LOG_TAG, "ItemViewHolder.setOnClickListener(v ->{}) " + v);
                int pos = (int) v.getTag();
                if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE)
                    listener.launchItemDetailFragment(filteredItems.get(pos));
                else if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
                    cb1.callOnClick();
                }
            });
            itemView.setOnLongClickListener(v -> {
                Log.i(LOG_TAG, "ItemViewHolder.setOnLongClickListener(v ->{}) " + v);
                if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE) {
                    listener.startActionMode();
                    setMode(ActionMenuModeEnum.ACTION_MODE);
                    cb1.callOnClick();
                    return true;
                }
                return false;
            });
            cb1.setOnClickListener(v -> {
                Log.i(LOG_TAG, "ItemViewHolder.Checkbox.setOnClickListener(v ->{}) " + v);
                int pos = (int) v.getTag();
                if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
                    int id = filteredItems.get(pos).getIdentity();
                    if (selectedItems.contains(id))
                        selectedItems.remove(id);
                    else
                        selectedItems.add(id);
                    notifyItemChanged(pos);
                    listener.showSelectedItemsCount(selectedItems.size());
                }
            });
        }
    }

    //---------------------------------------------------------------------------------------//
    @Override
    public ItemRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(LOG_TAG, "onCreateViewHolder(ViewGroup parent, int viewType) " + parent + ", " + viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_adapter_view_holder, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerViewAdapter.ItemViewHolder holder, int position) {
        Log.i(LOG_TAG, "onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) " + holder + ", " + position);
        String p = "$" + filteredItems.get(position).getPrice();
        holder.name.setText(filteredItems.get(position).getName());
        holder.price.setText(p);
        holder.iv1.setImageResource(filteredItems.get(position).getImage());

        holder.cb1.setTag(position);
        holder.itemView.setTag(position);

        if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE) {
            holder.cb1.setVisibility(View.INVISIBLE);
            holder.cb1.setChecked(false);
        } else if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
            if (selectedItems.contains(filteredItems.get(position).getIdentity())) {
                holder.cb1.setVisibility(View.VISIBLE);
                holder.cb1.setChecked(true);
            } else {
                holder.cb1.setVisibility(View.INVISIBLE);
                holder.cb1.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.i(LOG_TAG, "getItemCount() " + filteredItems.size());
        return filteredItems.size();
    }

    //---------------------------------------------------------------------------------------//
    //Filterable
    @Override
    public android.widget.Filter getFilter() {
        Log.i(LOG_TAG, "getFilter()");
        if (filter == null) {
            filter = new ItemFilter();
        }
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i(LOG_TAG, "performFiltering(CharSequence constraint) " + constraint);
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<ItemEntity> filteredList = new ArrayList<>();
                for (ItemEntity i : items) {
                    if (i.getName().contains(constraint))
                        filteredList.add(i);
                }
                results.count = filteredList.size();
                results.values = filteredList;
            } else {
                results.count = items.size();
                results.values = items;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.i(LOG_TAG, "publishResults(CharSequence constraint, FilterResults results) " + constraint + ", " + results);
            filteredItems = (List<ItemEntity>) results.values;
            notifyDataSetChanged();
        }
    }

    //---------------------------------------------------------------------------------------//
    //Called From Parent
    public void setMode(ActionMenuModeEnum n) {
        actionMenuModeEnum = n;
        if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE) {
            selectedItems.clear();
            notifyDataSetChanged();
        }
    }

    //---------------------------------------------------------------------------------------//
    public interface Listener {
        void launchItemDetailFragment(ItemEntity item);

        void startActionMode();

        void showSelectedItemsCount(int count);
    }
    //---------------------------------------------------------------------------------------//


}
