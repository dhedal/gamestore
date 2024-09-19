import {PageComponent} from "../components/page-component.js";
import {UserModifyForm} from "../forms/forms";
import {AuthenticationService} from "../services/authentication-service";
import {MessageUtils} from "../utils/message-utils";


export class UserSpacePage extends PageComponent {

    userModifForm;
    user;
    constructor() {
        super("/user-space", "Mon espace", "/html/user-space.html", []);
    }

    async before() {
        this.user = AuthenticationService.getUser();

        if(this.user == null) {
            AuthenticationService.logout();
            MessageUtils.danger("Un problème est survenue, veuillez vous reconnecter!");
        }
        else {
            this.userModifForm = new UserModifyForm();
            this.userModifForm.fill(this.user);
        }


    }

    async ready() {

    }
}