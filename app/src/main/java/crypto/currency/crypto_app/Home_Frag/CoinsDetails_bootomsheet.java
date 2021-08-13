package crypto.currency.crypto_app.Home_Frag;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import crypto.currency.crypto_app.R;

public class CoinsDetails_bootomsheet extends BottomSheetDialogFragment {
    private LineChart lineChart;
    List<Entry> closePrices = new ArrayList<>();
    ImageView btn_details;
    Button btn_movetosend,btn_movetoreceive;
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
        /*Graph code here*/
        setUpChart();
        closePrices.clear();
        for (int i = 0;  i < 5; i++) {
            closePrices.add(new Entry(i, (float) ((Math.random() * 10000.89)+1000)));
        }

        LineDataSet dataSet = setUpLineDataSet(closePrices);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.animateX(10);
        /*End graph code*/

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
}