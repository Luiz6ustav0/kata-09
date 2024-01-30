# kata-09

Solution written in Java for http://codekata.com/kata/kata09-back-to-the-checkout/

<h2>Project Structure</h2>

<h3>Overview</h3>

This project implements a pricing system for a checkout service. It consists of several components including pricing
strategies, services for calculating prices, and value objects representing checkout items and price results.

<h3>Project Components</h3>

<h4>Pricing Strategies</h4>

The BatchDiscountPricingStrategy and RegularPricingStrategy classes implement the IPricingStrategy interface, providing
different pricing mechanisms based on item quantities. With the interface, it would also be possible to implement new
pricing strategies that calculate price based on different rules, and further, if necessary, it would also be possible
to use some sort of composite pattern to encapsulate the pricing strategies for an item and build even more complex
pricing strategies.

Pros:

    Strategy pattern allows for easy extension with new pricing strategies.
    Clear separation of concerns, each strategy handles pricing logic independently.

Cons:

    Limited flexibility in composing complex pricing rules.

<h4>Pricing Strategy Service</h4>

The PricingStrategyService class manages a map of item names to pricing strategies and provides a method to calculate
prices for checkout items. The price for each item is being set in the constructor, but this could easily be exchanged
for a repository or some other more reliable form of keeping track of the price strategies for each item.

Pros:

    Encapsulates pricing logic and promotes separation of concerns.
    Flexible architecture allows for easy addition of new pricing strategies.

Cons:

    Tight coupling between the service and pricing strategy implementations.
    Lack of error handling for invalid checkout items or pricing strategies.

<h4>Checkout Service</h4>

The CheckoutService class orchestrates the checkout process, handling the scanning of items and calculating the total
price.

Pros:

    Encapsulates checkout logic, promoting separation of concerns.
    Keeps track of total price and discount efficiently, allowing the potential user to see the total price and total discount at all times

Cons:

    Direct dependency on PricingStrategyService, potentially limiting reusability.
    Mutability of totalPrice, totalDiscount, and items fields could lead to concurrency issues in a multi-threaded environment.

<h3>Conclusion</h3>

Time for conclusion: ~4 hours

The project's structure follows object-oriented principles and promotes modularization and reusability. However, there
are areas for improvement such as error handling, concurrency management, and reducing tight coupling between
components.