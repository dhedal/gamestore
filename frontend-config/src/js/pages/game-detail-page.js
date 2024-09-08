import {PageComponent} from "../components/page-component.js";
import {GameService} from "../services/game-service.js";

export class GameDetailPage extends PageComponent{
    gameVersionMap;

    constructor() {
        super("/game-detail", "Game detail", "/html/game-detail.html", []);
    }

    async before() {
        console.log("##GameDetailPage #before");
        this.gameVersionMap = new Map();

        const gameInfoUuid = this.param.get("info");

        await GameService.fetchGameByInfo(gameInfoUuid).then(response => {
            Object.entries(response).forEach(([uuid, game]) => {
                this.gameVersionMap.set(uuid, game);
            });
        });

    }

    ready() {
        console.log("##GameDetailPage #ready");
        this.fill(this.gameVersionMap.get(this.param.get("game")));
    }

    fill(game) {
        if(game == null) return;
        const platforms = document.getElementById("game-platforms");
        game.gameInfo.platforms.forEach(platform => {
            platforms.appendChild(this.platformTag(platform));
        });

        const gamePrice = document.getElementById("game-price");
        gamePrice.textContent = game.price + "€";

        if(game.promotion) {
            gamePrice.classList.add("text-decoration-line-through", "text-secondary");

            const gamePromoPurcent =  document.getElementById("game-promo-purcent");
            gamePromoPurcent.textContent = "-" + game.promotion.discountRate + "%";

            const gamePromoPrice =  document.getElementById("game-promo-price");
            gamePromoPrice.textContent = this.calculatePromotion(game) + "€";
        }

        document.getElementById("game-description").textContent = game.summary;

        document.getElementById("addCartBtn").addEventListener("click", event => {
            console.log(game);
        });
    }

    calculatePromotion(game) {
        if(game == null || game.promotion == null) return -0.0;
        const promo = (game.price * game.promotion.discountRate) / 100;
        return game.price - promo;
    }

    platformTag(platform) {
        const span = document.createElement("span");
        span.classList.add("btn", "btn-outline-primary");
        span.innerHTML = `
            <img class="me-1" width="15" height="15" srcset="${platform.icon}" />
            ${platform.label}
        `;
        return span;
    }
}