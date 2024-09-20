import {AuthenticationService} from "../services/authentication-service.js";
import {MessageUtils} from "../utils/message-utils.js";
import {appContext} from "../config/app-context.js";
import {AuthenticationModal} from "../modals/modal.js";
import {Carousel} from "bootstrap";
import {UserService} from "../services/user-service.js";
import {ResetPasswordService} from "../services/reset-password-service.js";


class Validator {

    static validateInputRequired = (input) => {
        return input.required && input.value !== "";
    }

    static validateStringEquals = (s1, s2) => {
        return s1 === s2;
    }

    static validateEmailInput = (email) => {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value);
    }

    static validateStringLength(string = "", minLength = 8){
        return !(string.length < minLength);
    }

    static validateStringContainsUpperAndLowerCase(string = ""){
        return /[A-Z]/.test(string) && /[a-z]/.test(string);
    }

    static validateStringContainsCharNumber(string = ""){
        return /[0-9]/.test(string);
    }

    static validateStringContainsOnlyZipCode(zipcode = "") {
        return /^[0-9]{5}(?:-[0-9]{4})?$/.test(zipcode);
    }

    static validateStringContainsSpecialCharacters(string = "", pattern = /[!@#$%&*?:+-]/){
        return pattern.test(string);
    }

    static validateInputNotEmpty(inputKey, input) {
        if(Validator.validateInputRequired(input)) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    }

    static validateZipCode(inputKey, input) {
        if(Validator.validateStringContainsOnlyZipCode(input.value)) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    }

    static validateEmail(inputKey, input) {
        if(Validator.validateEmailInput(input)) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }

        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    }

    static validatePassword(inputKey, input) {
        const value = input.value;
        const isValid = !Array.of(
            Validator.validateStringLength(value),
            Validator.validateStringContainsUpperAndLowerCase(value),
            Validator.validateStringContainsCharNumber(value),
            Validator.validateStringContainsSpecialCharacters(value)
        ).includes(false);

        if(isValid) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    }
}

class Form {
    _formElement;
    _fnMap;
    _inputArray;
    _submit;
    constructor(formElement, submitElement) {
        this._formElement = document.getElementById(formElement);
        this._submit = document.getElementById(submitElement);
        this._submit.disabled = true;
        this._fnMap = new Map();
        this._inputArray = new Array();

        this._submit.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();
            this.send(this._extractData());
        });
    }

    _getInput(inputKey) {
        return this._inputArray.find(([key, input]) => inputKey === key);
    }

    _setInputValue(inputId, value) {
        const input = document.getElementById(inputId);
        if(input) input.value = value;
    }

    _addInputs(inputId, eventType, fnValidate) {
        const input = document.getElementById(inputId);
        this._fnMap.set(inputId, fnValidate);
        this._inputArray.push([inputId, input]);
        input.addEventListener(eventType, event => {
            event.stopPropagation();
            this.validateForm();
        });
        return inputId
    }

    validateForm() {
        this._validateForm();
    }

    _validateForm() {
        this._submit.disabled = this._inputArray
            .map(([key, input]) => this._fnMap.get(key)(key, input))
            .includes(false);
    }

    _extractData() {
        const data = new Map();
        this._inputArray.forEach(([inputName, input]) => {
            data.set(inputName, input.value);
        });
        return data;
    }

    async send(data) {
    }

    _clearValidOrInvalidCSS = (element) => {
        if(element.classList.contains("is-valid")) {
            element.classList.remove("is-valid");
        }
        else {
            element.classList.remove("is-invalid");
        }
    }

    _clear() {
        this._inputArray.forEach(([elementKey, element]) => {
            this._clearValidOrInvalidCSS(element);
            if(element.tagName === "INPUT") element.value = "";
            else element.textContent = "";
        });
    }

    clear(){
        this._clear();
    }

}

export class SigninForm extends Form{
    email;
    password;
    authModal;

