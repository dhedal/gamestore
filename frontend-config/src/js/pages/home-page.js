import { PageComponent } from "components/page-component.js";

export class HomePage extends PageComponent {

    constructor() {
        super("/home", "Accueil", "/html/home.html", ["js/views/home.js"]);
    }

    async before() {
        console.log("## homePage # before");
    }

    ready() {
        console.log("## HomePage # ready");

    }

}