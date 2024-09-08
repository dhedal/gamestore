import {PageComponent} from "../components/page-component.js";
import {GameTemplate, GenreTemplate} from "../templates/templates.js";
import {GameService} from "../services/game-service.js";
import {cache} from "../config/cache.js";
import noUiSlider from "nouislider";


class PriceSlider {
    htmlSlider;
    priceMin;
    priceMax;
    priceMinContainer;
    priceMaxContainer;
    resetPricesBtn;

    constructor(sliderId = "prices-slider", min = 0, max = 100) {
        this.htmlSlider = document.getElementById(sliderId);
        this.min = 0;
        this.max = 100;
        this.priceMin = this.min;
        this.priceMax = this.max;
        this.priceMinContainer = document.getElementById("price-min");
        this.priceMaxContainer = document.getElementById("price-max");
        this.resetPricesBtn = document.getElementById("slider-reset-btn");

        noUiSlider.create(this.htmlSlider, {
            start: [this.min, this.max],
            step: 1,
            connect: true,
            range: {
                'min': min,
                'max': max
            }
        });

        this.htmlSlider.noUiSlider.on("update", (values, handle) => {

            if(handle) {
                this.priceMax = values[handle];
            }
            else {
                this.priceMin = values[handle];
            }
            this.update();
        });

        this.resetPricesBtn.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation()
            this.reset();
        });

    }

    update() {
        this.priceMaxContainer.textContent = this.priceMax + "€";
        this.priceMinContainer.textContent = this.priceMin + "€";
    }

    getPrices() {
        return [this.priceMin, this.priceMax];
    }

    reset() {
        this.priceMax = this.max;
        this.priceMin = this.min;
        this.htmlSlider.noUiSlider.set([this.min, this.max]);
        this.update();
    }

}

class Pagination {
    paginationContainer;
    pages;
    currentPage = 1;
    platformGameCount = 0;
    nbGamePerPage = 9;
    pageCount;
    maxNumPage = 3;
    currentTag;

    constructor(paginationContainer = "paginationContainer") {
        this.paginationContainer = document.getElementById(paginationContainer);
        this.pages = new Map();
        this.nbGamePerPage = cache.gameArticleLimit ? cache.gameArticleLimit : 9;
    }

    init(platform) {
        this.platformGameCount = cache.getCount(platform);
        this.pageCount = Math.floor(this.platformGameCount / this.nbGamePerPage);

        this.maxNumPage = this.maxNumPage > this.pageCount ? this.pageCount : this.maxNumPage;

        if(this.pageCount > this.maxNumPage) {
            this.initPrevPage();
            this.initFirstPage();
        }

        if(this.pageCount > 1) {
            for(let i = 1; i <= this.maxNumPage; i++){
                const key = i + "";
                this.initNumPage(key, i);
                if(i === 1) {
                    this.currentTag = this.pages.get(key);
                    this.currentTag.classList.add("active");
                    this.currentPage = i;
                }
            }
        }

        if(this.pageCount > this.maxNumPage) {
            this.separator();
            this.initLastPage();
            this.initNextPage();
        }

    }

    active(tag) {
        this.currentTag.classList.remove("active");
        this.currentTag = tag;
        this.currentTag.classList.add("active");
    }

    initPrevPage() {
        const li = this.li();
        const a = this.a("Précédent");
        li.appendChild(a);
        this.paginationContainer.appendChild(li);


        a.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.currentPage--;

            if(this.currentPage < 1) {
                this.currentPage = 1;
                return;
            }

            if(this.currentPage === 1) {
                this.currentPage = 1;
                this.active(this.pages.get("1"));
            }
            else if(this.currentPage % this.maxNumPage === 0) {
                this.pages.get("1").textContent = this.currentPage - 2;
                this.pages.get("2").textContent = this.currentPage - 1;
                this.pages.get("3").textContent = this.currentPage;

                this.active(this.pages.get("3"));
            }
            else {
                const n = (this.currentPage % this.maxNumPage) ;
                this.active(this.pages.get("" + n));
            }

            this.loadPage(this.currentPage);
        });

        this.pages.set("prev", a);
    }

    initFirstPage() {
        const li = this.li();
        const a = this.a("Premier");
        li.appendChild(a);
        this.paginationContainer.appendChild(li);

        a.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.currentPage = 1;
            this.active(this.pages.get("1"));

            this.pages.get("1").textContent = this.currentPage;
            this.pages.get("2").textContent = this.currentPage + 1;
            this.pages.get("3").textContent = this.currentPage + 2;

            this.loadPage(this.currentPage);
        });

        this.pages.set("first", a);
    }

    separator(){
        const li = this.li();
        const a = this.a("...")
        li.appendChild(a);
        this.paginationContainer.append(li);
        a.href= "javascript:void(0)";
    }

    initLastPage() {
        const li = this.li();
        const a = this.a(this.pageCount);
        li.appendChild(a);
        this.paginationContainer.appendChild(li);

        a.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.currentPage = this.pageCount;
            this.active(a);
            const pg = this.pageCount - this.maxNumPage;
            for(let i = 0; i < this.maxNumPage; i++) {
                const key = (i + 1) + "";
                this.pages.get(key).textContent = pg + i;

            }
            this.loadPage(this.currentPage);
        });

        this.pages.set("last", a);
    }

    initNextPage() {
        const li = this.li();
        const a = this.a("Suivant");
        li.appendChild(a);
        this.paginationContainer.appendChild(li);

        a.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();

            this.currentPage++;

            if(this.currentPage > this.pageCount){
                this.currentPage = this.pageCount;
                return;
            }

            if(this.currentPage === this.pageCount){
                this.active(this.pages.get("last"));
            }
            else if((this.currentPage) % this.maxNumPage === 1) {
                this.pages.get("1").textContent = this.currentPage;
                this.pages.get("2").textContent = this.currentPage + 1;
                this.pages.get("3").textContent = this.currentPage + 2;
                this.active(this.pages.get("1"));
            }
            else {
                const modulo = (this.currentPage) % this.maxNumPage;
                const key = modulo === 2 ? "2" : "3";
                this.active(this.pages.get(key));
            }

            this.loadPage(this.currentPage);

        });

        this.pages.set("next", a);
    }

    initNumPage(key, num) {
        const li = this.li();
        const a = this.a(num);
        li.appendChild(a);
        this.paginationContainer.appendChild(li);

        a.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.currentPage = parseInt(a.textContent);
            this.active(a);
            this.loadPage(parseInt(a.textContent));
        });

        this.pages.set(key, a);
    }

    li() {
        const li = document.createElement("li");
        li.classList.add("page-item");
        return li;
    }

    a(text) {
        const a = document.createElement("a");
        a.classList.add("page-link");
        a.href = "#";
        a.textContent = text;
        return a;
    }

    loadPage(num) {
        console.log("load : " + num);
    }
}

