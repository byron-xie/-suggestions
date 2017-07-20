package suggestions.example.com.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import suggestions.example.com.R;

/**
 * Created by tomas on 15/07/15.
 */
public class CountHeaderViewHolder extends RecyclerView.ViewHolder {

     TextView textView;

    public CountHeaderViewHolder(View itemView) {
        super(itemView);
         textView = (TextView)itemView.findViewById(R.id.title);
    }

    public void render(String text){
        textView.setText(text);
    }
}
