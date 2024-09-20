import {PageComponent} from "../components/page-component.js";
import {ResetPasswordForm} from "../forms/forms.js";
import {ResetPasswordService} from "../services/reset-password-service.js";


export class ResetPasswordPage extends PageComponent {

    resetPasswordForm;
    messageContainer;
    formContainer;

    constructor() {
        super("/reset-password", "Mot de passe oublié", "/html/reset-password.html", []);
    }

    async before(){
        this.messageContainer = document.getElementById("message-container");
        this.formContainer = document.getElementById("form-container");


        const token = this.param.get("token");

        await ResetPasswordService.postCheckToken(token).then(response => {
            if(response) {
                this.formContainer.classList.remove("visually-hidden");
                this.resetPasswordForm = new ResetPasswordForm(token);
            }
            else {
                this.messageContainer.innerHTML = "Le token est invalide, veuillez repasser par l'étape: j'ai oublié mon mot de passe.";
            }
        });
    }

    async ready(){
    }

}