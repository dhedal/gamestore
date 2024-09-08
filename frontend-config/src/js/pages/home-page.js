import { PageComponent } from "components/page-component.js";
import { GameService} from "../services/game-service.js";
import { PageDataService } from "../services/page-data-service.js"
import {GameTemplate} from "../templates/templates.js";

export class HomePage extends PageComponent {
    dataLastGameMap;
    dataCurrentPromotionMap;
    lastGamesContainer;
    currentPromotionsContainer;
    gameTemplate;

    constructor() {
        super("/home", "Accueil", "/html/home.html", [""]);
    }

    async before() {
        console.log("## homePage # before");
        this.dataLastGameMap = new Map();
        this.dataCurrentPromotionMap = new Map();

        await Promise.all([
            PageDataService.fetchHomePageData().then(response => {
                response.gameArticles.forEach(game => {
                    this.dataLastGameMap.set(game.uuid, game);
                });
                response.promotions.forEach(promotion => {
                    this.dataCurrentPromotionMap.set(promotion.gameArticle.uuid, promotion);
                });
            })
        ]);

        this.lastGamesContainer = document.getElementById("lastGamesContainer");
        this.currentPromotionsContainer = document.getElementById("currentPromotionsContainer");
        this.gameTemplate = new GameTemplate();
    }

    ready() {
        console.log("## HomePage # ready");
        this.dataLastGameMap.forEach(game => {
            const clone = this.gameTemplate.clone(game);
            this.lastGamesContainer.appendChild(clone);
        });

        this.dataCurrentPromotionMap.forEach(promotion => {
            const clone = this.gameTemplate.cloneForPromotion(promotion);
            this.currentPromotionsContainer.appendChild(clone);
        });
    }

}