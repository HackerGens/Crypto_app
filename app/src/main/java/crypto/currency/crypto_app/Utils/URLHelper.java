package crypto.currency.crypto_app.Utils;
public class URLHelper {
    public static final String base = "https://api.coingecko.com/api/v3";
    public static final String coins = base+"/coins";
    public static final String exchanges = base+"/exchanges?per_page=250&page=1";
    public static final String coinstop_7 = base+"/search/trending";
    public static final String graph_data = base+"/coins";
    public static final String single_coin_data = base+"/bitcoin?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=true";


}