import {CartGameTemplate} from "../templates/templates.js";
import {CartService} from "../services/cart-service.js";
import {SigninForm, SignupForm} from "../forms/forms.js";
import {Modal} from "bootstrap";


export class CartModal {
    container;
    cartGameTemplate;

    constructor(containerId = "cartModal") {
        this.container = document.getElementById(containerId);
        this.cartGameTemplate = new CartGameTemplate();
        this.init();
    }

    init() {
        this.container.addEventListener("show.bs.modal", event => {});

        this.container.addEventListener("cart-game-remove-event", event => {
            event.preventDefault();
            CartService.removeGame(event.detail.item);
            this.render();
        });


        this.container.addEventListener("cart-game-sub-event", event => {
            event.preventDefault();
            CartService.addGame(event.detail.item, -1);
            this.render();
        });

        this.container.addEventListener("cart-game-add-event", event => {
            event.preventDefault();
            CartService.addGame(event.detail.item);
            this.render();
        });
    }

    addGame(game) {
        CartService.addGame(game);
        this.render();

    }

    render() {
        const cart = CartService.getCart();
        const modalBody = this.container.querySelector(".modal-body");
        modalBody.innerHTML = "";

        if(cart == null || cart.size <= 0) {
            modalBody.innerHTML = `
            <div class="row text-center fw-bold">
                <div class="col-12">
                    <span class="fs-4 text-warning"><i class="bi bi-cart4"></i></span>
                </div>
                <p class="col-12">
                    Votre panier est vide.
                </p>
            </div>
            `;
        }
        else {
            cart.forEach(item => {
                const clone = this.cartGameTemplate.forModal(item);
                modalBody.appendChild(clone);
            });
        }



        const totalTag = document.getElementById("modal-cart-facture");
        let prices = 0, promoPrices = 0, total = 0;
        [prices, promoPrices, total] = CartService.getFacture();
        totalTag.textContent = total;
    }
}

export class AuthenticationModal {
    modalElement;

    constructor(elementId = "modal-authentication") {
        this.modalElement = document.getElementById(elementId);

    }


    // close() {
    //     Modal.getInstance(this.modalElement).hide();
    // }
}