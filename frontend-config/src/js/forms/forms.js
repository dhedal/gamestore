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
            console.log(this._extractData());
        });
    }

    _getInput(inputKey) {
        return this._inputArray.find(([key, input]) => inputKey === key);
    }

    _addInputs(inputId, eventType, fnValidate) {
        const input = document.getElementById(inputId);
        this._fnMap.set(inputId, fnValidate);
        this._inputArray.push([inputId, input]);
        input.addEventListener(eventType, event => {
            event.stopPropagation();
            this._validateForm();
        });
        return inputId
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

    constructor(formElement = "signin-form") {
        super(formElement, "signin-submit");

        this.email = this._addInputs("signin-email", "keyup", Validator.validateEmail);
        this.password = this._addInputs("signin-password", "keyup", Validator.validatePassword);
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
}