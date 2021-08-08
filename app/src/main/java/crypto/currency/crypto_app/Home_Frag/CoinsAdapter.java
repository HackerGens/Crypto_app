package crypto.currency.crypto_app.Home_Frag;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import crypto.currency.crypto_app.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.MyViewHolder> {

    @NonNull
    private android.content.Context mContext;
    private Context Context;
    private List<Coins_model> coinsList;

    public CoinsAdapter(Context mContext, List<Coins_model> coinsList) {
        this.mContext = mContext;
        this.coinsList = coinsList;
    }

    @NonNull
    @Override
    public CoinsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coins_item, parent, false);

        return new CoinsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoinsAdapter.MyViewHolder holder, int position) {
        Coins_model album = coinsList.get(position);
        holder.coins_name.setText(album.getCoins_title());

        Double d = Double.valueOf(album.getCoins_Totalprice());
        Double d_btc = Double.valueOf(album.getCoins_price());
        holder.coins_price.setText(String.format("%.2f", d_btc)+" BTC");
        holder.coins_Totalprice.setText(String.format("%.2f", d)+" $");
        holder.coinsPercentage.setText(album.getCoins_percentage());

        GlideToVectorYou
                .init()
                .with(mContext)
                .setPlaceHolder(R.drawable.loading, R.drawable.loading)
                .load(Uri.parse(album.getCoins_image()), holder.coins_pic);

    }

//    public static String getRoughNumber(long value) {
//        if (value <= 999) {
//            return String.valueOf(value);
//        }
//
//        final String[] units = new String[]{"", "K", "M", "B", "P"};
//        int digitGroups = (int) (Math.log10(value) / Math.log10(1000));
//        return new DecimalFormat("#,##0.#").format(value / Math.pow(1000, digitGroups)) + "" + units[digitGroups];
//
//    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView coins_name,coins_price,coins_Totalprice,coinsPercentage;
        public ImageView coins_pic;

        public MyViewHolder(@NonNull View view) {
            super(view);
            coins_name = view.findViewById(R.id.title_text);
            coins_price = view.findViewById(R.id.pricetext);
            coins_Totalprice = view.findViewById(R.id.coinstprice);
            coinsPercentage = view.findViewById(R.id.percentagetext);
            coins_pic = view.findViewById(R.id.coinsimage);
            view.setOnClickListener(this);
            Context = view.getContext();
        }

        @Override
        public void onClick(View view) {
        }
    }



}