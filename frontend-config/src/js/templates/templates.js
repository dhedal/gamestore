
import {AuthenticationService} from "../services/authentication-service.js";

/**
 *
 */
export class GameTemplate {
    template;
    constructor(templateId = "game-template") {
        this.template = document.getElementById(templateId);

    }

    clone(game) {
        if(game == null) return null;
        const clone = this.template.content.cloneNode(true);

        if(!AuthenticationService.isConnected() || game.stock <= 0) {
            clone.querySelector(".cartBtn").classList.add("visually-hidden");
        }
        else {
            clone.querySelector(".cartBtn").classList.remove("visually-hidden");
        }

        clone.querySelector(".card-bg").src = game.gameInfo.coverUrl;
        clone.querySelector(".card-title").textContent = game.gameInfo.title;
        clone.querySelector(".price").textContent = game.price + "€";

        const gameContainer = clone.querySelector(".card");
        gameContainer.addEventListener("click", event => {
            event.preventDefault();
            const nodeName = event.originalTarget.nodeName;
            if(nodeName === "BUTTON" || nodeName === "I") {
                const customEvent = new CustomEvent("game-click", {
                    detail: {item: game},
                    bubbles: true,
                    composed: true
                });
                gameContainer.dispatchEvent(customEvent);
            }
            else {
                window.location.href = `/game-detail?game=${game.uuid}&info=${game.gameInfo.uuid}`;
            }
        });

        if(game.promotion != null) this.clonePromotion(clone, game.promotion);

        return clone;
    }

    clonePromotion(clone, promotion) {
        if(clone == null || promotion == null);

        clone.querySelector(".price").classList.add("text-decoration-line-through");

        let promoElt = document.createElement("span");
        promoElt.classList.add("position-absolute", "bottom-0", "end-0", "badge", "text-bg-warning", "fs-6", "fw-bolder", "me-3", "mb-1", "text-white");
        promoElt.textContent = promotion.discountRate + "%";
        clone.querySelector(".picture").appendChild(promoElt);
    }
}

/**
 *
 */
export class GenreTemplate {
    template;
    constructor(templateId = "genre-template") {
        this.template = document.getElementById(templateId);
    }

    clone(genre) {
        const clone = this.template.content.cloneNode(true);

        const button = clone.querySelector(".btn");
        button.setAttribute("data-genre", genre.key);
        button.textContent = genre.label;
        return clone;
    }
}


/**
 *
 */
export class CartGameTemplate {
    template;

    constructor(templateId = "cart-game-template"){
        this.template = document.getElementById(templateId);

    }

    clone(game) {
        if(game == null) return;
        const gameInfo = game.gameInfo;
        if(gameInfo == null) return;

        const clone = this.template.content.cloneNode(true);
        const cartGame = clone.querySelector(".cart-game");

        clone.querySelector(".cart-game-image").href = "/game-detail?game=" + game.uuid + "&info="+ gameInfo.uuid;
        if(gameInfo.coverUrl){
            clone.querySelector(".img-thumbnail").src = gameInfo.coverUrl;
        }

        cartGame.querySelector(".cart-game-title").textContent = gameInfo.title;

        cartGame.querySelector(".cart-game-platform").textContent = game.platform.label;

        const currencyTag = cartGame.querySelector(".cart-game-price i");
        currencyTag.parentNode.insertBefore(document.createTextNode(game.price), currencyTag);

        if(game.promotion) {
            cartGame.querySelector(".cart-game-promo-purcent").textContent = "-"+game.promotion.discountRate + "%";
        }

        cartGame.querySelector(".cart-game-quantity").textContent = game.count;


        cartGame.querySelector(".cart-game-remove-btn").addEventListener("click", event => {
            event.preventDefault();
            const customEvent = new CustomEvent("cart-game-remove-event", {
                detail : {item: game},
                bubbles: true,
                composed: true
            });

            cartGame.dispatchEvent(customEvent);
        });


        cartGame.querySelector(".cart-game-add-btn").classList.remove("visually-hidden");
        cartGame.querySelector(".cart-game-add-btn").addEventListener("click", event => {
            event.preventDefault();
            const customEvent = new CustomEvent("cart-game-add-event", {
                detail : {item: game},
                bubbles: true,
                composed: true
            });
            cartGame.dispatchEvent(customEvent);
        });




        cartGame.querySelector(".cart-game-sub-btn").addEventListener("click", event => {
            event.preventDefault();
            const customEvent = new CustomEvent("cart-game-sub-event", {
                detail : {item: game},
                bubbles: true,
                composed: true
            });
            cartGame.dispatchEvent(customEvent);
        });

        return clone;
    }

