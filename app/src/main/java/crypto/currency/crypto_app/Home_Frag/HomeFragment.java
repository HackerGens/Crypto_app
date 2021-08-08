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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crypto.currency.crypto_app.Home_Frag.CoinsAdapter;
import crypto.currency.crypto_app.Home_Frag.Coins_model;
import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.SharedHelper;
import crypto.currency.crypto_app.Utils.URLHelper;
import crypto.currency.crypto_app.Utils.VolleySingleton;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView_coins;
    private CoinsAdapter coins_adapter;
    public List<Coins_model> coinsList;

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

        // top coins list

        recyclerView_coins_top = root.findViewById(R.id.recycler_view_coins_top);
        coinsList_top = new ArrayList<>();
        coins_adapter_top = new TopCoinsAdapter(getActivity(), coinsList_top);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView_coins_top.setLayoutManager(mLayoutManager);
        recyclerView_coins_top.addItemDecoration(new HomeFragment.GridSpacingItemDecoration(1, dpToPx(1), true));
        recyclerView_coins_top.setItemAnimator(new DefaultItemAnimator());
        recyclerView_coins_top.setAdapter(coins_adapter_top);
        coins_adapter_top.notifyDataSetChanged();


        //

        // coins list
        recyclerView_coins = root.findViewById(R.id.recycler_view_coins);
        coinsList = new ArrayList<>();
        coins_adapter = new CoinsAdapter(getActivity(), coinsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_coins.setLayoutManager(layoutManager);
        recyclerView_coins.addItemDecoration(new HomeFragment.GridSpacingItemDecoration(1, dpToPx(0), false));
        recyclerView_coins.setItemAnimator(new DefaultItemAnimator());
        recyclerView_coins.setAdapter(coins_adapter);

//        coinsList.add(new Coins_model("Bitcoin", "US$38,230.05", "https://toppng.com/uploads/preview/bitcoin-png-bitcoin-logo-transparent-background-11562933997uxok6gcqjp.png", "$558.28","+1.29%"));
//        coinsList.add(new Coins_model("Ethereum", "US$305,292,097,842", "https://mpng.subpng.com/20180802/jhk/kisspng-zcash-clip-art-cryptocurrency-portable-network-gra-ethereum-eth-cryptocurrency-png-clip-art-image-gal-5b638a7b055206.9657338615332501710218.jpg", "$583.28","+0.29%"));
//        coinsList.add(new Coins_model("Tether", "US$61,986,751,352", "https://www.cryptonewspoint.com/wp-content/uploads/2020/01/Tether-01.png", "$483.18","+2.21%"));
//        coinsList.add(new Coins_model("Binance Coin", "US$58,742,250,222", "https://icons.iconarchive.com/icons/cjdowner/cryptocurrency-flat/1024/Binance-Coin-BNB-icon.png", "$383.08","+1.00%"));
//        coinsList.add(new Coins_model("Cardano", "US$53,646,388,494", "https://www.iconpacks.net/icons/2/free-cardano-coin-icon-2216-thumb.png", "$283.00","+2.29%"));
//        coinsList.add(new Coins_model("Dogecoin", "$47,254,695,613", "https://image.pngaaa.com/186/326186-middle.png", "0 BTC","+1.50%"));
//        coinsList.add(new Coins_model("XRP", "US$43,765,009,771", "https://res.cloudinary.com/geopayme/image/upload/f_auto,q_auto/v1585828404/ripple-coin.png", "$183.10","+1.29%"));

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
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("status");
                                if (status.equals("success")) {
                                    JSONObject obj2 = (JSONObject)obj.get("data");
                                    JSONArray jArray = obj2.getJSONArray("coins");
                                    int length = jArray.length();
                                    if (String.valueOf(jArray.length()).equals("0")) {
                                        progressDialog.dismiss();
                                        displayMessage("Oops! no item were found");
                                    } else {
                                        for (int i = 0; i < length; i++) {
                                            JSONObject jObj = jArray.getJSONObject(i);
                                            String name = jObj.getString("name");
                                            String symbol = jObj.getString("symbol");
                                            String iconUrl    = jObj.getString("iconUrl");
                                            String marketCap    = jObj.getString("marketCap");
                                            String price    = jObj.getString("price");
                                            String rank    = jObj.getString("rank");
                                            String twentyhVolume    = jObj.getString("24hVolume");
                                            String btcPrice    = jObj.getString("btcPrice");

                                            coinsList.add(new Coins_model(name, price, iconUrl, btcPrice,"+1.29%"));

                                        }
                                        progressDialog.dismiss();
                                        coins_adapter.notifyDataSetChanged();
                                    }
                                } else {

                                    progressDialog.dismiss();
                                    displayMessage("Oops! no item were found");
                                }
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

                                        coinsList_top.add(new Top_coins_model(name,"Rank "+marketRank,iconUrl,btcPrice+" BTC",symbol));


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