import {API_URL, HttpConfig} from "../config/config.js";
import {AuthenticationService} from "./authentication-service.js";

const  API_ORDER_URL = API_URL + "order";

export class OrderService {

    static postRegister = async (order) => {
        order.user = AuthenticationService.getUser().uuid;
        const header = HttpConfig.postHeader(order);
        const response = await fetch(`${API_ORDER_URL}/register`, header);
        return await response.json();
    }
}