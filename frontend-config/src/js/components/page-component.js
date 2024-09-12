import { router, WEBSITE_NAME } from "../routes/router.js";
import {cache} from "../config/cache";
import {appContext} from "../config/app-context.js";

export class PageComponent {
    _container = "main-page";
    _url;
    _name;
    _pathHtml;
    _pathJS;
    _param

    constructor(url, name, pathHtml, pathJs = []) {
        this._url = url;
        this._name = name;
        this._pathHtml = pathHtml;
        this._pathJS = pathJs;
        appContext.currentPage = this;
    }

    get url() { return this._url;}
    get pathHtml () { return this._pathHtml;}
    get param () { return this._param;}


    async _init() {
        const html = await fetch(this._pathHtml).then(data => data.text());
        document.getElementById(this._container).innerHTML = html;

        if(this._pathJS) {
            this._pathJS.forEach(js => {
                if(js != null || js != ""){
                    const scriptTag = document.createElement("script");
                    scriptTag.setAttribute("type", "module");
                    scriptTag.setAttribute("src", js);
                    document.querySelector("body").appendChild(scriptTag);
                }
            });
        }


        document.title = this._name + " - " + WEBSITE_NAME;

        this._param = new URLSearchParams(window.location.search);
    }

    async _onLoaded() {
        try {
            await this.before();
            await this.ready();
        } catch( e) {
            await this.error(e);
        }

    }

    async before() {}

    async ready() {}

    async error(exception) { console.log(exception);}

    async refresh(){}
}