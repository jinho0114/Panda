package com.example.firebasetest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;
import com.example.firebasetest.models.Post;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public void setDatas(List<Post> datas) {
        this.datas = datas;
    }

    private List<Post> datas;

    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data = datas.get(position); //position은 0,1,2 위에서부터 아래
        holder.name.setText("작성자 : " +data.getName());
        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContent());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;
        private TextView name;

         public PostViewHolder(@NonNull View itemView) {
             super(itemView);
             name = itemView.findViewById(R.id.item_post_name);
             title = itemView.findViewById(R.id.item_post_title);
             content = itemView.findViewById(R.id.item_post_contents);
         }
     }

}
