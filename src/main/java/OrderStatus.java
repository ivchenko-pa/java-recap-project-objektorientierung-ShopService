public enum OrderStatus {
    PROCESSING("Order placed"),
    IN_DELIVERY ("Order shipped"),
    COMPLETED ("Order delivered");

    private String description;

    private OrderStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
