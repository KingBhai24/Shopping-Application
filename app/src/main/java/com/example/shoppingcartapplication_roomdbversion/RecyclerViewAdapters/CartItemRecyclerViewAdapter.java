package com.example.shoppingcartapplication_roomdbversion.RecyclerViewAdapters;
//---------------------------------------------------------------------------------------//

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcartapplication_roomdbversion.Enums.ActionMenuModeEnum;
import com.example.shoppingcartapplication_roomdbversion.Objects.CartItemObject;
import com.example.shoppingcartapplication_roomdbversion.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//---------------------------------------------------------------------------------------//
public class CartItemRecyclerViewAdapter extends RecyclerView.Adapter<CartItemRecyclerViewAdapter.CartItemViewHolder> {
    //---------------------------------------------------------------------------------------//
    //Attributes
    private static final String LOG_TAG = "CartItemRecyclerViewAdapter";
    private final Listener listener;
    private List<CartItemObject> cartItems;
    private ActionMenuModeEnum actionMenuModeEnum;
    private final HashSet<Integer> selectedItems;

    //---------------------------------------------------------------------------------------//
    //Constructors
    public CartItemRecyclerViewAdapter(Listener listener) {
        this.actionMenuModeEnum = ActionMenuModeEnum.DEFAULT_MODE;
        this.listener = listener;
        this.cartItems = new ArrayList<>();
        this.selectedItems = new HashSet<>();
    }

    //---------------------------------------------------------------------------------------//
    //Getters and Setters
    public void setCartItems(List<CartItemObject> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public HashSet<Integer> getSelectedItems() {
        return selectedItems;
    }

    //---------------------------------------------------------------------------------------//
    //View Holder Class
    public class CartItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv1;
        private final TextView name, price, count;
        private final Button dec, inc;
        private final CheckBox cb1;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(LOG_TAG, "CartItemViewHolder(View itemView) " + itemView);
            iv1 = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Iv1);
            name = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Tv1);
            price = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Tv2);
            count = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Tv3);
            dec = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Bt1);
            inc = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Bt2);
            cb1 = itemView.findViewById(R.id.CartItemRecyclerViewAdapterViewHolder_Cb1);

            dec.setOnClickListener(v -> {
                int pos = (int) v.getTag();
                listener.countDecreased(cartItems.get(pos).getIdentity());
            });
            inc.setOnClickListener(v -> {
                int pos = (int) v.getTag();
                listener.countIncreased(cartItems.get(pos).getIdentity());
            });
            itemView.setOnLongClickListener(v -> {
                Log.i(LOG_TAG, "CartItemViewHolder.setOnLongClickListener(v ->{}) " + v);
                if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE) {
                    listener.startActionMode();
                    setMode(ActionMenuModeEnum.ACTION_MODE);
                    cb1.callOnClick();
                    return true;
                }
                return false;
            });
            itemView.setOnClickListener(v -> {
                Log.i(LOG_TAG, "CartItemViewHolder.setOnClickListener(v ->{}) " + v);
                if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
                    cb1.callOnClick();
                }
            });
            cb1.setOnClickListener(v -> {
                Log.i(LOG_TAG, "CartItemViewHolder.Checkbox.setOnClickListener(v ->{}) " + v);
                int pos = (int) v.getTag();
                if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
                    int id = cartItems.get(pos).getIdentity();
                    if (selectedItems.contains(id))
                        selectedItems.remove(id);
                    else
                        selectedItems.add(id);
                    listener.showSelectedItemsCount(selectedItems.size());
                    notifyItemChanged(pos);
                }
            });
        }
    }

    //---------------------------------------------------------------------------------------//
    @NonNull
    @Override
    public CartItemRecyclerViewAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(LOG_TAG, "onCreateViewHolder(ViewGroup parent, int viewType) " + parent + ", " + viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycler_view_adapter_view_holder, parent, false);
        return new CartItemRecyclerViewAdapter.CartItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemRecyclerViewAdapter.CartItemViewHolder holder, int position) {
        Log.i(LOG_TAG, "onBindViewHolder(CartItemViewHolder holder, int position) " + holder + ", " + position);
        holder.iv1.setImageResource(cartItems.get(position).getImage());
        holder.name.setText(String.valueOf(cartItems.get(position).getName()));
        holder.price.setText("$" + cartItems.get(position).getPrice());
        holder.count.setText(String.valueOf(cartItems.get(position).getCount()));

        holder.inc.setTag(position);
        holder.dec.setTag(position);
        holder.cb1.setTag(position);
        holder.itemView.setTag(position);

        if (actionMenuModeEnum == ActionMenuModeEnum.DEFAULT_MODE) {
            holder.cb1.setVisibility(View.INVISIBLE);
            holder.cb1.setChecked(false);
        } else if (actionMenuModeEnum == ActionMenuModeEnum.ACTION_MODE) {
            if (selectedItems.contains(cartItems.get(position).getIdentity())) {
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
        Log.i(LOG_TAG, "getItemCount()");
        return cartItems.size();
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
        void countIncreased(int identity);

        void countDecreased(int identity);

        void startActionMode();

        void showSelectedItemsCount(int count);
    }
}
