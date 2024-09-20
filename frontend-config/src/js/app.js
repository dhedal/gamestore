import {router} from "./routes/router.js";
import {ForgotPasswordForm, SigninForm, SignupForm} from "./forms/forms.js";
import {AuthenticationService} from "./services/authentication-service";
import {appContext} from "./config/app-context";

export class AppMenu {
    classActive;
    platformLinks;
    homeLink;
    cartLink;
    linkActive;
    loginBtn;
    workSpaceLink;
    userSpaceLink;
    logOutBtn;

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
        this.workSpaceLink = document.getElementById("gs-team-workspace");
        this.userSpaceLink = document.getElementById("user-space");
        this.logOutBtn = document.getElementById("logOutBtn");

        this.checkAuthData();

        this.initEvent();

    }

    checkAuthData() {
        if(AuthenticationService.isConnected()){
            this.loginBtn.classList.add("visually-hidden");
            this.userSpaceLink.classList.remove("visually-hidden");
            this.logOutBtn.classList.remove("visually-hidden");
            this.cartLink.classList.remove("visually-hidden");

            if(AuthenticationService.isEmployee()) {
                this.workSpaceLink.classList.remove("visually-hidden");
            }
            else {
                this.workSpaceLink.classList.add("visually-hidden");
            }
        }
        else {
            this.loginBtn.classList.remove("visually-hidden");
            this.workSpaceLink.classList.add("visually-hidden");
            this.userSpaceLink.classList.add("visually-hidden");
            this.logOutBtn.classList.add("visually-hidden");
            this.cartLink.classList.add("visually-hidden");
        }
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
            event.preventDefault();
            event.stopPropagation();
            this.resetLinkActive();
            event.target.href = "/home";
            window.route(event);
        });

        this.cartLink.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.resetLinkActive();
            event.target.href = "/cart";
            window.route(event);
        });

        this.loginBtn.addEventListener("click", event => {
            this.resetLinkActive();
        });

        this.logOutBtn.addEventListener("click", event => {
            this.resetLinkActive();
            AuthenticationService.logout();
            event.target.href = "/home";
            window.route(event);
        });

        this.workSpaceLink.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.resetLinkActive();
        });

        this.userSpaceLink.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.resetLinkActive();
            event.target.href = "/user-space";
            window.route(event);
        });


    }

    resetLinkActive() {
        if(!this.linkActive) return;
        this.linkActive.classList.remove(...this.classActive);
        this.linkActive = null;
    }
}

class App{
    constructor() {
    }

    run() {
        appContext.appMenu = new AppMenu();
        router.loadPage().then(() => {
            const signin = new SigninForm();
            const signup = new SignupForm();
            const forgotPassword = new ForgotPasswordForm();
        });
    }
}

export const app = new App();

