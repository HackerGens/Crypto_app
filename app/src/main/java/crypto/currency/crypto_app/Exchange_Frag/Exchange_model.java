package crypto.currency.crypto_app.Exchange_Frag;

public class Exchange_model {
    public String getExchange_id() {
        return exchange_id;
    }

    public String getExchange_title() {
        return exchange_title;
    }

    public String getExchange_url() {
        return exchange_url;
    }

    public String getExchange_image() {
        return exchange_image;
    }

    public String getExchange_btcprice() {
        return exchange_btcprice;
    }

    private String exchange_id;
    private String exchange_title;
    private String exchange_url;
    private String exchange_image;
    private String exchange_btcprice;

    public String getExchange_year_estb() {
        return exchange_year_estb;
    }

    private String exchange_year_estb;

    public String getExchange_tsr() {
        return exchange_tsr;
    }

    private String exchange_tsr;

    public String getExchange_country() {
        return exchange_country;
    }

    private String exchange_country;
    public Exchange_model(String exchange_id, String exchange_title, String exchange_url, String exchange_image, String exchange_btcprice,String exchange_country,String exchange_tsr,String exchange_year_estb) {
        this.exchange_id = exchange_id;
        this.exchange_title = exchange_title;
        this.exchange_url = exchange_url;
        this.exchange_image = exchange_image;
        this.exchange_btcprice = exchange_btcprice;
        this.exchange_country = exchange_country;
        this.exchange_tsr = exchange_tsr;
        this.exchange_year_estb = exchange_year_estb;
    }


}
