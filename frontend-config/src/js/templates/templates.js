import {cache} from "../config/cache.js";
import {AuthenticationService} from "../services/authentication-service.js";
import {appContext} from "../config/app-context";

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