    constructor(formElement = "signin-form") {
        super(formElement, "signin-submit");

        this.email = this._addInputs("signin-email", "keyup", Validator.validateEmail);
        this.password = this._addInputs("signin-password", "keyup", Validator.validatePassword);
        this.authModal = new AuthenticationModal();
    }

    async send(data) {
        if(!data) return

        const signinData = {
            email: data.get(this.email),
            password: data.get(this.password)
        };

        AuthenticationService.postSignin(signinData).then(async response => {

            if (response.user && response.token) {
                const authData = {
                    user: response.user,
                    token: response.token,
                    expiresIn: response.expiresIn
                }
                AuthenticationService.setAuthData(authData);
                this.clear();
                await appContext.refresh();
                this.authModal.close();

            } else if (response.messages) {
                const messages = Array.from(response.messages);
                messages.forEach(message => {
                    MessageUtils.danger(message);
                });
            }

        });

    }
}

export class SignupForm extends Form {
    firstName;
    lastName;
    email;
    streetAddress;
    zipCode;
    city;
    country;
    password;
    passwordCopy;
    charLengthError;
    charLowerUpperError;
    charNumberError;
    charSpecialError;

        constructor(formElement = "signup-form") {
        super(formElement, "signup-submit");

        this.firstName = this._addInputs("signup-first-name", "keyup", Validator.validateInputNotEmpty);
        this.lastName = this._addInputs("signup-last-name", "keyup", Validator.validateInputNotEmpty);
        this.email = this._addInputs("signup-email", "keyup", Validator.validateEmail);
        this.streetAddress = this._addInputs("signup-street-address", "keyup", Validator.validateInputNotEmpty);
        this.zipCode = this._addInputs("signup-zip-code", "keyup", Validator.validateZipCode);
        this.city = this._addInputs("signup-city", "keyup", Validator.validateInputNotEmpty);
        this.country = this._addInputs("signup-country", "keyup", Validator.validateInputNotEmpty);
        this.password = this._addInputs("signup-password", "keyup", this.validatePassword.bind(this));
        this.passwordCopy = this._addInputs("signup-password-copy", "keyup", this.validatePasswordCopy.bind(this));

        this.charLengthError = document.getElementById("signup-error-char-length");
        this.charLowerUpperError = document.getElementById("signup-error-char-lower-upper");
        this.charNumberError = document.getElementById("signup-error-char-number");
        this.charSpecialError = document.getElementById("signup-error-char-special");
    }

    validatePassword(inputKey, input) {
        const value = input.value;

        const checkLength = Validator.validateStringLength(value);
        this.colorMessage(this.charLengthError, checkLength);

        const checkLowerUpper = Validator.validateStringContainsUpperAndLowerCase(value);
        this.colorMessage(this.charLowerUpperError, checkLowerUpper);

        const checkNumber = Validator.validateStringContainsCharNumber(value);
        this.colorMessage(this.charNumberError, checkNumber);

        const checkSpecial = Validator.validateStringContainsSpecialCharacters(value);
        this.colorMessage(this.charSpecialError, checkSpecial);

        if(checkLength && checkLowerUpper && checkNumber && checkSpecial) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;


    }
    validatePasswordCopy(inputKey, input) {
        const [passwordKey, passwordInput] = this._getInput(this.password);

        if(passwordInput.classList.contains("is-invalid") ||
            passwordInput.value.length === 0 ||
            !Validator.validateStringEquals(passwordInput.value, input.value))
        {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            return false;
        }

        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
        return true;
    }

    colorMessage(message, ok) {
        if(ok) {
            message.classList.add("text-success");
            message.classList.remove("text-danger")
        }
        else {
            message.classList.remove("text-success");
            message.classList.add("text-danger");
        }
    }

    clearColorMessage( message) {
        message.classList.remove("text-success", "text-danger");
    }



    clear() {
        this._clear();
        this.clearColorMessage(this.charLengthError);
        this.clearColorMessage(this.charLowerUpperError);
        this.clearColorMessage(this.charNumberError);
        this.clearColorMessage(this.charSpecialError);
    }

