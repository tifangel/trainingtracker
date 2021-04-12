package com.example.workout.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.Model.News;
import com.example.workout.R;

import java.util.List;

public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.SportsNewsViewHolder> {
    private List<News> newsList;

    public SportsNewsAdapter(List<News> newsList){
        this.newsList = newsList;
    }

    @Override
    public SportsNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_view, parent, false);
        return new SportsNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SportsNewsViewHolder holder, int position) {
        String title = newsList.get(position).getTitle();
        String author = "Author: " + newsList.get(position).getAuthor();
        String desc = newsList.get(position).getDescription();
        holder.txtNewsTitle.setText(title);
        holder.txtNewsAuthor.setText(author);
        holder.txtNewsDesc.setText(desc);
//        String url = newsList.get(position).getLogo();

//        holder.txtNamaRadio.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                int id = newsList.get(position).getId();
//
//            }
    }

    @Override
    public int getItemCount() {
        return (newsList != null) ? newsList.size() : 0;
    }

    public class SportsNewsViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNewsDesc, txtNewsTitle, txtNewsAuthor;

        public SportsNewsViewHolder(View itemView){
            super(itemView);
            txtNewsTitle = (TextView) itemView.findViewById(R.id.txt_news_title);
            txtNewsAuthor = (TextView) itemView.findViewById(R.id.txt_news_author);
            txtNewsDesc = (TextView) itemView.findViewById(R.id.txt_news_desc);
        }
    }

    public interface OnNewsListener{
        void onNewsClick(int position);
    }
}
