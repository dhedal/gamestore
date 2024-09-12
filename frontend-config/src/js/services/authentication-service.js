import {API_URL, HttpConfig} from "../config/config.js";

const  API_AUTH_URL = API_URL + "auth";

export class AuthenticationService {
    static AUTH_DATA_KEY = "authData";

    static postRestPassword = async (resetPassword) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(resetPassword);
        const response = await fetch(`${API_AUTH_URL}/reset-password`, parameters);
        return await response.json();
    }

    static postSignup = async (streamer) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(streamer);
        const response = await fetch(`${API_AUTH_URL}/signup`, parameters);
        return await response.json();
    }

    static postSignin = async (authData) => {
        const parameters = AuthenticationService.httpHeader("POST");
        parameters.body = JSON.stringify(authData);
        const response = await fetch(`${API_AUTH_URL}/signin`, parameters);
        return await response.json();
    }

    static fetchForgotPassword = async(email) => {
        const response = await fetch(`${API_AUTH_URL}/forgot-password/${email}`);
        return await response.json();
    }

    static setAuthData(authData) {
        localStorage.setItem(AuthenticationService.AUTH_DATA_KEY, JSON.stringify(authData));
    }

    static getAuthData() {
        const authDataStr = localStorage.getItem(AuthenticationService.AUTH_DATA_KEY);
        return authDataStr ? JSON.parse(authDataStr) : null;
    }

    static getUser() {
        const authData = AuthenticationService.getAuthData();
        return authData ? authData.user : null;
    }

    static getRole() {
        const user = AuthenticationService.getUser();
        return user ? user.rule.label : null;
    }

    static getToken() {
        const authData = AuthenticationService.getAuthData();
        return authData ? authData.token : null;
    }

    static logout() {
        AuthenticationService.setAuthData(null);
    }

    static isConnected() {
        // TODO: mettre en place JWT
        // const token = AuthenticationService.getToken();
        // return token != null && token.length > 1;
        return true;
    }

    static httpHeader(method, withAuthorization = false) {
        const parameters = {
            method: method,
            headers : {
                'Content-Type': 'application/json'
            }
        };
        if(withAuthorization) parameters.headers.Authorization = `Bearer ${AuthenticationService.getToken()}`;
        console.log(parameters);
        return parameters;
    }

    static fetchIsEmailUnique = async (email) => {
        const response = await fetch(`${API_AUTH_URL}/is-email-unique/${email}`);
        return await response.json();
    }

    static isAdmin() {
        return AuthenticationService.getRole() === "admin";
    }

    static isEmployee() {
        return AuthenticationService.getRole() === "employee";
    }
}