    async send(data) {
        if(!data) return;

        const signupData = {
            firstName: data.get(this.firstName),
            lastName: data.get(this.lastName),
            email: data.get(this.email),
            password: data.get(this.password),
            streetAddress: data.get(this.streetAddress),
            zipCode: data.get(this.zipCode),
            city: data.get(this.city),
            country: data.get(this.country)
        };

        AuthenticationService.postSignup(signupData).then(response => {
            if(response.ok) {
                this.clear();
                const carousel = new Carousel(document.getElementById("formCarousel"));
                carousel.to(0);
                MessageUtils.success("Votre compte a bien été enregistré");
                if(response.emailSent) {
                    MessageUtils.success("Un email de confirmation vous a été envoyé");
                }


            }
            else{
                const messages = Array.from(response.messages);
                messages.forEach(message => {
                    MessageUtils.danger(message);
                });
            }

        });

    }
}

export class UserModifyForm extends Form {
    user;
    formCheckBox;
    email;
    firstName;
    lastName;
    streetAddress;
    zipCode;
    city;
    country;

    constructor(formElement = "user-modif-form") {
        super(formElement, "modif-submit");

        this.firstName = this._addInputs("modif-first-name", "keyup", Validator.validateInputNotEmpty);
        this.lastName = this._addInputs("modif-last-name", "keyup", Validator.validateInputNotEmpty);
        this.streetAddress = this._addInputs("modif-street-address", "keyup", Validator.validateInputNotEmpty);
        this.zipCode = this._addInputs("modif-zip-code", "keyup", Validator.validateZipCode);
        this.city = this._addInputs("modif-city", "keyup", Validator.validateInputNotEmpty);
        this.country = this._addInputs("modif-country", "keyup", Validator.validateInputNotEmpty);

        this.email = "user-email";
        this.formCheckBox = document.getElementById("user-form-checkbox");
        this.formCheckBox.addEventListener("change", event => {
            this.fieldDisabled(this.formCheckBox.checked);
        });
    }

    clear() {
        this._inputArray.forEach(([elementKey, element]) => {
            this._clearValidOrInvalidCSS(element);
        });
        this._submit.disabled = true;
    }

    validateForm() {
        if(!this.formCheckBox.checked && this.isDataChanged()) {
            this._validateForm();
        }
        else {
            this.clear();
        }
    }

    async send(data) {
        if(!data) return;

        const userInfoData = {
            uuid: this.user.uuid,
            firstName: data.get(this.firstName),
            lastName: data.get(this.lastName),
            streetAddress: data.get(this.streetAddress),
            zipCode: data.get(this.zipCode),
            city: data.get(this.city),
            country: data.get(this.country)
        };

        UserService.postUserInfoUpdate(userInfoData).then(response => {

            if(response.ok) {
                const authData = AuthenticationService.getAuthData();
                if(authData == null) {
                    this.user = null;
                    AuthenticationService.logout();
                    MessageUtils.danger("Un problème est survenue, déconnectez vous puis reconnectez vous");
                }
                else {
                    this.user = response.user;
                    authData.user = this.user;
                    AuthenticationService.setAuthData(authData);
                    this.clear();
                    MessageUtils.success("Vos informations personnelles ont bien été modifiées");
                }

            }
            else {
                const messages = response.messages;
                if(messages) {
                    messages.forEach(message => {
                        MessageUtils.danger(message);
                    })
                }
            }
        });
    }

    fill(userData) {
        if(userData == null) return;
        this.user = userData;
        this._setInputValue(this.firstName, this.user.firstName);
        this._setInputValue(this.lastName, this.user.lastName);
        this._setInputValue(this.streetAddress, this.user.address.streetAddress);
        this._setInputValue(this.zipCode, this.user.address.zipCode);
        this._setInputValue(this.city, this.user.address.city);
        this._setInputValue(this.country, this.user.address.country);
        const emailElement = document.getElementById(this.email);
        emailElement.value = this.user.email;
    }

    fieldDisabled(checked = true) {
        this._inputArray.forEach(([inputKey, input]) =>{
            input.disabled = checked;
        });
    }

