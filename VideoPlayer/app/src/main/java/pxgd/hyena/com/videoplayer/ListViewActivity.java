package pxgd.hyena.com.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("ListView");
        setContentView(R.layout.activity_list_view);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void clickNormal(View view) {
       startActivity(new Intent(ListViewActivity.this, ListViewNormalActivity.class));
    }
    public void clickListViewFragmentViewpager(View view) {
        startActivity(new Intent(ListViewActivity.this, ListViewFragmentActivity.class));
    }
    public void clickMultiHolder(View view) {
        startActivity(new Intent(ListViewActivity.this, ListViewHolderActivity.class));
    }
    public void clickRecyclerView(View view) {
        startActivity(new Intent(ListViewActivity.this, ListViewRecycleActivity.class));
    }
}
