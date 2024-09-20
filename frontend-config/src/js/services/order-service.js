import {API_URL, HttpConfig} from "../config/config.js";
import {AuthenticationService} from "./authentication-service.js";

const  API_ORDER_URL = API_URL + "order";

export class OrderService {

    static fetchOrderDelivered = async (orderUuid) => {
        const response = await fetch(`${API_ORDER_URL}/status-delivered/${orderUuid}`);
        return await response.json();
    }

    static postRegister = async (order) => {
        order.user = AuthenticationService.getUser().uuid;
        const header = HttpConfig.postHeader(order);
        const response = await fetch(`${API_ORDER_URL}/register`, header);
        return await response.json();
    }

    static getOrdersByCreatedAt = async (date) => {
        const response = await fetch(`${API_ORDER_URL}/created-at/${date}`);
        return await response.json();
    }

    static getOrdersByUserUuid = async (userUuid) => {
        const response = await fetch(`${API_ORDER_URL}/user/${userUuid}`);
        return await response.json();
    }

    static postOrderSearchByEmail = async (requestData) => {
        const header = HttpConfig.postHeader(requestData);
        const response = await fetch(`${API_ORDER_URL}/search/email`, header);
        return await response.json();
    }
}