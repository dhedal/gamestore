import {PageComponent} from "../components/page-component.js";
import {GameService} from "../services/game-service.js";
import {CartModal} from "../modals/modal.js";
import {AuthenticationService} from "../services/authentication-service.js";

export class GameDetailPage extends PageComponent{
    gameMap;
    platformTagMap;
    game;
    addCartBtn;
    cartModal;

    constructor() {
        super("/game-detail", "Game detail", "/html/game-detail.html", []);
    }

    async before() {
        this.gameMap = new Map();
        this.platformTagMap = new Map();

        const gameInfoUuid = this.param.get("info");

        await GameService.fetchGameByInfo(gameInfoUuid).then(response => {
            Object.entries(response).forEach(([uuid, game]) => {
                this.gameMap.set(uuid, game);
            });
            this.game = null;
        });


        this.cartModal = new CartModal();

        this.addCartBtn = document.getElementById("addCartBtn");

    }

    ready() {
        this.fill(this.gameMap.get(this.param.get("game")));
    }

    fill(game) {
        if(game == null || game.gameInfo == null) return;

        this.fillGameInfo(game.gameInfo);
        this.fillPlatformGameVersion(game);

        document.getElementById("addCartBtn").addEventListener("click", event => {
            this.cartModal.addGame(this.game);
        });
    }

    fillGameInfo(gameInfo) {
        if (gameInfo == null) return;
        const gameCover = document.getElementById("game-cover");
        gameCover.src = gameInfo.coverUrl ? gameInfo.coverUrl : "/assets/images/empty.bmp";


        const title = document.getElementById("game-title");
        title.textContent = gameInfo.title;

        const pegi = document.getElementById("game-pegi");
        pegi.textContent = gameInfo.pegi.label;

        this.createPlatformsTag(gameInfo);

        document.getElementById("game-description").textContent = gameInfo.summary;

        if (gameInfo.genres) {
            const gameGenres = document.getElementById("game-genres");
            gameGenres.innerHTML = "";
            gameInfo.genres.forEach((genre) => {
                const span = document.createElement("span");
                span.classList.add("badge", "bg-primary", "me-2", "mb-2");
                span.textContent = genre.label;
                gameGenres.appendChild(span);
            });
        }
    }



    createPlatformsTag(gameInfo) {
        if(gameInfo == null || gameInfo.platforms == null) return;
        const platforms = document.getElementById("game-platforms");
        platforms.innerHTML = "";
        gameInfo.platforms.forEach(platform => {
            const span = this.platformTag(platform);
            if(span) {
                const div = document.createElement("div");
                div.classList.add("col-auto", "mb-2");
                div.appendChild(span);
                platforms.appendChild(div);
                this.platformTagMap.set(platform.key, span);

                span.addEventListener("click", event => {
                    const game = Array.from(this.gameMap)
                        .find(([uuid, game]) => game.platform.key === platform.key)?.[1];
                    if(game) this.fillPlatformGameVersion(game);
                });
            }

        });

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

    fillPlatformGameVersion(game) {
        if(game == null) return;

        const gamePrice = document.getElementById("game-price");
        gamePrice.innerHTML = "";
        gamePrice.classList.remove("text-decoration-line-through");


        const gamePromoPurcent =  document.getElementById("game-promo-purcent");
        gamePromoPurcent.innerHTML = "";

        const gamePromoPrice =  document.getElementById("game-promo-price");
        gamePromoPrice.innerHTML = "";

        const gameStock = document.getElementById("game-stock");
        gameStock.innerHTML = "";

        if(AuthenticationService.isConnected() && game.stock > 0) {
            gamePrice.textContent = game.price + "€";

            gameStock.appendChild(this.spanText("En stock", ["text-success"]));
            this.addCartBtn.classList.remove("visually-hidden");

            if(game.promotion) {
                gamePrice.classList.add("text-decoration-line-through", "text-secondary");

                const gamePromoPurcent =  document.getElementById("game-promo-purcent");
                gamePromoPurcent.textContent = "-" + game.promotion.discountRate + "%";

                const gamePromoPrice =  document.getElementById("game-promo-price");
                gamePromoPrice.textContent = this.calculatePromotion(game) + "€";
            }

        }
        else if(!AuthenticationService.isConnected()){
            this.addCartBtn.classList.add("visually-hidden");
        }
        else {
            gameStock.appendChild(this.spanText("Rupture de stock", ["text-danger"]));
            this.addCartBtn.classList.add("visually-hidden");
        }
        

        this.activeGame(game);

    }

    activeGame(game) {
        if(game == null) return;

        if(this.game) {
            const tag = this.platformTagMap.get(this.game.platform.key);
            tag.classList.remove("btn-primary");
            tag.classList.add("btn-outline-primary");
        }

        const tag = this.platformTagMap.get(game.platform.key);
        if(tag) {
            tag.classList.remove("btn-outline-primary");
            tag.classList.add("btn-primary");
            this.game = game;
        }

    }

    calculatePromotion(game) {
        if(game == null || game.promotion == null) return -0.00;
        const promo = (game.price * game.promotion.discountRate) / 100;
        return (game.price - promo).toFixed(2);
    }

    spanText(text, classArray = []){
        const span = document.createElement("span");
        span.classList.add(...classArray);
        span.textContent = text;
        return span;
    }
}