    isDataChanged() {
        const data= this._extractData();
        if(this.user.firstName !== data.get(this.firstName)) return true;
        if(this.user.lastName !== data.get(this.lastName)) return true;
        if(this.user.address.streetAddress !== data.get(this.streetAddress)) return true;
        if(this.user.address.zipCode !== data.get(this.zipCode)) return true;
        if(this.user.address.city !== data.get(this.city)) return true;
        if(this.user.address.country !== data.get(this.country)) return true;
        return false;
    }
}

export class ChangePasswordForm extends Form {
    passwordOld;
    password;
    passwordCopy;
    charLengthError;
    charLowerUpperError;
    charNumberError;
    charSpecialError;
    formCheckBox;

    constructor(formElement = "change-password-form") {
        super(formElement, "change-password-submit");



        this.passwordOld = this._addInputs("change-password-old", "keyup", this.validatePassword.bind(this));
        this.password = this._addInputs("change-password", "keyup", this.validatePassword.bind(this));
        this.passwordCopy = this._addInputs("change-password-copy", "keyup", this.validatePasswordCopy.bind(this));

        this.charLengthError = document.getElementById("change-error-char-length");
        this.charLowerUpperError = document.getElementById("change-error-char-lower-upper");
        this.charNumberError = document.getElementById("change-error-char-number");
        this.charSpecialError = document.getElementById("change-error-char-special");

        this.formCheckBox = document.getElementById("change-password-form-checkbox");
        this.formCheckBox.addEventListener("change", event => {
            this.fieldDisabled(this.formCheckBox.checked);
        });
    }

    validatePassword(inputKey, input) {
        const value = input.value;

        const checkLength = Validator.validateStringLength(value);
        this.colorMessage(this.charLengthError, checkLength);

        const checkLowerUpper = Validator.validateStringContainsUpperAndLowerCase(value);
        this.colorMessage(this.charLowerUpperError, checkLowerUpper);

        const checkNumber = Validator.validateStringContainsCharNumber(value);
        this.colorMessage(this.charNumberError, checkNumber);

        const checkSpecial = Validator.validateStringContainsSpecialCharacters(value);
        this.colorMessage(this.charSpecialError, checkSpecial);

        if(checkLength && checkLowerUpper && checkNumber && checkSpecial) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;


    }
    validatePasswordCopy(inputKey, input) {
        const [passwordKey, passwordInput] = this._getInput(this.password);

        if(passwordInput.classList.contains("is-invalid") ||
            passwordInput.value.length === 0 ||
            !Validator.validateStringEquals(passwordInput.value, input.value))
        {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            return false;
        }

        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
        return true;
    }

    colorMessage(message, ok) {
        if(ok) {
            message.classList.add("text-success");
            message.classList.remove("text-danger")
        }
        else {
            message.classList.remove("text-success");
            message.classList.add("text-danger");
        }
    }

    clearColorMessage( message) {
        message.classList.remove("text-success", "text-danger");
    }

    clear() {
        this._clear();
        this.clearColorMessage(this.charLengthError);
        this.clearColorMessage(this.charLowerUpperError);
        this.clearColorMessage(this.charNumberError);
        this.clearColorMessage(this.charSpecialError);
        this.formCheckBox.checked = true;
        this.fieldDisabled();
        this._submit.disabled = true;
    }

    async send(data) {
        if(!data) return

        const user = AuthenticationService.getUser();

        const signinData = {
            email: user.email,
            passwordOld: data.get(this.passwordOld),
            password: data.get(this.password)
        };

        AuthenticationService.postChangePassword(signinData).then( response => {

            if (response.user && response.token) {
                const authData = {
                    user: response.user,
                    token: response.token,
                    expiresIn: response.expiresIn
                }
                AuthenticationService.setAuthData(authData);
                this.clear();
                MessageUtils.success("Votre mot de passe a bien été modifié");

            } else if (response.messages) {
                const messages = Array.from(response.messages);
                messages.forEach(message => {
                    MessageUtils.danger(message);
                });
            }
        });
    }

