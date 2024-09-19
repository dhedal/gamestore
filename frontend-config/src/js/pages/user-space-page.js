import {PageComponent} from "../components/page-component.js";
import {UserModifyForm} from "../forms/forms.js";
import {AuthenticationService} from "../services/authentication-service.js";
import {MessageUtils} from "../utils/message-utils.js";
import {OrderTemplate} from "../templates/templates.js";
import {OrderService} from "../services/order-service.js";
import {cache} from "../config/cache.js";


export class UserSpacePage extends PageComponent {

    userModifForm;
    user;
    orderTemplate;
    ordersContainerMap;
    currentOrderContainer;
    orderByStatusMap;
    orderStatusMap;
    orderDelivredBtn
    orderValidatedBtn;
    constructor() {
        super("/user-space", "Mon espace", "/html/user-space.html", []);
    }

    async before() {
        this.ordersContainerMap = new Map();
        this.ordersContainerMap.set(cache.getKeyOrderStatusValidated(), document.getElementById("orders-validated-container"));
        this.ordersContainerMap.set(cache.getKeyOrderStatusDelivred(), document.getElementById("orders-delivred-container"));
        this.user = AuthenticationService.getUser();
        if(this.user == null) {
            AuthenticationService.logout();
            MessageUtils.danger("Un problème est survenue, veuillez vous reconnecter!");
        }
        else {
            this.userModifForm = new UserModifyForm();
            this.userModifForm.fill(this.user);
        }
        this.orderByStatusMap = new Map();
        this.orderStatusMap = new Map();
        // const dateIsoString = new Date(2024, 8, 17).toISOString();

        await Promise.all([
            OrderService.getOrdersByUserUuid(this.user.uuid).then(response => {
                response.forEach(order => {
                    this.addOrder(order);
                })
            })
        ]);

        this.orderTemplate = new OrderTemplate();

        this.orderDelivredBtn = document.getElementById("order-delivred-btn");
        this.orderDelivredBtn.addEventListener("click", event => {
            this.switchOrderContainer(cache.getKeyOrderStatusDelivred());
        });

        this.orderValidatedBtn = document.getElementById("order-validated-btn")
        this.orderValidatedBtn.addEventListener("click", event => {
            this.switchOrderContainer(cache.getKeyOrderStatusValidated());
        });



    }

    async ready() {
        this.fillOrders();
        this.switchOrderContainer(cache.getKeyOrderStatusValidated());
    }

    addOrder(order) {
        if(order == null) return;

        const status = order.status;
        if(!this.orderStatusMap.has(status.key)) this.orderStatusMap.set(status.key, status);

        if(!this.orderByStatusMap.has(status.key)) this.orderByStatusMap.set(status.key, new Array());

        this.orderByStatusMap.get(status.key).push(order);
    }

    clearOrderMap() {
        this.orderByStatusMap.forEach((array) => array.clear());
    }

    fillOrders(){
        this.orderByStatusMap.forEach((value, key) => {
            const container = this.ordersContainerMap.get(key);
            container.innerHTML = "";
            value.forEach(order => {
                const clone = this.orderTemplate.clone(order);
                container.appendChild(clone);
            });
        })
    }

    switchOrderContainer(orderStatusKey) {
        if(this.currentOrderContainer) {
            this.currentOrderContainer.classList.add("visually-hidden");
        }
        this.currentOrderContainer = this.ordersContainerMap.get(orderStatusKey);
        this.currentOrderContainer.classList.remove("visually-hidden");
    }
}