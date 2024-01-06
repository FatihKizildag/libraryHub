class ReturnCheckoutStrategy implements CheckoutStrategy {
    @Override
    public void checkoutResource(Book book, User user, int daysToReturn) {
        // Check if the user delivered the book on time
        if (book.getDueDays() >= daysToReturn) {
            System.out.println(user.getUsername() + " returned the book on time.");
        } else {
            System.out.println(user.getUsername() + ", the book was not returned on time. Do not allow to check out a new book today.");
        }
    }
}
