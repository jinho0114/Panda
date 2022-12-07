package com.example.firebasetest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;
import com.example.firebasetest.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> datas;

    public CommentAdapter(List<Comment> datas) {
        this.datas = datas;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment data = datas.get(position); //position은 0,1,2 위에서부터 아래
        holder.name.setText("작성자 : " +data.getName());
        holder.content.setText(data.getContent());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        private TextView content;
        private TextView name;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_post_name);
            content = itemView.findViewById(R.id.comment_post_contents);
        }
    }

}
