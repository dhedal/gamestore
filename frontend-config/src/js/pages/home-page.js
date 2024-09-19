import { PageComponent } from "components/page-component.js";
import { PageDataService } from "../services/page-data-service.js"
import {GameTemplate} from "../templates/templates.js";
import {CartModal} from "../modals/modal.js";

export class HomePage extends PageComponent {
    dataLastGameMap;
    dataCurrentPromotionMap;
    lastGamesContainer;
    currentPromotionsContainer;
    gameTemplate;
    cartModal;

    constructor() {
        super("/home", "Accueil", "/html/home.html", [""]);
    }

    async before() {
        this.dataLastGameMap = new Map();
        this.dataCurrentPromotionMap = new Map();

        await Promise.all([
            PageDataService.fetchHomePageData().then(response => {
                response.gameArticles.forEach(game => {
                    this.dataLastGameMap.set(game.uuid, game);
                });
                response.promotions.forEach(game => {
                    this.dataCurrentPromotionMap.set(game.uuid, game);
                });
            })
        ]);

        this.lastGamesContainer = document.getElementById("lastGamesContainer");
        this.currentPromotionsContainer = document.getElementById("currentPromotionsContainer");
        this.gameTemplate = new GameTemplate();
        this.cartModal = new CartModal();
    }

    async ready() {
        this.appendGameMapToContainer(this.lastGamesContainer, this.dataLastGameMap);
        this.appendGameMapToContainer(this.currentPromotionsContainer, this.dataCurrentPromotionMap);
    }

    appendGameMapToContainer(container, map) {
        container.innerHTML = "";
        map.forEach(game => {
            const clone = this.gameTemplate.clone(game);
            if(clone) container.appendChild(clone);
        });
        container.addEventListener("game-click", event => {
            this.cartModal.addGame(event.detail.item);
        });
    }

    async refresh() {
        await this.ready();
    }

}