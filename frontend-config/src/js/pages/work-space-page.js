import {PageComponent} from "../components/page-component.js";
import {MemberForm, OrderSearchForm} from "../forms/forms.js";
import {OrderTemplate} from "../templates/templates.js";
import {cache} from "../config/cache.js";
import {OrderService} from "../services/order-service";
import {MessageUtils} from "../utils/message-utils";

export class WorkSpacePage extends PageComponent {
    orderContainer;
    orderSearchForm;
    orderTemplate;
    memberForm;
    constructor() {
        super("/work-space", "L'équipe", "/html/work-space.html", []);
    }

    async before() {
        console.log("## WorkSpacePage # before");
        this.orderContainer = document.getElementById("order-container");
        this.orderContainer.addEventListener("order-delivered", event => {
            const orderUuid = event.detail.order;
            OrderService.fetchOrderDelivered(orderUuid).then(response => {
                if(response.ok) {
                    MessageUtils.success("La livraison de la commande a bien été validée.");
                    this.fillOrders(response.orders);
                }
                else{
                    const messages = Array.from(response.messages);
                    messages.forEach(message => {
                        MessageUtils.danger(message);
                    });
                }
            });
        });

        this.orderSearchForm = new OrderSearchForm(this.fillOrders.bind(this));
        this.orderTemplate = new OrderTemplate();
        this.orderTemplate.isTeam = true;

        this.memberForm = new MemberForm();
    }

    async ready() {
        console.log("## WorkSpacePage # ready");
    }

    fillOrders(orders = new Array()) {
        this.orderContainer.innerHTML = "";
        orders.forEach(order => {
            if(order.status.key === cache.getKeyOrderStatusValidated()) {
                const clone = this.orderTemplate.clone(order);
                this.orderContainer.appendChild(clone);
            }
        });
    }

}