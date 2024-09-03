import {API_URL} from "../config/config.js";

const  API_PAGE_DATA_URL = API_URL + "page-data";
export class PageDataService {
    static fetchHomePageData = async () =>{
        const response = await fetch( `${API_PAGE_DATA_URL}/home_page_data`);
        return await response.json();
    }
}