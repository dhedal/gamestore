import {cache} from "../config/cache.js";

export class GameTemplate {
    template;
    constructor(templateId = "game-template") {
        this.template = document.getElementById(templateId);

    }

    clone(game) {
        if(game == null) return null;
        const clone = this.template.content.cloneNode(true);

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