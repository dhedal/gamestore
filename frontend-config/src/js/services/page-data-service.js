import {API_URL, HttpConfig} from "../config/config.js";

const  API_PAGE_DATA_URL = API_URL + "page-data";
export class PageDataService {
    static fetchHomePageData = async () =>{
        const response = await fetch( `${API_PAGE_DATA_URL}/home_page_data`);
        return await response.json();
    }

    static postGetCartPageData = async (uuidList = []) =>{
        const header = HttpConfig.postHeader(uuidList);
        const response = await fetch( `${API_PAGE_DATA_URL}/cart_page_data`, header);
        return await response.json();
    }

    static fetchCacheData = async () => {
        const response = await fetch( `${API_PAGE_DATA_URL}/cache-data`);
        return await response.json();
    }
}