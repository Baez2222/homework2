package com.example.android.hw2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder>{

    private List<Brew> brews;
    private List<Brew> brewsFavorited;
    private Context context;

    // pass this list into the constructor of the adapter
    public BrewAdapter(List<Brew> brews, Context context) {
        this.brews  = brews;
        this.brewsFavorited = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // used to inflate a layout from xml and return the ViewHolder
        // very standard code/template looking code
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the custom layout
        View brewView = inflater.inflate(R.layout.item_brew, parent, false);
        // return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(brewView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through holder

        Brew brew = brews.get(position);

        // set the view based on the data and the view names
        holder.textView_name.setText(brew.getName());
        holder.textView_description.setText(brew.getDescription());
        Picasso.get().load(brew.getImg_url()).into(holder.imageView_brew);





        // set imageView to assets image
        InputStream inputStream = null;
        try {
            if (brewsFavorited.contains(brew)){
                inputStream = context.getAssets().open("favorite.png");
                Drawable icon = Drawable.createFromStream(inputStream, null);
                holder.imageView_fav.setImageDrawable(icon);
            }
            else {
                inputStream = context.getAssets().open("favorite_not.png");
                Drawable icon = Drawable.createFromStream(inputStream, null);
                holder.imageView_fav.setImageDrawable(icon);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }







        // TODO add fav icon

    }

    @Override
    public int getItemCount() {
        return brews.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_name;
        TextView textView_description;
        ImageView imageView_brew;
        LinearLayout linearLayout;
        ImageView imageView_fav;
        Context context;

        public ViewHolder (View itemView){
            super(itemView);

            linearLayout = itemView.findViewById(R.id.LL_item_clickable);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_brew = itemView.findViewById(R.id.imageView_brew);
            imageView_fav = itemView.findViewById(R.id.imageView_fav);
            context = linearLayout.getContext();


            linearLayout.setOnClickListener(this);
            imageView_fav.setOnClickListener(v -> setIcon());

        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, FourthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name", textView_name.getText().toString());
            context.startActivity(intent);
        }

        public void setIcon(){
            int selected = getAdapterPosition();
            Brew selectedB = brews.get(selected);
            if(brewsFavorited.contains(selectedB)){
                brewsFavorited.remove(selectedB);
            }
            else{
                brewsFavorited.add(selectedB);
            }
            notifyDataSetChanged();
        }

    }
}
