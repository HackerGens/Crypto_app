package crypto.currency.crypto_app.Home_Frag;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.URLHelper;
import crypto.currency.crypto_app.Utils.VolleySingleton;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView_coins;
    private CoinsAdapter coins_adapter;
    public List<Coins_model> coinsList;
    String coin,currency;
    private RecyclerView recyclerView_coins_top;
    private TopCoinsAdapter coins_adapter_top;
    public List<Top_coins_model> coinsList_top;
    ProgressDialog progressDialog;
    public HomeFragment() {
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");

        // top coins list
        recyclerView_coins_top = root.findViewById(R.id.recycler_view_coins_top);
        coinsList_top = new ArrayList<>();
        coins_adapter_top = new TopCoinsAdapter(getActivity(), coinsList_top);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_coins_top.setLayoutManager(layoutManager);
        recyclerView_coins_top.addItemDecoration(new HomeFragment.GridSpacingItemDecoration(1, dpToPx(0), false));
        recyclerView_coins_top.setItemAnimator(new DefaultItemAnimator());
        recyclerView_coins_top.setAdapter(coins_adapter_top);
        coins_adapter_top.notifyDataSetChanged();
        //

        // coins list
        recyclerView_coins = root.findViewById(R.id.recycler_view_coins);
        coinsList = new ArrayList<>();
        coins_adapter = new CoinsAdapter(getActivity(), coinsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_coins.setLayoutManager(mLayoutManager);
        recyclerView_coins.addItemDecoration(new HomeFragment.GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView_coins.setItemAnimator(new DefaultItemAnimator());
        recyclerView_coins.setAdapter(coins_adapter);
        all_coins();
        return root;
    }

    public void all_coins(){
        progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLHelper.coins,
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
                                    String symbol = obj.getString("symbol");
                                    String name = obj.getString("name");

                                    JSONObject obj2 = (JSONObject)obj.get("image");
                                    String iconUrl = obj2.getString("large");
                                    JSONObject obj3 = (JSONObject)obj.get("market_data");
                                    JSONObject obj4 = (JSONObject)obj3.get("current_price");
                                    JSONObject obj5 = (JSONObject)obj3.get("market_cap");
                                    String market_cap_usd = obj5.getString("usd");
                                    String market_cap_btc = obj5.getString("btc");
                                    String btc = obj4.getString("btc");
                                    String usd = obj4.getString("usd");

                                    coinsList.add(new Coins_model(id,name, usd, iconUrl, btc,"+1.29%","$ "+market_cap_usd,market_cap_btc+" BTC"));
                                }
                                coins_adapter.notifyDataSetChanged();
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
        seven_coins_byUsers();
    }

    public void seven_coins_byUsers(){
        progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLHelper.coinstop_7,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(@Nullable String response) {
                        try {
                            if (response != null) {
                                JSONObject obj = new JSONObject(response);
                                JSONArray jArray = obj.getJSONArray("coins");
                                int length = jArray.length();
                                if (String.valueOf(jArray.length()).equals("0")) {
                                    progressDialog.dismiss();
                                    displayMessage("Oops! no item were found");
                                } else {
                                    for (int i = 0; i < length; i++) {
                                        JSONObject jObj = jArray.getJSONObject(i);
                                        JSONObject obj2 = (JSONObject)jObj.get("item");
                                        String name = obj2.getString("name");
                                        String symbol = obj2.getString("symbol");
                                        String iconUrl    = obj2.getString("large");
                                        String marketRank    = obj2.getString("market_cap_rank");
                                        String btcPrice   = obj2.getString("price_btc");
                                     //  String btcPrice    = String.valueOf(btcPrice_int);
                                        String alphaRefined = btcPrice.replaceAll("[^\\d-]", "");
                                        String numberRefined = btcPrice.replaceAll("[^0-9.]", "");

                                        coinsList_top.add(new Top_coins_model(name,"Rank "+marketRank,iconUrl,numberRefined,symbol));


                                    }
                                    progressDialog.dismiss();
                                    coins_adapter_top.notifyDataSetChanged();
                                }
                            } else {
                                progressDialog.dismiss();
                                displayMessage(getString(R.string.SomethingWrong));
                            }

                        } catch (JSONException e) {
                            displayMessage(getString(R.string.SomethingWrong));
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        displayMessage(getString(R.string.SomethingWrong));
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