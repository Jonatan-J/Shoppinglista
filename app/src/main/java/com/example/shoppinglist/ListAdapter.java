package com.example.shoppinglist;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context myContext;
    private Cursor myCursor;

    public ListAdapter(Context context, Cursor cursor) {
        myContext = context;
        myCursor = cursor;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView countText;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText =  itemView.findViewById(R.id.name_item);
            countText = itemView.findViewById(R.id.amnt_item);
        }
    }

    @NonNull

    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (!myCursor.moveToPosition(position)) {
            return;
        }

        String name = myCursor.getString(myCursor.getColumnIndex(ShoppingListContract.AddItemInput.NAME_COLUMN));
        int amount = myCursor.getInt(myCursor.getColumnIndex(ShoppingListContract.AddItemInput.AMOUNT_COLUMN));
        long id = myCursor.getLong(myCursor.getColumnIndex(ShoppingListContract.AddItemInput._ID));

        holder.itemView.setTag(id);
        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));

    }

    @Override
    public int getItemCount() {
        return myCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (myCursor!=null) {
            myCursor.close();
        }
        System.out.println(newCursor.getCount());
        myCursor = newCursor;

        if (newCursor!=null) {
            notifyDataSetChanged();
        }
    }
}
