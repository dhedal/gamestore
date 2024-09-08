import {PageDataService} from "../services/page-data-service.js";


class Cache {
    platformMap = null;
    genreMap = null;
    gameArticleLimit
    gameCountMap = null;
    dataTempMap;
    constructor(limitDefault = 9) {
        this.gameArticleLimit = limitDefault;
        this.dataTempMap = new Map();
    }

    getCount(platform){
        if(platform == null || this.gameCountMap == null || this.gameCountMap.size == 0) return 0;
        return this.gameCountMap.get(platform.key);
    }

    consumeDataTemp(key) {
        const value =  this.dataTempMap.get(key);
        if(value) this.dataTempMap.delete(key);
        return value;
    }

    saveDataTemp(key, value) {
        if(key != null && value != null) this.dataTempMap.set(key, value);
    }

    async init() {
        this.platformMap = new Map();
        this.genreMap = new Map();
        this.gameCountMap = new Map();
        await PageDataService.fetchCacheData().then(datas => {
            Object.entries(datas.gameCountMap).forEach(([key, value]) => {
                this.gameCountMap.set(parseInt(key), value);
            });


            datas.platforms.forEach(platform => {
                this.platformMap.set(platform.key, platform);
            });
            datas.genres.forEach(genre => {
                this.genreMap.set(genre.key, genre);
            });


        });
    }

    async check() {
        if(this.platformMap == null || this.genreMap == null) {
            await this.init();
        }
    }
}

export const cache = new Cache();