package entity;

public class Purchase {

    private int id;
    private String createAt;
    private int quantity;
    private int idClient;
    private int idProduct;
    private Client client;
    private Product product;

    public Purchase() {
    }

    public Purchase(int id, String createAt, int quantity, int idClient, int idProduct) {
        this.id = id;
        this.createAt = createAt;
        this.quantity = quantity;
        this.idClient = idClient;
        this.idProduct = idProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("Purchase").append("\n Id: ").append(id)
                .append("\n Create at: ").append(createAt)
                .append("\n Quantity: ").append(quantity)
                .append("\n Client id: ").append(idClient);
        if (client != null) {
            message.append(client);
        }
        message.append("\n Product id: ").append(idProduct)
                .append("\n");
        if (product != null) {
            message.append("\n Product: ").append(product.getName())
                    .append("\n Price: ").append(product.getPrice())
                    .append("\n IVA: ").append("19%")
                    .append("\n Total: ").append(product.getPrice()*1.19);
        }
        message.append("\n");
        return message.toString();
    }
}
