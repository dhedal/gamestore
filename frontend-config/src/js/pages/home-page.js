import { PageComponent } from "components/page-component.js";
import { GameService} from "../services/game-service.js";

export class HomePage extends PageComponent {
    dataLastGameMap;
    dataCurrentPromotionMap;
    lastGamesContainer;
    currentPromotionsContainer;

    constructor() {
        super("/home", "Accueil", "/html/home.html", ["js/views/home.js"]);
    }

    async before() {
        console.log("## homePage # before");
        this.dataLastGameMap = new Map();
        this.dataCurrentPromotionMap = new Map();

        await Promise.all([
            GameService.fetchHomePageData().then(response => {
                response.lastGames.forEach(game => {
                    this.dataLastGameMap.set(game.uuid, game);
                });
                response.currentPromotions.forEach(game => {
                    this.dataCurrentPromotionMap.set(game.uuid, game);
                });
            })
        ]);

        this.lastGamesContainer = document.getElementById("lastGamesContainer");
        this.currentPromotionsContainer = document.getElementById("currentPromotionsContainer");
    }

    ready() {
        console.log("## HomePage # ready");
        console.log(this.dataLastGameMap);
        console.log("-----------");
        console.log(this.dataCurrentPromotionMap);

    }

}