    /**
     *
     * @param {*} game
     * @returns
     */
    forModal(game) {
        return this.clone(game);
    }

    /**
     *
     * @param {*} game
     */
    forCartPage(game) {
        const clone = this.clone(game);
        const cartGame = clone.querySelector(".cart-game");
        cartGame.classList.add("bg-light", "text-secondary", "p-3", "rounded-3", "w-75");

        return clone;
    }
}


export class OrderTemplate {
    template;
    isTeam;
    constructor(templateId = "order-template", isTeam = false) {
        this.template = document.getElementById(templateId);
        this.isEmployee = AuthenticationService.isEmployee();
        this.isTeam = isTeam;
    }

    clone(order) {
        if(order == null) return null;
        const clone = this.template.content.cloneNode(true);
        let total = 0;
        const card = clone.querySelector(".card");
        card.querySelector(".order-date-created").textContent = order.createdAt.split("T")[0];
        card.querySelector(".order-status").textContent = order.status.label;
        card.querySelector(".order-num").textContent = order.uuid;

        const orderLinesContainer = card.querySelector(".order-lines-container");
        order.orderLines.forEach(orderLine => {
            const result = this.orderLineTemplate(orderLine);
            total += result.total;
            orderLinesContainer.appendChild(result.div);
        });

        card.querySelector(".order-price").textContent = total + " €" ;

        if(!this.isTeam) {
            card.querySelector(".card-footer").classList.add("visually-hidden");
        }
        else {
            const checkbox = card.querySelector(".checkbox-validate");
            const validateButton = card.querySelector(".validate-button");

            checkbox.addEventListener("change", event => {
                if (checkbox.checked) {
                    validateButton.disabled = true;
                } else {
                    validateButton.disabled = false;
                }
            });

            validateButton.addEventListener("click", event => {
                const customEvent = new CustomEvent("order-delivered", {
                    bubbles: true,
                    detail: {order: order.uuid}
                });
                validateButton.dispatchEvent(customEvent);
            });

        }

        return clone;
    }

    orderLineTemplate(orderLine) {

        let reduction = 0;
        const quantity = orderLine.quantity;
        const price = orderLine.gameArticle.price;
        if(orderLine.promotion) {
            reduction = (price * orderLine.promotion.discountRate) / 100;
        }
        const total = (price - reduction) * quantity;
        const div = document.createElement("div");
        div.classList.add("col-12", "border", "border-primary-subtle", "mb-3");
        div.innerHTML =  `
        ${this.keyAndValueTemplate("Titre:", orderLine.gameArticle.gameInfo.title)}
        ${this.keyAndValueTemplate("Platforme:", orderLine.gameArticle.platform.label)}
        ${this.keyAndValueTemplate("Prix unitaire:", price.toFixed(2) + " €")}
        ${this.keyAndValueTemplate("Réduction:", "-" + reduction.toFixed(2) + " €")}
        ${this.keyAndValueTemplate("Quantité:", quantity)}
        ${this.keyAndValueTemplate("Total:", total.toFixed(2) + " €")}
        `;

        return {total: total, div: div};
    }

    keyAndValueTemplate(key, value) {
        return `
        <div class="row">
            <div class="col-lg-2 col-md-4 col-sm-12 fw-bold">${key}</div>
            <div class="col-lg-10 col-md-8 col-sm-12">${value}</div>
        </div>`;
    }
}