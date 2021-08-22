package crypto.currency.crypto_app.Exchange_Frag;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import crypto.currency.crypto_app.Home_Frag.CoinsAdapter;
import crypto.currency.crypto_app.Home_Frag.Coins_model;
import crypto.currency.crypto_app.Home_Frag.HomeFragment;
import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.URLHelper;
import crypto.currency.crypto_app.Utils.VolleySingleton;

public class ExchangeFragment extends Fragment {
    private RecyclerView recyclerView_exchange;
    private ExchangeAdapter exchange_adapter;
    public List<Exchange_model> exchangeList;
    ProgressDialog progressDialog;

    public ExchangeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_exchange, container, false);
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        recyclerView_exchange = root.findViewById(R.id.recycler_view_exchange);
        exchangeList = new ArrayList<>();
        exchange_adapter= new ExchangeAdapter(getActivity(), exchangeList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_exchange.setLayoutManager(mLayoutManager);
        recyclerView_exchange.addItemDecoration(new ExchangeFragment.GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView_exchange.setItemAnimator(new DefaultItemAnimator());
        recyclerView_exchange.setAdapter(exchange_adapter);
        all_exchange();


        return root;
    }

    public void all_exchange(){
       // progressDialog.show();
      //  progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLHelper.exchanges,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(@Nullable String response) {
                        try {
                            if (response != null) {
                                JSONArray jArray = new JSONArray(response);
                                int length = jArray.length();
                                for (int i = 0; i < length; i++) {
                                    JSONObject obj = jArray.getJSONObject(i);
                                    String id = obj.getString("id");
                                    String name = obj.getString("name");
                                    String country = obj.getString("country");
                                    String image = obj.getString("image");
                                    String year_established = obj.getString("year_established");
                                    String trust_score_rank = obj.getString("trust_score_rank");
                                    String trade_volume_24h_btc = obj.getString("trade_volume_24h_btc");
                                    String url = obj.getString("url");

                                    exchangeList.add(new Exchange_model(id,name,url,image,trade_volume_24h_btc,country,trust_score_rank,year_established));
                                }
                                exchange_adapter.notifyDataSetChanged();
                            } else {
                                progressDialog.dismiss();
                                displayMessage("Oops! Some thing went wrong");
                            }

                        } catch (JSONException e) {
                            displayMessage(getString(R.string.SomethingWrong));
                            progressDialog.dismiss();
                            displayMessage("Oops! Some thing went wrong");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        progressDialog.dismiss();
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = 5; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = 5; // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = 5;
                }
            } else {
                outRect.left = 15; // column * ((1f / spanCount) * spacing)
                outRect.right = 15; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = 5; // item top
                }
            }
        }
    }
}