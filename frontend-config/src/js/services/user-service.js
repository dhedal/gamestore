import {API_URL, HttpConfig} from "../config/config.js";
import {AuthenticationService} from "./authentication-service.js";

const  API_USER_URL = API_URL + "user";

export class UserService {
    static postUserInfoUpdate = async (userInfo) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(userInfo);
        const response = await fetch(`${API_USER_URL}/update`, parameters);
        return await response.json();
    }
}