class GameFilter {
    currentPage;
    platform;
    genres;
    genreTemplate;
    genreContainer;
    priceSlider;
    pagination;

    constructor(platform) {
        this.platform = platform;
        this.currentPage = 0;
        this.genres = new Array();
        this.genreTemplate = new GenreTemplate();
        this.genreContainer = document.getElementById("genreContainer");
        this.priceSlider = new PriceSlider();
        this.pagination = new Pagination();
        this.pagination.init(this.platform);


        this.init();
    }

    init() {
        cache.genreMap.forEach(genre => {
            if(genre.key !== 0) {
                const clone = this.genreTemplate.clone(genre);
                this.genreContainer.appendChild(clone);
            }
        });

        this.genreContainer.querySelectorAll("button[data-genre]").forEach(button => {
            button.addEventListener("click", event => {
                event.preventDefault();
                event.stopPropagation();
                const genre = button.getAttribute("data-genre");
                if (this.genres.includes(genre)) {
                    button.classList.remove("btn-primary");
                    button.classList.add("btn-outline-primary");
                    this.genres = this.genres.filter(item => item !== genre);
                } else {
                    button.classList.remove("btn-outline-primary");
                    button.classList.add("btn-primary");
                    this.genres.push(genre);
                }
            });
        });

    }

    get() {
        return {
            platformKey: this.platform.key,
            genreKeys: this.genres,
            priceMin: this.priceSlider.priceMin,
            priceMax: this.priceSlider.priceMax,
            page: this.pagination.currentPage - 1,
            limit: this.pagination.nbGamePerPage
        };
    }

    reset() {
        this.priceSlider.reset();
        this.genreContainer.querySelectorAll("button[data-genre]").forEach(button => {
            const genre = button.getAttribute("data-genre");
            if (this.genres.includes(genre)) {
                button.classList.remove("btn-primary");
                button.classList.add("btn-outline-primary");
                this.genres = this.genres.filter(item => item !== genre);
            }
        });
        this.genres = new Array();

    }

}

export class GameListPage extends PageComponent {
    dataGameMap;
    gamesContainer;
    gameTemplate;
    platformName;
    gameFilter;
    pagination;
    submitFilterBtn

    constructor() {
        super("/game-list", "Listes", "/html/game-list.html", [""]);
    }

    async before() {
        this.dataGameMap = new Map();
        this.gamesContainer = document.getElementById("gamesContainer");
        this.gameTemplate = new GameTemplate();

        const platform = cache.platformMap.get(parseInt(this.param.get("pkey")));
        this.platformName = document.getElementById("platformName");
        this.platformName.textContent = platform.label;

        this.gameFilter = new GameFilter(platform);

        this.submitFilterBtn = document.getElementById("filter-submit-btn");
        this.submitFilterBtn.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            GameService.postGamesByFilter(this.gameFilter.get()).then(response => {
                this.setGameDatas(response);
                this.fillGameContainer();
            });
        });

        await Promise.all([
            GameService.postGamesByFilter(this.gameFilter.get()).then(response => {
                this.setGameDatas(response);
            })
        ]);
    }

    ready() {
        this.fillGameContainer();
    }

    fillGameContainer() {
        this.gamesContainer.innerHTML = "";
        this.dataGameMap.forEach(game => {
            const clone = this.gameTemplate.clone(game);
            this.gamesContainer.appendChild(clone);
            clone.addEventListener("click", event => {
                console.log(clone);
            })
        });
    }

    setGameDatas(gameDatas){
        this.dataGameMap.clear();
        Object.entries(gameDatas).forEach(([key, value]) => {
            this.dataGameMap.set(key, value);
        });
    }
}