    fieldDisabled(checked = true) {
        this._inputArray.forEach(([inputKey, input]) =>{
            input.disabled = checked;
        });
    }

}

export class ResetPasswordForm extends Form{
    token;
    password;
    passwordCopy;
    charLengthError;
    charLowerUpperError;
    charNumberError;
    charSpecialError;
    constructor(token, formElement = "reset-password-form") {
        super(formElement, "reset-password-submit");
        this.token = token;

        this.password = this._addInputs("reset-password", "keyup", this.validatePassword.bind(this));
        this.passwordCopy = this._addInputs("reset-password-copy", "keyup", this.validatePasswordCopy.bind(this));

        this.charLengthError = document.getElementById("reset-error-char-length");
        this.charLowerUpperError = document.getElementById("reset-error-char-lower-upper");
        this.charNumberError = document.getElementById("reset-error-char-number");
        this.charSpecialError = document.getElementById("reset-error-char-special");
    }

    validatePassword(inputKey, input) {
        const value = input.value;

        const checkLength = Validator.validateStringLength(value);
        this.colorMessage(this.charLengthError, checkLength);

        const checkLowerUpper = Validator.validateStringContainsUpperAndLowerCase(value);
        this.colorMessage(this.charLowerUpperError, checkLowerUpper);

        const checkNumber = Validator.validateStringContainsCharNumber(value);
        this.colorMessage(this.charNumberError, checkNumber);

        const checkSpecial = Validator.validateStringContainsSpecialCharacters(value);
        this.colorMessage(this.charSpecialError, checkSpecial);

        if(checkLength && checkLowerUpper && checkNumber && checkSpecial) {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true;
        }
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    }

    validatePasswordCopy(inputKey, input) {
        const [passwordKey, passwordInput] = this._getInput(this.password);

        if(passwordInput.classList.contains("is-invalid") ||
            passwordInput.value.length === 0 ||
            !Validator.validateStringEquals(passwordInput.value, input.value))
        {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            return false;
        }

        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
        return true;
    }

    colorMessage(message, ok) {
        if(ok) {
            message.classList.add("text-success");
            message.classList.remove("text-danger")
        }
        else {
            message.classList.remove("text-success");
            message.classList.add("text-danger");
        }
    }

    clearColorMessage( message) {
        message.classList.remove("text-success", "text-danger");
    }

    clear() {
        this._clear();
        this.clearColorMessage(this.charLengthError);
        this.clearColorMessage(this.charLowerUpperError);
        this.clearColorMessage(this.charNumberError);
        this.clearColorMessage(this.charSpecialError);
        this._submit.disabled = true;
    }

    async send(data) {
        if(!data) return

        const resetPasswordData = {
            token: this.token,
            password: data.get(this.password)
        };

        ResetPasswordService.postResetPassword(resetPasswordData).then( response => {

            if (response.ok) {
                this.clear();
                window.location.replace("/home");
                MessageUtils.success("Votre mot de passe a bien été réinitialisé");

            } else if (response.messages) {
                const messages = Array.from(response.messages);
                messages.forEach(message => {
                    MessageUtils.danger(message);
                });
            }
            else{
                MessageUtils.danger("Un problème serveur est survenue, veuillez réessayer ultérieurement");
            }
        });

    }

}


export class ForgotPasswordForm extends Form{
    email;
    constructor(formElement = "forgot-password-form") {
        super(formElement, "forgot-password-submit");

        this.email = this._addInputs("forgot-password-email", "keyup", Validator.validateEmail);
    }

    async send(data) {
        if(!data) return;

        const forgotPasswordData = {
            email: data.get(this.email)
        };

        ResetPasswordService.postForgotPassword(forgotPasswordData).then(response => {
            if(response.ok) {
                this.clear();
                MessageUtils.success("Un email vous a été envoyer");
            }
            else{
                const messages = Array.from(response.messages);
                messages.forEach(message => {
                    MessageUtils.danger(message);
                });
            }
        });

    }
}