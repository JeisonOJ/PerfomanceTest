package entity;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int idShop;
    private Shop shop;

    public Product(){}

    public Product(int id, String name, double price, int stock, int idShop) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.idShop = idShop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("Product").append("\n Id: ").append(id)
                .append("\n Name: ").append(name)
                .append("\n Price: ").append(price)
                .append("\n Stock: ").append(stock)
                .append("\n Shop id: ").append(idShop)
                .append("\n");
        if(shop != null){
            message.append("\n").append(shop);
        }
        message.append("\n");
        return message.toString();
    }
}
