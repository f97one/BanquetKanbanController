package net.formula97.banquetkanbancontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import net.formula97.banquetkanbancontroller.adapters.SummaryListAdapter;
import net.formula97.banquetkanbancontroller.entities.BanquetSummary;
import net.formula97.banquetkanbancontroller.models.BanquetSummaryModel;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.summaryList)
    ListView summaryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        BanquetSummaryModel model = new BanquetSummaryModel(getApplicationContext());
        try {
            List<BanquetSummary> banquetSummaries = model.findAll();

            SummaryListAdapter adapter = new SummaryListAdapter(this, R.layout.sumamry_row, banquetSummaries);
            summaryList.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.summary, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean ret = false;

        switch (item.getItemId()) {
            case R.id.addBanquet:

                ret = true;
                break;
        }

        return ret;
    }
}
