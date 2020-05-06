package com.example.myrecyclerview.presentation.view;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecyclerview.R;
import com.example.myrecyclerview.presentation.model.Categories;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Categories> values;
    private int[] image;
    private Context context;
    private String TAG = "RecyclerViewAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is two string and an image in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imageView;
        public View layout;

        //Constructeur
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader =  v.findViewById(R.id.firstLine);
            //txtFooter =  v.findViewById(R.id.secondLine);
            imageView =  v.findViewById(R.id.icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Categories> myDataset, int [] image, Context context) {
        values = myDataset;
        this.image = image;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreateViewHolder : created");
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "OnBindViewHolder : called");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Categories currentCategorie = values.get(position);
        final int image_id = image[position];
        holder.imageView.setImageResource(image_id);
        holder.txtHeader.setText(currentCategorie.getTitle());
        //holder.txtFooter.setText(currentCategorie.getDescription());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick : click on item "+image_id);
                //Toast.makeText(context,image_id,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, InfoActivity.class);
                //On récupère les données
                intent.putExtra("desc_1",currentCategorie.getDescription());
                intent.putExtra("image_1", image_id);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return image.length;
    }
}