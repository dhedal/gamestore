import {router} from "./routes/router.js";

export class AppMenu {
    classActive;
    platformLinks;
    homeLink;
    cartLink;
    linkActive;
    loginBtn;

    constructor() {
        this.classActive = ["border", "rounded"];
        this.platformLinks = [
            document.getElementById("app-menu-pc-link"),
            document.getElementById("app-menu-playstation-link"),
            document.getElementById("app-menu-xbox-link"),
            document.getElementById("app-menu-switch-link")
        ];

        this.homeLink = document.getElementById("app-menu-home-link");
        this.cartLink = document.getElementById("app-menu-cart-link");
        this.loginBtn = document.getElementById("loginBtn");

        this.initEvent();
    }

    initEvent() {
        this.platformLinks.forEach(item => {
            item.addEventListener("click", event => {
                event.preventDefault();
                this.resetLinkActive();
                this.linkActive = item;
                this.linkActive.classList.add(...this.classActive);
                window.route(event);
            });
        });

        this.homeLink.addEventListener("click", event => {
            this.resetLinkActive();
            // window.route(event);
        });

        this.cartLink.addEventListener("click", event => {
            this.resetLinkActive();
            window.route(event);
        });

        this.loginBtn.addEventListener("click", event => {
            this.resetLinkActive();
        });

    }

    resetLinkActive() {
        if(!this.linkActive) return;
        this.linkActive.classList.remove(...this.classActive);
        this.linkActive = null;
    }
}
