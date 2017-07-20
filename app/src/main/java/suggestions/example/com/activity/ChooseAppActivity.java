package suggestions.example.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import suggestions.example.com.adapter.CountSectionAdapter;
import suggestions.example.com.bean.CountItemViewHolder;
import suggestions.example.com.utils.FileUtils;
import suggestions.example.com.R;
import suggestions.example.com.bean.Items;

/**
 * Created by root on 17-7-13.
 */

public class ChooseAppActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recycler;
    private Toolbar tool_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseitem);
        intView();
    }

    private void intView() {
        tool_bar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Items allData = FileUtils.getDefaultJson(this);
        CountSectionAdapter countSectionAdapter = new CountSectionAdapter(LayoutInflater.from(this),allData ,this);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(countSectionAdapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        recycler.setAdapter(countSectionAdapter);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {

        if(v.getTag() instanceof CountItemViewHolder){
            String s = ((CountItemViewHolder) v.getTag()).textView.getText().toString();
            Intent intent = new Intent();
            intent.setAction(s);
            setResult(RESULT_OK,intent);
            Log.e("text_click",s);
            finish();
        }
    }
}
