class ExtendCheckoutStrategy implements CheckoutStrategy {
    @Override
    public void checkoutResource(Book book, User user, int daysToReturn) {
        System.out.println(user.getUsername() + " checked out " + book.getTitle() +
                " using Extended Checkout for " + daysToReturn + " days.");
    }
}