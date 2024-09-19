import {PageDataService} from "../services/page-data-service.js";


class Cache {
    platformMap = null;
    genreMap = null;
    gameArticleLimit
    gameCountMap = null;
    dataTempMap;
    orderStatusMap;
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

    getKeyOrderStatusDelivred() {
        return this.orderStatusMap.get(2).key;
    }

    getKeyOrderStatusValidated() {
        return this.orderStatusMap.get(1).key;
    }

    async init() {
        this.platformMap = new Map();
        this.genreMap = new Map();
        this.gameCountMap = new Map();
        this.orderStatusMap = new Map();
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
            datas.orderStatus.forEach(status => {
                this.orderStatusMap.set(status.key, status);
            });
        });
    }

    async check() {
        if(this.platformMap == null || this.genreMap == null || this.orderStatusMap == null) {
            await this.init();
        }
    }
}

export const cache = new Cache();