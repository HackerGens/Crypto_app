package crypto.currency.crypto_app.Utils;
public class URLHelper {
    public static final String base = "https://api.coinranking.com/v2";
    public static final String base2 = "https://api.coingecko.com/api/v3";

    public static final String coins = base+"/coins";
    public static final String coinstop_7 = base2+"/search/trending";
    public static final String graph_data = base2+"/coins/ethereum/market_chart?vs_currency=USD&days=10&interval=daily";
}