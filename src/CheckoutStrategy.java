interface CheckoutStrategy {
    void checkoutResource(Book book, User user, int daysToReturn);
}