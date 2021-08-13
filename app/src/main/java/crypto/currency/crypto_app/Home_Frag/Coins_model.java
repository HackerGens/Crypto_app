package crypto.currency.crypto_app.Home_Frag;

public class Coins_model {

    public String getCoins_title() {
        return coins_title;
    }

    public String getCoins_Totalprice() {
        return coins_Totalprice;
    }

    public String getCoins_image() {
        return coins_image;
    }

    public String getCoins_price() {
        return coins_price;
    }

    public String getCoins_percentage() {
        return coins_percentage;
    }

    public String getCoins_id() {
        return coins_id;
    }

    private String coins_id;
    private String coins_title;
    private String coins_Totalprice;
    private String coins_image;
    private String coins_price;
    private String coins_percentage;
    private String market_cap_usd;

    public String getMarket_cap_usd() {
        return market_cap_usd;
    }

    public String getMarket_cap_btc() {
        return market_cap_btc;
    }

    private String market_cap_btc;

    public Coins_model(String coins_id,String coins_title, String coins_Totalprice, String coins_image, String coins_price, String coins_percentage,String market_cap_usd,String market_cap_btc) {
        this.coins_id= coins_id;
        this.coins_title = coins_title;
        this.coins_Totalprice = coins_Totalprice;
        this.coins_image = coins_image;
        this.coins_price = coins_price;
        this.coins_percentage = coins_percentage;
        this.market_cap_usd = market_cap_usd;
        this.market_cap_btc = market_cap_btc;
    }


}
