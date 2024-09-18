import {PageComponent} from "../components/page-component.js";


export class UserSpacePage extends PageComponent {
    constructor() {
        super("/user-space", "Mon espace", "/html/user-space.html", []);
    }

    async before() {
        console.log("## userSpace #before()");
    }

    async ready() {
        console.log("## userSpace #ready()");
    }
}