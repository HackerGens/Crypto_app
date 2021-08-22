package crypto.currency.crypto_app.Home_Frag;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.SharedHelper;
import crypto.currency.crypto_app.Utils.URLHelper;
import crypto.currency.crypto_app.Utils.VolleySingleton;

public class CoinsDetails_bootomsheet extends BottomSheetDialogFragment {
    private LineChart lineChart;
    TextView txtheaderName,txtheaderPrice,txtbtcbalance,txtcurrentvalue,txtgain_loss,txtaverageprice;
    ImageView imageView;
    List<Entry> closePrices = new ArrayList<>();
    Intent i;
    public CoinsDetails_bootomsheet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.coins_details_bootom_sheet, container, false);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;

        lineChart = root.findViewById(R.id.chart);
        imageView = root.findViewById(R.id.coinheetimage);

        txtheaderName = root.findViewById(R.id.title_sheet);
        txtheaderPrice = root.findViewById(R.id.pricesheet);
        txtbtcbalance = root.findViewById(R.id.btc_balance);
        txtcurrentvalue = root.findViewById(R.id.currentvalue);
        txtgain_loss = root.findViewById(R.id.gainloss);
        txtaverageprice = root.findViewById(R.id.averageprice);


        /*Graph code here*/
        setUpChart();
        oneday_graph_data();

        LineDataSet dataSet = setUpLineDataSet(closePrices);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.animateX(10);
        /*End graph code*/
        single_coin_data();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        View view = getView();
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());

        });
    }

    public void setUpChart() {

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(false);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setContentDescription("");

    }

    public LineDataSet setUpLineDataSet(List<Entry> entries) {
        Drawable gradientDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_background_graph);
        LineDataSet dataSet = new LineDataSet(entries, "Price");
        dataSet.setColor(Color.parseColor("#0BA1B5"));
        dataSet.setCircleColor(Color.parseColor("#e5c48e"));
        dataSet.setCircleColor(Color.parseColor("#e5c48e"));
        dataSet.setFillColor(Color.parseColor("#FFCCD5"));
        dataSet.setFillDrawable(gradientDrawable);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setDrawHighlightIndicators(false);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawValues(false);
        dataSet.setCircleRadius((float) 1.5);
        dataSet.setHighlightLineWidth(1);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawHighlightIndicators(true);
        return dataSet;


    }

    public void single_coin_data(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.coingecko.com/api/v3/coins/bitcoin?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=true",
       // StringRequest stringRequest = new StringRequest(Request.Method.GET, URLHelper.single_coin_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(@Nullable String response) {
                        try {
                            if (response != null) {
                                JSONObject obj = new JSONObject(response);
                                String id = obj.getString("id");
                                String symbol = obj.getString("symbol");
                                String name = obj.getString("name");
                                JSONObject obj2 = (JSONObject)obj.get("image");
                                String iconUrl = obj2.getString("large");
                                JSONObject obj3 = (JSONObject)obj.get("market_data");
                                JSONObject obj4 = (JSONObject)obj3.get("current_price");
                                JSONObject obj5 = (JSONObject)obj3.get("market_cap");
                                JSONObject obj6 = (JSONObject)obj3.get("sparkline_7d");

                                String usd = obj4.getString("usd");
                                String btc = obj4.getString("btc");

                                String market_usd = obj5.getString("usd");
                                String market_btc = obj5.getString("btc");

                                txtheaderName.setText(name);
                                txtheaderPrice.setText(usd);
                                txtbtcbalance.setText(btc);


//                                txtheaderName = root.findViewById(R.id.title_sheet);
//                                txtheaderPrice = root.findViewById(R.id.pricesheet);
//                                txtbtcbalance = root.findViewById(R.id.btc_balance);
//                                txtcurrentvalue = root.findViewById(R.id.currentvalue);
//                                txtgain_loss = root.findViewById(R.id.gainloss);
//                                txtaverageprice = root.findViewById(R.id.averageprice);

                                txtcurrentvalue.setText(market_usd);
                                txtaverageprice.setText(market_btc);

                                Glide.with(getActivity())
                                        .load(iconUrl)
                                        .centerCrop()
                                        .into(imageView);

//                                JSONArray jArray = obj6.getJSONArray("price");
//                                int length = jArray.length();
//                                if (String.valueOf(jArray.length()).equals("0")) {
//                                    closePrices.clear();
//                                    displayMessage("Oops! no item were found");
//                                } else {
//                                    closePrices.clear();
//                                    for (int i = 0; i < length; i++) {
//                                        JSONArray arrayInArray = jArray.getJSONArray(i);
//                                        String graph_index = arrayInArray.getString(i);
//                                        float float_data = Float.parseFloat(graph_index);
//                                        closePrices.add(new Entry(i, float_data));
//                                        LineDataSet dataSet = setUpLineDataSet(closePrices);
//                                        LineData lineData = new LineData(dataSet);
//                                        lineChart.setData(lineData);
//                                        lineChart.animateX(10);
//                                    }
//                               }
                            } else {
                                closePrices.clear();
                                displayMessage(getString(R.string.SomethingWrong));
                            }

                        } catch (JSONException e) {
                            closePrices.clear();
                            displayMessage(getString(R.string.SomethingWrong));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        closePrices.clear();
                        displayMessage(getString(R.string.SomethingWrong));
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void oneday_graph_data(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLHelper.graph_data+"/"+SharedHelper.getKey(getActivity(),"coin_name")+"/market_chart?vs_currency=usd&days=7&interval=daily",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(@Nullable String response) {
                        try {
                            if (response != null) {
                                JSONObject obj = new JSONObject(response);
                                JSONArray jArray = obj.getJSONArray("prices");
                                int length = jArray.length();
                                if (String.valueOf(jArray.length()).equals("0")) {
                                    closePrices.clear();
                                    displayMessage("Oops! no item were found");
                                } else {
                                    closePrices.clear();
                                    for (int i = 0; i < length; i++) {
                                        JSONArray arrayInArray = jArray.getJSONArray(i);
                                        for (int j = 0; j < arrayInArray.length(); j++) {
                                            String graph_index = arrayInArray.getString(j);
                                            float float_data = Float.parseFloat(graph_index);
                                            closePrices.add(new Entry(i, float_data));
                                            LineDataSet dataSet = setUpLineDataSet(closePrices);
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.animateX(10);
                                        }
                                    }
                                }
                            } else {
                                closePrices.clear();
                                displayMessage(getString(R.string.SomethingWrong));
                            }

                        } catch (JSONException e) {
                            closePrices.clear();
                            displayMessage(getString(R.string.SomethingWrong));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        closePrices.clear();
                        displayMessage(getString(R.string.SomethingWrong));
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void displayMessage(@NonNull String toastString) {
        try {
            Snackbar.make(getActivity().getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } catch (Exception e) {
            displayMessage(getString(R.string.SomethingWrong));
            Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        }
    }
}