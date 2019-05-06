package com.example.tundex_droid;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PaymentActivity extends Activity implements View.OnClickListener {
    Button btn_back;
    final String LOG_TAG = "myLogs";

    ListView lvMain;
    String[] payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.payment,
                android.R.layout.simple_list_item_single_choice);
        lvMain.setAdapter(adapter);

        // получаем массив из файла ресурсов
        payment = getResources().getStringArray(R.array.payment);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:

                onBackPressed();
                break;
            default:
                break;
        }
    }
}
