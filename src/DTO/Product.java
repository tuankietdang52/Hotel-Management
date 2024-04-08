package DTO;

public class Product {
    private String productCode;
    private String productName;
    private String typeCode;
    private int quantity;
    private String description;
    private String image;
    private double price;

    public Product(){

    }

    /**
     *  Pass path to image variable
     */
    public Product(String productCode,
                   String productName,
                   String typeCode,
                   int quantity,
                   String description,
                   String image,
                   double price){

        this.productCode = productCode;
        this.productName = productName;
        this.typeCode = typeCode;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     *
     * @param image: Path of an image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return Path of an image
     */
    public String getImage() {
        return image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
