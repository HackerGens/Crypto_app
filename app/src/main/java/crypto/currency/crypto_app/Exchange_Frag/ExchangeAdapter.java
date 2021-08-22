package crypto.currency.crypto_app.Exchange_Frag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import crypto.currency.crypto_app.Home_Frag.CoinsDetails_bootomsheet;
import crypto.currency.crypto_app.Home_Frag.Coins_model;
import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.SharedHelper;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.MyViewHolder> {

    @NonNull
    private android.content.Context mContext;
    private Context Context;
    private List<Exchange_model> exchangeList;

    public ExchangeAdapter(Context mContext, List<Exchange_model> exchangeList) {
        this.mContext = mContext;
        this.exchangeList = exchangeList;
    }

    @NonNull
    @Override
    public ExchangeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exchange_item, parent, false);

        return new ExchangeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExchangeAdapter.MyViewHolder holder, int position) {
        Exchange_model album = exchangeList.get(position);
        holder.ex_name.setText(album.getExchange_title());


        holder.ex_country.setText(album.getExchange_country());
        holder.ex_tsr.setText(album.getExchange_tsr());
        holder.ex_year_established.setText(album.getExchange_year_estb());
        Double d_btc = Double.valueOf(album.getExchange_btcprice());
        holder.ex_btc.setText(String.format("%.2f", d_btc)+" BTC 24h");

        Glide.with(mContext)
                .load(album.getExchange_image())
                .centerCrop()
                .into(holder.ex_pic);
    }

    @Override
    public int getItemCount() {
        return exchangeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ex_name,ex_country,ex_btc,ex_tsr,ex_year_established;
        public ImageView ex_pic;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ex_name = view.findViewById(R.id.ex_title);
            ex_tsr = view.findViewById(R.id.ex_tsr);
            ex_year_established = view.findViewById(R.id.ex_year_established);
            ex_country = view.findViewById(R.id.ex_country);
            ex_btc = view.findViewById(R.id.ex_btc);
            ex_pic = view.findViewById(R.id.ex_pic);
            view.setOnClickListener(this);
            Context = view.getContext();
        }

        @Override
        public void onClick(View view) {

        }
    }



}