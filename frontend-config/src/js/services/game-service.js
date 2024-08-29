import {API_URL} from "../config/config.js";

const  API_GAME_URL = API_URL + "game";
export class GameService {
    static fetchHomePageData = async () =>{
        const response = await fetch( `${API_GAME_URL}/home_page_data`);
        return await response.json();
    }
}