package crypto.currency.crypto_app.Home_Frag;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.List;

import crypto.currency.crypto_app.R;

public class TopCoinsAdapter extends RecyclerView.Adapter<TopCoinsAdapter.MyViewHolder> {

    @NonNull
    private android.content.Context mContext;
    private Context Context;
    private List<Top_coins_model> coinsList;

    public TopCoinsAdapter(Context mContext, List<Top_coins_model> coinsList) {
        this.mContext = mContext;
        this.coinsList = coinsList;
    }

    @NonNull
    @Override
    public TopCoinsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_coins_item, parent, false);

        return new TopCoinsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TopCoinsAdapter.MyViewHolder holder, int position) {
        Top_coins_model album = coinsList.get(position);
        holder.coins_name.setText(album.getCoins_title());
        holder.coins_price.setText(album.getCoins_Totalprice());
        //+" BTC"
        Double d_btc = Double.valueOf(album.getCoins_price_btc());
        holder.coins_Btcprice.setText(String.format("%.8f", d_btc)+" BTC");
        holder.coinsSymbol.setText(album.getCoins_symbol());

        Picasso.Builder builder = new Picasso.Builder(mContext);
        Picasso picasso = builder.build();
        picasso.load(album.getCoins_image()).placeholder(R.drawable.loading).fit().centerInside().into(holder.coins_pic);

    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView coins_name,coins_price,coins_Btcprice,coinsSymbol;
        public ImageView coins_pic;

        public MyViewHolder(@NonNull View view) {
            super(view);
            coins_name = view.findViewById(R.id.top_title_text);
            coins_price = view.findViewById(R.id.top_pricetext);
            coins_Btcprice = view.findViewById(R.id.top_price_btc);
            coinsSymbol = view.findViewById(R.id.top_coinsSymbol);
            coins_pic = view.findViewById(R.id.top_coinsimage);
            view.setOnClickListener(this);
            Context = view.getContext();
        }

        @Override
        public void onClick(View view) {
        }
    }
}