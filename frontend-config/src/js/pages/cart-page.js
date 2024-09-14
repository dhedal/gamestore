import {PageComponent} from "../components/page-component.js";
import {CartGameTemplate} from "../templates/templates.js";
import {CartService} from "../services/cart-service.js";
import {PageDataService} from "../services/page-data-service.js";
import {OrderService} from "../services/order-service";
import {GeolocationService} from "../services/geolocation-service.js";
import { Carousel } from 'bootstrap';


export class CartPage extends PageComponent{
    cartGameTemplate;
    cartGameContainer;
    factureContainer;
    agenceMap;
    reserveBtn;
    orderBtn;
    cityValue;
    pickupDateValue;

    constructor() {
        super("/cart", "Panier", "/html/cart.html", []);
    }

    async before() {
        this.cartGameTemplate = new CartGameTemplate();
        this.cartGameContainer = document.getElementById("cart-game-container");
        this.factureContainer = document.getElementById("facture-container");
        this.agenceMap = new Map();
        this.reserveBtn = document.getElementById("reserveBtn");
        this.orderBtn = document.getElementById("orderBtn");

        await PageDataService.postGetCartPageData(CartService.getGameUuidList()).then(response => {
            const stockMap = new Map();
            Object.entries(response.stockMap).forEach(([uuid, stock]) => {
                stockMap.set(uuid, stock);
            });
            CartService.updateStock(stockMap);

            response.agences.forEach(agence => {
                this.agenceMap.set(agence.city, agence);
            });

        });

        this.orderBtn.addEventListener("click", event => {
            const order = {
                articles :CartService.getArticlesOrder(),
                agence : this.agenceMap.get(this.cityValue).uuid,
                date: new Date(this.pickupDateValue),
            }
            OrderService.postRegister(order).then(response => {
                const messageTag = document.getElementById("confirmation-message");
                messageTag.innerHTML = "";
                if(response.ok) {
                    CartService.clear();
                    let message ="Merci pour votre commande !";
                    if(response.emailSent) {
                        message += " Un email de confirmation vous a été envoyé.";
                    }
                    messageTag.textContent = message;

                }
                else {
                    messageTag.textContent = "Une erreur est survenue lors de l'enregistrement de votre commande. Veuillez réessayer plus tard."
                }
                const carouselElement = document.getElementById("cartStepCarousel");
                const carousel = new Carousel(carouselElement);
                carousel.to(2);
            })
        });

        this.cartGameContainer.addEventListener("cart-game-remove-event", event => {
            event.preventDefault();
            CartService.removeGame(event.detail.item);
            this.render();
        });


        this.cartGameContainer.addEventListener("cart-game-sub-event", event => {
            event.preventDefault();
            CartService.addGame(event.detail.item, -1);
            this.render();
        });

        this.cartGameContainer.addEventListener("cart-game-add-event", event => {
            event.preventDefault();
            CartService.addGame(event.detail.item);
            this.render();
        });

    }

    async ready() {
        this.choosePickupLocationAndDate();
        this.render();
    }

    render() {
        const cart = CartService.getCart();
        this.cartGameContainer.innerHTML = "";

        if(cart == null || cart.size <= 0) {
            document.getElementById("cart-empty-message").classList.remove("visually-hidden")
            this.cartGameContainer.parentNode.parentNode.classList.add("visually-hidden");
        }
        else {
            this.cartGameContainer.parentNode.parentNode.classList.remove("visually-hidden");
            cart.forEach(item => {
                const clone = this.cartGameTemplate.forCartPage(item);
                this.cartGameContainer.appendChild(clone);
            });

            let facturePrice = 0, facturePromo = 0, facture = 0;
            [facturePrice, facturePromo, facture] = CartService.getFacture();

            this.factureContainer.querySelector(".cart-facture-price").textContent = facturePrice;
            this.factureContainer.querySelector(".cart-facture-promo").textContent = "-" + facturePromo;
            this.factureContainer.querySelector(".cart-facture").textContent = facture;

            const disabled = facture <= 0;
            this.reserveBtn.disabled = disabled;
        }

    }

    choosePickupLocationAndDate(){
        this.pickupLocation();

        const dayNames = ["Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"];
        const monthNames = ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"];
        const now = new Date();

        const pickupDateContainer = document.getElementById("pickup-dates");

        for(let i = 0; i < 7; i++) {
            const pickupDate = new Date(now);
            pickupDate.setDate(pickupDate.getDate() + i);
            const day = pickupDate.getDay();


            if(day !== 0 && day !== 1) {
                const dateString = `${dayNames[day]}, le ${pickupDate.getDate()} ${monthNames[pickupDate.getMonth()]} ${pickupDate.getFullYear()}`;
                pickupDateContainer.appendChild(this.pickupDateDiv(dateString, day, pickupDate));
            }
        }

        const radios = document.getElementsByName("pickup-date-radio");
        radios.forEach(radio => {
            radio.addEventListener("change", event => {
                event.stopPropagation();
                this.pickupDateValue = event.target.value;
                this.checkOrderValidate();
            })
        });
    }

    pickupDateDiv(dateString, day, pickupDate) {
        const div = document.createElement("div");
        div.classList.add("input-group", "mb-3");
        div.innerHTML = `
        <div class="input-group-text">
            <input class="form-check-input" type="radio" name="pickup-date-radio" id="pickup-date-radio-${day}" value="${pickupDate.toISOString()}" />
        </div>
        <label class="form-control form-check-label" for="pickup-date-radio-${day}">${dateString}</label>
        
        `;
        return div;
    }

    pickupLocation() {
        const pickupLocationContainer = document.getElementById("pickup-location");
        this.agenceMap.forEach((agence, city) => {
            pickupLocationContainer.appendChild(this.option(city));
        });

        GeolocationService.getClosestAgency(this.agenceMap)
            .then(agency => {
                this.cityValue = agency.city;
                if(this.cityValue) pickupLocationContainer.value = this.cityValue;
            })
            .catch(error => {
                console.error("Erreur de géolocalisation :", error.message)
            });

        pickupLocationContainer.addEventListener("change", event => {
            event.stopPropagation();
            this.cityValue = event.target.value;
            this.checkOrderValidate();
        });
    }

    option(city) {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        return option;
    }

    checkOrderValidate() {
        const disabled = this.cityValue == null ||
            this.cityValue.length === 0 ||
            this.pickupDateValue == null ||
            this.pickupDateValue.length === 0;
        this.orderBtn.disabled = disabled;
    }


}