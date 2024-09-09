import {PageComponent} from "../components/page-component.js";
import {GameService} from "../services/game-service.js";

export class GameDetailPage extends PageComponent{
    gameMap;
    platformTagMap;
    game;

    constructor() {
        super("/game-detail", "Game detail", "/html/game-detail.html", []);
    }

    async before() {
        console.log("##GameDetailPage #before");
        this.gameMap = new Map();
        this.platformTagMap = new Map();

        const gameInfoUuid = this.param.get("info");

        await GameService.fetchGameByInfo(gameInfoUuid).then(response => {
            Object.entries(response).forEach(([uuid, game]) => {
                this.gameMap.set(uuid, game);
            });
            console.log(this.gameMap);
            this.game = null;
        });



    }

    ready() {
        console.log("##GameDetailPage #ready");
        this.fill(this.gameMap.get(this.param.get("game")));
    }

    fill(game) {
        console.log(game);
        if(game == null || game.gameInfo == null) return;

        this.fillGameInfo(game.gameInfo);
        this.fillPlatformGameVersion(game);

        document.getElementById("addCartBtn").addEventListener("click", event => {
            console.log(game);
        });
    }

    fillGameInfo(gameInfo) {
        if(gameInfo == null) return;
        const gameCover = document.getElementById("game-cover");
        gameCover.src= gameInfo.coverUrl ? gameInfo.coverUrl : "/assets/images/empty.bmp";


        const title = document.getElementById("game-title");
        title.textContent = gameInfo.title;

        this.createPlatformsTag(gameInfo);

        document.getElementById("game-description").textContent = gameInfo.summary;
    }



    createPlatformsTag(gameInfo) {
        if(gameInfo == null || gameInfo.platforms == null) return;
        const platforms = document.getElementById("game-platforms");
        platforms.innerHTML = "";
        gameInfo.platforms.forEach(platform => {
            const tag = this.platformTag(platform);
            if(tag) {
                platforms.appendChild(tag);
                this.platformTagMap.set(platform.key, tag);

                tag.addEventListener("click", event => {
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
        gamePrice.textContent = game.price + "€";

        const gamePromoPurcent =  document.getElementById("game-promo-purcent");
        gamePromoPurcent.innerHTML = "";

        const gamePromoPrice =  document.getElementById("game-promo-price");
        gamePromoPrice.innerHTML = "";



        if(game.promotion) {
            gamePrice.classList.add("text-decoration-line-through", "text-secondary");

            const gamePromoPurcent =  document.getElementById("game-promo-purcent");
            gamePromoPurcent.textContent = "-" + game.promotion.discountRate + "%";

            const gamePromoPrice =  document.getElementById("game-promo-price");
            gamePromoPrice.textContent = this.calculatePromotion(game) + "€";
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
}