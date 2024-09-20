import { Page404 } from "../pages/404-page.js";
import { HomePage } from "../pages/home-page.js";
import {GameListPage} from "../pages/game-list-page.js";
import {cache} from "../config/cache.js";
import {GameDetailPage} from "../pages/game-detail-page.js";
import {CartPage} from "../pages/cart-page.js";
import {UserSpacePage} from "../pages/user-space-page.js";
import {appContext} from "../config/app-context.js";
import {ResetPasswordPage} from "../pages/reset-password-page.js";
import {WorkSpacePage} from "../pages/work-space-page.js";

export const WEBSITE_NAME = "GameStore";

class Router {
    _pages;

    constructor() {
        this._pages = new Map();
        this._addPage(new HomePage());
        this._addPage(new GameListPage());
        this._addPage(new GameDetailPage());
        this._addPage(new CartPage());
        this._addPage(new UserSpacePage());
        this._addPage(new WorkSpacePage());
        this._addPage(new ResetPasswordPage());
        this._addPage(new Page404());
        this.init();
    }

    _addPage(page){
        this._pages.set(page.url, page);
    }

    getPage(url) {
        return this._pages.get(url) || this._pages.get("404");
    }

    init(){
        // Gestion de l'événement de retour en arrière dans l'historique du navigateur
        window.onpopstate = () => this.loadPage();

        // Assignation de la fonction routeEvent à la propriété route de la fenêtre
        window.route = (event) => {
            event.preventDefault();
            window.history.pushState({}, "", event.target.href);
            this.loadPage();
        };
    }


    /**
     *
     */
    async loadPage() {
        await cache.check();
        appContext.appMenu.checkAuthData();
        const page = this.getPage(window.location.pathname);
        appContext.currentPage = page;
        await page._init();
        await page._onLoaded();

    }
}

// TODO : afficher/ masquer des éléments
export const actionWhenPageLoaded = () => {};


/**
 * fonction qui permet d'éviter
 * les failles XSS injection HTML
 * @param text
 * @returns {string}
 */
const sanitizeHtml = (text) => {
    const tempHtml = document.createElement("div");
    tempHtml.textContent = text;
    return tempHtml.innerHTML;
};


export const router = new Router();
