const CART_KEY = "cart";

export class CartService {

    static getCart() {
        let cart = JSON.parse(localStorage.getItem(CART_KEY));
        return cart ? new Map(Object.entries(cart)) : new Map();
    }

    static setCart(cart) {
        localStorage.setItem(CART_KEY, JSON.stringify(Object.fromEntries(cart)));
    }

    static getGame(uuid) {
        let cart = CartService.getCart();
        return cart.get(uuid);
    }

    static addGame(game, quantity = 1) {
        let cart = this.getCart();
        let item = cart.has(game.uuid) ? cart.get(game.uuid) : {...game, count : 0};
        item.count += quantity;

        if(item.count < 0) CartService.removeGame(item);
        else {
            cart.set(item.uuid, item);
            CartService.setCart(cart);
        }
    }

    static removeGame(game) {
        let cart = CartService.getCart();
        cart.delete(game.uuid);
        CartService.setCart(cart);
    }

    static getFacture() {
        const cart = CartService.getCart();
        let prices = 0;
        let promoPrices = 0;

        cart.forEach(item => {
            const p = item.price * item.count;
            prices += p;
            if(item.promotion) {
               promoPrices += ((p * item.promotion.discountRate) / 100);
            }
        });

        return [prices.toFixed(2), promoPrices.toFixed(2), (prices - promoPrices).toFixed(2)];
    }

}