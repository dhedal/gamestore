import {API_URL} from "../config/config.js";
import {AuthenticationService} from "./authentication-service.js";

const  API_RESET_PASSWORD_URL = API_URL + "reset-password";

export class ResetPasswordService {
    static postResetPassword = async (requestData) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(requestData);
        const response = await fetch(`${API_RESET_PASSWORD_URL}/reset`, parameters);
        return await response.json();
    }

    static postCheckToken = async (token) => {
        const response = await fetch(`${API_RESET_PASSWORD_URL}/check-token/${token}`);
        return await response.json();
    }

    static postForgotPassword = async (requestData) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(requestData);
        const response = await fetch(`${API_RESET_PASSWORD_URL}/create-token`, parameters);
        return await response.json();
    }
}