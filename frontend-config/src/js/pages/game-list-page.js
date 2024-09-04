import {PageComponent} from "../components/page-component";


export class GameListPage extends PageComponent {

    constructor() {
        super("/game-list", "Listes", "/html/game-list.html", [""]);
    }

    async before() {
        console.log("## gameList # before");
        console.log(this.param.get("platform"));
    }

    ready() {
        console.log("## gameList # ready");
    }
}