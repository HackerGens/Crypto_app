package crypto.currency.crypto_app.Home_Frag;

public class Top_coins_model {

    public String getCoins_title() {
        return coins_title;
    }

    public String getCoins_Totalprice() {
        return coins_Totalprice;
    }

    public String getCoins_image() {
        return coins_image;
    }



    private String coins_title;
    private String coins_Totalprice;
    private String coins_image;

    public String getCoins_price_btc() {
        return coins_price_btc;
    }

    private String coins_price_btc;

    public String getCoins_symbol() {
        return coins_symbol;
    }

    private String coins_symbol;
    public Top_coins_model(String coins_title, String coins_Totalprice, String coins_image, String coins_price_btc, String coins_symbol) {
        this.coins_title = coins_title;
        this.coins_Totalprice = coins_Totalprice;
        this.coins_image = coins_image;
        this.coins_price_btc = coins_price_btc;
        this.coins_symbol = coins_symbol;
    }


}
